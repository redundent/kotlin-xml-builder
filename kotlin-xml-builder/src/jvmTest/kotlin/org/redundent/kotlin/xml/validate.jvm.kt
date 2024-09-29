package org.redundent.kotlin.xml

import javax.xml.parsers.DocumentBuilderFactory
import org.intellij.lang.annotations.Language
import org.w3c.dom.Document

internal actual fun validateXml(
	@Language("XML")
	actual: String,
) {
	parseXmlToDocument(actual)
}

internal fun parseXmlToDocument(
	@Language("XML")
	xml: String,
): Document {
	return xml.byteInputStream().use {
		DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(it)
	}
}
