package org.redundent.kotlin.xml

import org.junit.Rule
import org.junit.rules.TestName
import org.w3c.dom.Document
import java.io.InputStream
import java.io.InputStreamReader
import java.io.StringWriter
import java.util.MissingResourceException
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import kotlin.test.assertEquals

open class TestBase {
	@get:Rule
	val testName = TestName()

	private fun getExpectedXml(): String {
		val inputStream = getInputStream()
		inputStream.use {
			return InputStreamReader(it).readText().replace(System.lineSeparator(), "\n")
		}
	}

	protected fun getInputStream(): InputStream {
		val resName = "/test-results/${javaClass.simpleName}/${testName.methodName}.xml"
		return javaClass.getResourceAsStream(resName)
			?: throw MissingResourceException("Cannot find expected xml resource: $resName. Did you forget to create it?", javaClass.name, testName.methodName)
	}

	protected fun validate(xml: Node, prettyFormat: Boolean = true) {
		validate(xml, PrintOptions(pretty = prettyFormat))
	}

	protected fun validate(xml: Node, printOptions: PrintOptions) {
		val actual = xml.toString(printOptions)

		// Doing a replace to cater for different line endings.
		assertEquals(getExpectedXml(), actual.replace(System.lineSeparator(), "\n"), "actual xml matches what is expected")

		validateXml(actual)
	}

	protected fun validateXml(actual: String): Document {
		return actual.byteInputStream().use {
			DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(it)
		}
	}

	protected fun validateTest(xml: Node) {
		val actual = validateXml(xml.toString())
		val expected = getInputStream().use {
			DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(it)
		}

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
