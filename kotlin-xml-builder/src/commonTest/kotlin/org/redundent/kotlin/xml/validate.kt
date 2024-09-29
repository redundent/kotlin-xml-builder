package org.redundent.kotlin.xml

import kotlin.test.assertEquals

internal fun validate(expectedXml: String, xml: Node, prettyFormat: Boolean = true) {
	validate(expectedXml, xml, PrintOptions(pretty = prettyFormat))
}

internal fun validate(expectedXml: String, xml: Node, printOptions: PrintOptions) {
	val actual = xml.toString(printOptions)

	assertEquals(expectedXml, actual, "actual xml matches what is expected")

	validateXml(actual)
}

internal expect fun validateXml(actual: String)
