package org.redundent.kotlin.xml

import org.apache.commons.lang3.StringEscapeUtils
import org.w3c.dom.Document
import org.xml.sax.InputSource
import java.io.File
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.ArrayList
import java.util.Comparator
import java.util.LinkedHashMap
import java.util.NoSuchElementException
import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.Node as W3CNode

private fun getLineEnding(prettyFormat: Boolean) = if (prettyFormat) System.lineSeparator() else ""

/**
 * Base interface for all elements. You shouldn't have to interact with this interface directly.
 * @author Jason Blackwell
 */
interface Element {
	/**
	 * This method handles creating the xml. Used internally
	 */
	fun render(builder: StringBuilder, indent: String, prettyFormat: Boolean)
}

/**
 * An element type that has some text in it.
 * For example:
 * <loc>http://blog.redundent.org</loc>
 */
open class TextElement internal constructor(val text: String) : Element {
	internal fun isEmpty() = text.trim('\n', '\r').isBlank()

	override fun render(builder: StringBuilder, indent: String, prettyFormat: Boolean) {
		if (isEmpty()) {
			return
		}

		val lineEnding = getLineEnding(prettyFormat)

		builder.append("$indent${StringEscapeUtils.escapeXml11(text)}$lineEnding")
	}
}

/**
 * Similar to a [TextElement] except that the inner text is wrapped inside a <![CDATA[]]> tag.
 */
class CDATAElement internal constructor(text: String) : TextElement(text) {
	override fun render(builder: StringBuilder, indent: String, prettyFormat: Boolean) {
		if (isEmpty()) {
			return
		}

		val lineEnding = getLineEnding(prettyFormat)
		builder.append("$indent<![CDATA[$lineEnding$text$lineEnding]]>$lineEnding")
	}
}

/**
 * Base type for all elements. This is what handles pretty much all the rendering and building.
 */
open class Node(val nodeName: String) : Element {
	/**
	 * The default xmlns for the document. To add other namespaces, use the [namespace] method
	 */
	var xmlns: String?
		get() = this["xmlns"]
		set(value) {
			this["xmlns"] = value
		}

	/**
	 * Whether to include the xml prolog, i.e. <?xml version="1.0" encoding="UTS-8"?>
	 */
	var includeXmlProlog = false

	/**
	 * Sets the encoding on the document. Setting this value will set [includeXmlProlog] to true
	 */
	var encoding: String = StandardCharsets.UTF_8.name()
		set(value) {
			includeXmlProlog = true
			field = value
		}

	/**
	 * Any attributes that belong to this element. You can either interact with this property directly or use the [get] and [set] operators
	 * @sample [set]
	 */
	val attributes = LinkedHashMap<String, Any?>()

	private val _children = ArrayList<Element>()

	private val childOrderMap: Map<String, Int>? by lazy {
		val xmlTypeAnnotation = this::class.annotations.firstOrNull { it is XmlType } as? XmlType ?: return@lazy null
		val childOrder = xmlTypeAnnotation.childOrder

		childOrder.indices.associateBy { childOrder[it] }
	}

	val children: List<Element>
		get() = _children

	private fun <T : Element> initTag(tag: T, init: (T.() -> Unit)?): T {
		if (init != null) {
			tag.init()
		}
		_children.add(tag)
		return tag
	}

	/**
	 * Allows for easy access of this node's attributes
	 *
	 * <code>
	 *     val attr = element["key"]
	 * </code>
	 */
	operator fun <T> get(attributeName: String): T? {
		@Suppress("UNCHECKED_CAST")
		return attributes[attributeName] as T?
	}

	/**
	 * Allows for easy access of adding/updating this node's attributes
	 *
	 * <code>
	 *     element["key"] = "value"
	 * </code>
	 */
	operator fun set(attributeName: String, value: Any?) {
		if (value == null) {
			attributes.remove(attributeName)
		} else {
			attributes[attributeName] = value
		}
	}

	fun hasAttribute(attributeName: String): Boolean = attributes.containsKey(attributeName)

	override fun render(builder: StringBuilder, indent: String, prettyFormat: Boolean) {
		val lineEnding = getLineEnding(prettyFormat)
		builder.append("$indent<$nodeName${renderAttributes()}")

		if (_children.isNotEmpty()) {
			builder.append(">$lineEnding")
			for (c in sortedChildren()) {
				c.render(builder, getIndent(prettyFormat, indent), prettyFormat)
			}
			builder.append("$indent</$nodeName>$lineEnding")
		} else {
			builder.append("/>$lineEnding")
		}
	}

	private fun sortedChildren(): List<Element> {
		return if (childOrderMap == null) {
			_children
		} else {
			_children.sortedWith(Comparator { a, b ->
				val indexA = if (a is Node) childOrderMap!![a.nodeName] else 0
				val indexB = if (b is Node) childOrderMap!![b.nodeName] else 0

				compareValues(indexA, indexB)
			})
		}
	}

	private fun renderAttributes(): String {
		if (attributes.isEmpty()) {
			return ""
		}

		return " " + attributes.map {
			"${it.key}=\"${escapeQuotes(it.value)}\""
		}.joinToString(" ")
	}

