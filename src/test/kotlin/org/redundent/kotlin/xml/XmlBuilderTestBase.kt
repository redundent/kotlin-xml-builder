package org.redundent.kotlin.xml

import org.junit.Rule
import org.junit.rules.TestName
import java.io.InputStreamReader
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.test.assertEquals

open class XmlBuilderTestBase {
	@get:Rule
	val testName = TestName()

	protected fun getExpectedXml(): String {
		val inputStream = javaClass.getResourceAsStream("/test-results/${javaClass.simpleName}/${testName.methodName}.xml")
		inputStream.use {
			return InputStreamReader(it).readText().replace(System.lineSeparator(), "\n")
		}
	}

	protected fun validate(xml: Node) {
		val actual = xml.toString()

		//Doing a replace to cater for different line endings.
		assertEquals(getExpectedXml(), actual.replace(System.lineSeparator(), "\n"), "actual xml matches what is expected")

		validateXml(actual)
	}

	protected fun validateXml(actual: String) {
		actual.byteInputStream().use {
			DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(it)
		}
	}
}