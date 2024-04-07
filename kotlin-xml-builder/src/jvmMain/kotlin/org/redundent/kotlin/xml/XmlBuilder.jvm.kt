package org.redundent.kotlin.xml

import org.w3c.dom.Document
import org.xml.sax.InputSource
import java.io.File
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.math.min
import org.w3c.dom.Node as W3CNode

fun parse(f: File): Node = parse(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(f))
fun parse(uri: String): Node = parse(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(uri))
fun parse(inputSource: InputSource): Node =
	parse(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource))

fun parse(inputStream: InputStream): Node =
	parse(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream))

fun parse(inputStream: InputStream, systemId: String): Node =
	parse(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream, systemId))

fun parse(document: Document): Node {
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
		.forEach {
			if (it.nodeName.startsWith("xmlns")) {
				dest.namespace(it.nodeName.substring(min(6, it.nodeName.length)), it.nodeValue)
			} else {
				dest.attribute(it.nodeName, it.nodeValue)
			}
		}
}