	private fun escapeQuotes(value: Any?): String? {
		val asString = value?.toString() ?: return null

		val sb = StringBuilder()
		for (c in asString) {
			sb.append(when (c) {
				'"' -> "&quot;"
				'\'' -> "&apos;"
				else -> c
			})
		}

		return sb.toString()
	}

	private fun getIndent(prettyFormat: Boolean, indent: String): String = if (!prettyFormat) "" else "$indent\t"

	/**
	 * Get the xml representation of this object with prettyFormat = true
	 */
	override fun toString() = toString(prettyFormat = true)

	/**
	 * Get the xml representation of this object
	 *
	 * @param [prettyFormat] true to format the xml with newlines and tabs; otherwise no formatting
	 */
	fun toString(prettyFormat: Boolean): String {
		val lineEnding = getLineEnding(prettyFormat)

		val sb = StringBuilder()

		if (includeXmlProlog) {
			sb.append("<?xml version=\"1.0\" encoding=\"$encoding\"?>$lineEnding")
		}

		render(sb, "", prettyFormat)

		return sb.toString().trim()
	}

	operator fun String.unaryMinus() = text(this)

	fun text(text: String) {
		_children.add(TextElement(text))
	}

	/**
	 * Adds a basic element with the specific name to the parent.
	 * <code>
	 *     element("url") {
	 *     		...
	 *     }
	 * </code>
	 *
	 * @param name The name of the element.
	 * @param init The block that defines the content of the element.
	 */
	fun element(name: String, init: (Node.() -> Unit)? = null): Node = initTag(Node(name), init)

	/**
	 * Adds a basic element with the specific name and value to the parent. This cannot be used for complex elements.
	 * <code>
	 *     element("url", "https://google.com")
	 * </code>
	 *
	 * @param name The name of the element.
	 * @param value The inner text of the element
	 */
	fun element(name: String, value: String): Node = initTag(Node(name)) {
		-value
	}

	/**
	 * Adds a basic element with the specific name and value to the parent. This cannot be used for complex elements.
	 * <code>
	 *     "url"("https://google.com")
	 * </code>
	 *
	 * @receiver The name of the element.
	 * @param value The inner text of the element
	 */
	operator fun String.invoke(value: String): Node = element(this, value)

	/**
	 * Adds a basic element with the specific name to the parent. This method
	 * allows you to specify optional attributes and content
	 * <code>
	 *     "url"("key" to "value") {
	 *     		...
	 *     }
	 * </code>
	 *
	 * @receiver The name of the element.
	 * @param attributes Any attributes to add to this element. Can be omited.
	 * @param init The block that defines the content of the element.
	 */
	operator fun String.invoke(vararg attributes: Pair<String, Any>, init: (Node.() -> Unit)? = null): Node {
		val e = element(this) {
			attributes(*attributes)
		}

		if (init != null) {
			e.apply(init)
		}

		return e
	}

	/**
	 * Adds an attribute to the current element
	 * <code>
	 *     "url" {
	 *         attribute("key", "value")
	 *     }
	 * </code>
	 *
	 * @param name The name of the attribute. This is currenly no validation against the name.
	 * @param value The attribute value.
	 */
	fun attribute(name: String, value: Any) {
		attributes[name] = value.toString()
	}

	/**
	 * Adds a set of attributes to the current element.
	 * @see [attribute]
	 *
	 * <code>
	 *     "url" {
	 *         attributes(
	 *             "key" to "value",
	 *             "id" to "1"
	 *         )
	 *     }
	 * </code>
	 *
	 * @param attrs Collection of the attributes to apply to this element.
	 */
	fun attributes(vararg attrs: Pair<String, Any>) {
		attrs.forEach { attribute(it.first, it.second) }
	}

	/**
	 * Adds the supplied text as a CDATA element
	 *
	 * @param text The inner text of the CDATA element.
	 */
	fun cdata(text: String) {
		_children.add(CDATAElement(text))
	}

	/**
	 * Adds the specified namespace to the element.
	 * <code>
	 *     "url" {
	 *         namespace("t", "http://someurl.org")
	 *     }
	 * </code>
	 *
	 * @param name The name of the namespace.
	 * @param value The url or descriptor of the namespace
	 */
	fun namespace(name: String, value: String) {
		attributes["xmlns:$name"] = value
	}

	/**
	 * Adds a node to the element.
	 * @param node The node to append.
	 */
	fun addNode(node: Node) {
		_children.add(node)
	}

	/**
	 * Adds a node to the element after the specific node.
	 * @param node The node to add
	 * @param after The node to add [node] after
	 *
	 * @throws IllegalArgumentException If [after] can't be found
	 */
	fun addNodeAfter(node: Node, after: Node) {
		val index = findIndex(after)
		if (index + 1 == _children.size) {
			_children.add(node)
		} else {
			_children.add(index + 1, node)
		}
	}

	/**
	 * Adds a node to the element before the specific node.
	 * @param node The node to add
	 * @param before The node to add [node] before
	 *
	 * @throws IllegalArgumentException If [before] can't be found
	 */
	fun addNodeBefore(node: Node, before: Node) {
		_children.add(findIndex(before), node)
	}

