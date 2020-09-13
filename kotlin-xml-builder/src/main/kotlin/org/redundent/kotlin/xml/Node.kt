package org.redundent.kotlin.xml

import java.util.ArrayList
import java.util.LinkedHashMap
import java.util.NoSuchElementException

/**
 * Base type for all elements. This is what handles pretty much all the rendering and building.
 */
open class Node(val nodeName: String) : Element {
	private companion object {
		private val isReflectionAvailable: Boolean by lazy {
			Node::class.java.classLoader.getResource("kotlin/reflect/full") != null
		}
	}

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
	 *
	 * <p>NOTE: this only applies to the root element. It is ignored an all children
	 */
	var includeXmlProlog = false

	/**
	 * Sets the encoding on the document. Setting this value will set [includeXmlProlog] to true
	 */
	var encoding: String = Charsets.UTF_8.name()
		set(value) {
			includeXmlProlog = true
			field = value
		}

	var version: XmlVersion = XmlVersion.V10
		set(value) {
			includeXmlProlog = true
			field = value
		}

	/**
	 * Any attributes that belong to this element. You can either interact with this property directly or use the [get] and [set] operators
	 * @sample [set]
	 */
	val attributes = LinkedHashMap<String, Any?>()

	private val _globalLevelProcessingInstructions = ArrayList<ProcessingInstructionElement>()

	private val _children = ArrayList<Element>()

	private val childOrderMap: Map<String, Int>? by lazy {
		if (!isReflectionAvailable) {
			return@lazy null
		}

		@Suppress("NO_REFLECTION_IN_CLASS_PATH") // Checked for reflection class above
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

	override fun render(builder: Appendable, indent: String, printOptions: PrintOptions) {
		val lineEnding = getLineEnding(printOptions)
		builder.append("$indent<$nodeName${renderAttributes(printOptions)}")

		if (_children.isNotEmpty()) {
			if (printOptions.pretty && printOptions.singleLineTextElements
				&& _children.size == 1 && _children[0] is TextElement) {
				builder.append(">")
				(_children[0] as TextElement).renderSingleLine(builder, printOptions)
				builder.append("</$nodeName>$lineEnding")
			} else {
				builder.append(">$lineEnding")
				for (c in sortedChildren()) {
					c.render(builder, getIndent(printOptions, indent), printOptions)
				}

				builder.append("$indent</$nodeName>$lineEnding")
			}
		} else {
			builder.append("${getEmptyTagClosing(printOptions)}$lineEnding")
		}
	}

	private fun getEmptyTagClosing(printOptions: PrintOptions): String = if (printOptions.useSelfClosingTags)
		"/>"
	else
		"></$nodeName>"

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

	private fun renderAttributes(printOptions: PrintOptions): String {
		if (attributes.isEmpty()) {
			return ""
		}

		return " " + attributes.entries.joinToString(" ") {
			"${it.key}=\"${escapeValue(it.value, printOptions.xmlVersion, printOptions.useCharacterReference)}\""
		}
	}

	private fun getIndent(printOptions: PrintOptions, indent: String): String = if (!printOptions.pretty) "" else "$indent\t"

	/**
	 * Get the xml representation of this object with prettyFormat = true
	 */
	override fun toString() = toString(prettyFormat = true)

	/**
	 * Get the xml representation of this object
	 *
	 * @param [prettyFormat] true to format the xml with newlines and tabs; otherwise no formatting
	 */
	fun toString(prettyFormat: Boolean): String = toString(PrintOptions(pretty = prettyFormat))

	fun toString(printOptions: PrintOptions): String
		= StringBuilder().also { writeTo(it, printOptions) }.toString().trim()

	fun writeTo(appendable: Appendable, printOptions: PrintOptions = PrintOptions()) {
		val lineEnding = getLineEnding(printOptions)

		printOptions.xmlVersion = version

		if (includeXmlProlog) {
			appendable.append("<?xml version=\"${printOptions.xmlVersion.value}\" encoding=\"$encoding\"?>$lineEnding")
		}

		if (_globalLevelProcessingInstructions.isNotEmpty()) {
			_globalLevelProcessingInstructions.forEach { it.render(appendable, "", printOptions) }
		}

		render(appendable, "", printOptions)
	}

	operator fun String.unaryMinus() = text(this)

	fun text(text: String) {
		_children.add(TextElement(text))
	}

	/**
	 * Adds an xml comment to the document.
	 * <code>
	 *     comment("my comment")
	 * </code>
	 *
	 * @param text The text of the comment. This text will be rendenered unescaped except for replace "--" with "&#45;&#45;"]
	 */
	fun comment(text: String) {
		_children.add(Comment(text))
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
	 * Adds the supplied text as a processing instruction element
	 *
	 * @param text The inner text of the processing instruction element.
	 * @param attribtes Optional set of attributes to apply to this processing instruction.
	 */
	fun processingInstruction(text: String, vararg attribtes: Pair<String, String>) {
		_children.add(ProcessingInstructionElement(text, linkedMapOf(*attribtes)))
	}


	/**
	 * Adds the supplied text as a processing instruction element to the root of the document.
	 *
	 * @param text The inner text of the processing instruction element.
	 * @param attribtes Optional set of attributes to apply to this processing instruction.
	 */
	fun globalProcessingInstruction(text: String, vararg attribtes: Pair<String, String>) {
		_globalLevelProcessingInstructions.add(ProcessingInstructionElement(text, linkedMapOf(*attribtes)))
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