package org.redundent.kotlin.xml

import java.io.ByteArrayInputStream
import java.io.StringReader
import java.io.StringWriter
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import org.w3c.dom.Document
import org.xml.sax.InputSource
import org.xml.sax.SAXException

class XmlParseTest {

	@Test
	fun invalidElementName() {
		val root = xml("invalid root")

		assertFailsWith<SAXException> {
			validateXml(root.toString())
		}
	}

	@Test
	fun invalidAttributeName() {
		val root = xml("root") {
			attribute("invalid name", "")
		}

		assertFailsWith<SAXException> {
			validateXml(root.toString())
		}
	}

	@Test
	fun parseAndVerify() {
		val xmlns = "http://blog.redundent.org"
		val value = "value"
		val input = ByteArrayInputStream("<root xmlns=\"$xmlns\"><child>$value</child></root>".toByteArray())

		val root = parse(input)

		assertEquals("root", root.nodeName, "root element nodeName is correct")
		assertEquals(xmlns, root.xmlns, "root xmlns is correct")

		val children = root.children
		assertEquals(1, children.size, "root has 1 child")
		assertTrue(children[0] is Node, "child is a node")

		val child = children.first() as Node
		assertTrue(child.children[0] is TextElement, "element is text")
		assertEquals(value, (child.children[0] as TextElement).text)
	}

	@Test
	fun parseCData() = parseTest(XmlBuilderTestResults.parseCData)

	@Test
	fun parseCDataWhitespace() = parseTest(XmlBuilderTestResults.parseCDataWhitespace)

	@Test
	fun parseCustomNamespaces() = parseTest(XmlBuilderTestResults.parseCustomNamespaces)

	@Test
	fun parseMultipleAttributes() = parseTest(XmlBuilderTestResults.parseMultipleAttributes)

	@Test
	fun parseBasicTest() = parseTest(XmlBuilderTestResults.parseBasicTest)

	@Test
	fun parseXmlEncode() = parseTest(XmlBuilderTestResults.parseXmlEncode)

	private fun parseTest(testResult: String) {
		val xml = parseXml(testResult)
		validateTest(testResult, xml)
	}

	companion object {

		private fun validateTest(testResult: String, xml: Node) {
			val actual = parseXmlToDocument(xml.toString())
			val expected = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(InputSource(StringReader(testResult)))

			val actualString = actual.transform()
			val expectedString = expected.transform()

			assertEquals(expectedString, actualString, "actual xml matches what is expected")
		}

		private fun Document.transform(): String {
			val sw = StringWriter()
			val tf = TransformerFactory.newInstance()
			val transformer = tf.newTransformer()
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no")
			transformer.setOutputProperty(OutputKeys.METHOD, "xml")
			transformer.setOutputProperty(OutputKeys.INDENT, "yes")
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8")

			transformer.transform(DOMSource(this), StreamResult(sw))
			return sw.toString()
		}
	}
}