	/**
	 * Removes a node from the element
	 * @param node The node to remove
	 *
	 * @throws IllegalArgumentException If [node] can't be found
	 */
	fun removeNode(node: Node) {
		val index = findIndex(node)
		_children.removeAt(index)
	}

	/**
	 * Replaces a node with a different node
	 * @param existing The existing node to replace
	 * @param newNode The node to replace [existing] with
	 *
	 * @throws IllegalArgumentException If [existing] can't be found
	 */
	fun replaceNode(existing: Node, newNode: Node) {
		val index = findIndex(existing)

		_children.removeAt(index)
		_children.add(index, newNode)
	}

	/**
	 * Returns a list containing only elements whose nodeName matches [name].
	 */
	fun filter(name: String): List<Node> = filter { it.nodeName == name }

	/**
	 * Returns a list containing only elements matching the given [predicate].
	 */
	fun filter(predicate: (Node) -> Boolean): List<Node> = filterChildrenToNodes().filter(predicate)

	/**
	 * Returns the first element whose nodeName matches [name].
	 * @throws [NoSuchElementException] if no such element is found.
	 */
	fun first(name: String): Node = filterChildrenToNodes().first { it.nodeName == name }

	/**
	 * Returns the first element matching the given [predicate].
	 * @throws [NoSuchElementException] if no such element is found.
	 */
	fun first(predicate: (Element) -> Boolean): Element = _children.first(predicate)

	/**
	 * Returns the first element whose nodeName matches [name], or `null` if element was not found.
	 */
	fun firstOrNull(name: String): Node? = filterChildrenToNodes().firstOrNull { it.nodeName == name }

	/**
	 * Returns the first element matching the given [predicate], or `null` if element was not found.
	 */
	fun firstOrNull(predicate: (Element) -> Boolean): Element? = _children.firstOrNull(predicate)

	/**
	 * Returns `true` if at least one element's nodeName matches [name].
	 */
	fun exists(name: String): Boolean = filterChildrenToNodes().any { it.nodeName == name }

	/**
	 * Returns `true` if at least one element matches the given [predicate].
	 */
	fun exists(predicate: (Element) -> Boolean): Boolean = _children.any(predicate)

	private fun filterChildrenToNodes(): List<Node> = _children.filterIsInstance(Node::class.java)

	private fun findIndex(node: Node): Int {
		val index = _children.indexOf(node)
		if (index == -1) {
			throw IllegalArgumentException("Node with nodeName '${node.nodeName}' is not a child of '$nodeName'")
		}

		return index
	}
}

/**
 * Creates a new xml document with the specified root element name
 *
 * @param root The root element name
 * @param prettyFormat Whether to format the xml with newlines and tabs or keep it all on a single line
 * @param encoding The encoding to use for the xml prolog
 * @param init The block that defines the content of the xml
 */
fun xml(root: String, encoding: String? = null, init: (Node.() -> Unit)? = null): Node {
	val node = Node(root)
	if (encoding != null) {
		node.encoding = encoding
	}
	if (init != null) {
		node.init()
	}
	return node
}

/**
 * Creates a new xml document with the specified root element name
 *
 * @param name The name of the element
 * @param init The block that defines the content of the xml
 */
fun node(name: String, init: (Node.() -> Unit)? = null): Node {
	val node = Node(name)
	if (init != null) {
		node.init()
	}
	return node
}

fun parse(f: File): Node = parse(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f))
fun parse(uri: String): Node = parse(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(uri))
fun parse(`is`: InputSource): Node = parse(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(`is`))
fun parse(`is`: InputStream): Node = parse(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(`is`))
fun parse(`is`: InputStream, systemId: String): Node = parse(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(`is`, systemId))

private fun parse(document: Document): Node {
	val root = document.documentElement

	val result = xml(root.tagName)

	copyAttributes(root, result)

	val children = root.childNodes
	(0 until children.length)
			.map(children::item)
			.forEach { copy(it, result) }

	return result
}

private fun copy(source: W3CNode, dest: Node) {
	when (source.nodeType) {
		W3CNode.ELEMENT_NODE -> {
			val cur = dest.element(source.nodeName)

			copyAttributes(source, cur)

			val children = source.childNodes
			(0 until children.length)
					.map(children::item)
					.forEach { copy(it, cur) }
		}
		W3CNode.CDATA_SECTION_NODE -> {
			dest.cdata(source.nodeValue.trim { it.isWhitespace() || it == '\r' || it == '\n' })
		}
		W3CNode.TEXT_NODE -> {
 			dest.text(source.nodeValue.trim { it.isWhitespace() || it == '\r' || it == '\n' })
		}
	}
}

private fun copyAttributes(source: W3CNode, dest: Node) {
	val attributes = source.attributes
	if (attributes == null || attributes.length == 0) {
		return
	}

	(0 until attributes.length)
			.map(attributes::item)
			.forEach { dest.attribute(it.nodeName, it.nodeValue) }
}