package org.redundent.kotlin.xml

import org.w3c.dom.Document
import org.xml.sax.InputSource
import java.io.File
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.Node as W3CNode

internal fun getLineEnding(printOptions: PrintOptions) = if (printOptions.pretty) System.lineSeparator() else ""

/**
 * Creates a new xml document with the specified root element name
 *
 * @param root The root element name
 * @param encoding The encoding to use for the xml prolog
 * @param version The XML specification version to use for the xml prolog and attribute encoding
 * @param init The block that defines the content of the xml
 */
fun xml(root: String, encoding: String? = null, version: XmlVersion? = null, init: (Node.() -> Unit)? = null): Node {
	val node = Node(root)
	if (encoding != null) {
		node.encoding = encoding
	}

	if (version != null) {
		node.version = version
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
			dest.cdata(source.nodeValue)
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
