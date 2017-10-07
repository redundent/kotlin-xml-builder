package org.redundent.kotlin.xml

import org.apache.commons.lang3.StringEscapeUtils
import java.util.*

/**
 * Base interface for all elements. You shouldn't have to interact with this interface directly.
 */
internal interface Element {
	/**
	 * This method handles creating the xml. Used internally
	 */
	fun render(builder: StringBuilder, indent: String)
}

/**
 * An element type that has some text in it.
 * For example:
 * <loc>http://google.com</loc>
 */
open class TextElement(val text: String, prettyFormat: Boolean = true) : Element {
	protected val lineEnding: String = if (prettyFormat) System.lineSeparator() else ""

	override fun render(builder: StringBuilder, indent: String) {
		builder.append("$indent${StringEscapeUtils.escapeXml11(text)}$lineEnding")
	}
}

/**
 * Similar to a [TextElement] except that the inner text is wrapped inside a <![CDATA[]]> tag.
 */
class CDATAElement(text: String, prettyFormat: Boolean = true) : TextElement(text, prettyFormat) {
	override fun render(builder: StringBuilder, indent: String) {
		builder.append("$indent<![CDATA[$lineEnding$text$lineEnding]]>$lineEnding")
	}
}

/**
 * Base type for all elements. This is what handles pretty much all the redereing and building.
 */
open class Node(private val name: String, private val prettyFormat: Boolean = true) : Element {
	/**
	 * The default xmlns for the document. To add other namespaces, use the [namespace] method
	 */
	var xmlns: String?
		get() = attributes["xmlns"] as? String
		set(value) {
			if (value == null) {
				attributes.remove("xmlns")
			} else {
				attributes["xmlns"] = value
			}
		}

	private val children = ArrayList<Element>()

	/**
	 * Any attributes that belong to this element. You can either interact with this property directly or use the [get] and [set] operators
	 * @sample [set]
	 */
	var attributes = LinkedHashMap<String, Any>()
	private val lineEnding = if (prettyFormat) System.lineSeparator() else ""

	private fun <T : Element> initTag(tag: T, init: (T.() -> Unit)?): T {
		if (init != null) {
			tag.init()
		}
		children.add(tag)
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
		return attributes[attributeName] as? T?
	}

	/**
	 * Allows for easy access of adding/updating this node's attributes
	 *
	 * <code>
	 *     element["key"] = "value"
	 * </code>
	 */
	operator fun set(attributeName: String, value: Any) {
		attributes[attributeName] = value
	}

	override fun render(builder: StringBuilder, indent: String) {
		builder.append("$indent<$name${renderAttributes()}")

		if (children.isNotEmpty()) {
			builder.append(">$lineEnding")
			for (c in children) {
				c.render(builder, getIndent(indent))
			}
			builder.append("$indent</$name>$lineEnding")
		} else {
			builder.append("/>$lineEnding")
		}
	}

	private fun renderAttributes(): String {
		val builder = StringBuilder()
		for (a in attributes.keys) {
			builder.append(" $a=\"${attributes[a]}\"")
		}
		return builder.toString()
	}

	private fun getIndent(indent: String): String {
		if (!prettyFormat) {
			return ""
		}

		return "$indent\t"
	}

	override fun toString(): String {
		val builder = StringBuilder()
		render(builder, "")
		return builder.toString().trim()
	}

	operator fun String.unaryMinus() {
		children.add(TextElement(this, prettyFormat))
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
	fun element(name: String, init: (Node.() -> Unit)?): Node = initTag(Node(name, prettyFormat), init)

	/**
	 * Adds a basic element with the specific name and value to the parent. This cannot be used for complex elements.
	 * <code>
	 *     element("url", "https://google.com")
	 * </code>
	 *
	 * @param name The name of the element.
	 * @param value The inner text of the element
	 */
	fun element(name: String, value: String): Node {
		return initTag(Node(name, prettyFormat)) {
			-value
		}
	}

	/**
	 * Adds an attribute to the current element
	 * <code>
	 *     elenmet("url") {
	 *         attribute("key", "value")
	 *     }
	 * </code>
	 *
	 * @param name The name of the attribute. This is currenly no validation against the name.
	 * @param value The attribute value.
	 */
	fun attribute(name: String, value: Any) {
		attributes[name] = value
	}

	/**
	 * Adds a set of attributes to the current element.
	 * @see [attribute]
	 *
	 * <code>
	 *     element("url") {
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
		children.add(CDATAElement(text, prettyFormat))
	}

	/**
	 * Adds the specified namespace to the element.
	 * <code>
	 *     element("url") {
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
}

/**
 * Root element for all xml structures. Don't access this class directly. You should use [xml] instead.
 */
class Xml(root: String, prettyFormat: Boolean) : Node(root, prettyFormat)

/**
 * Creates a new xml document with the specified root element name
 *
 * @param root The root element name
 * @param init The block that defines the content of the xml
 */
fun xml(root: String, prettyFormat: Boolean = true, init: (Xml.() -> Unit)? = null): Xml {
	val xml = Xml(root, prettyFormat)
	if (init != null) {
		xml.init()
	}
	return xml
}