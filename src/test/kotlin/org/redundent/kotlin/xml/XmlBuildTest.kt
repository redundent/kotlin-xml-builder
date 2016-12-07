package org.redundent.kotlin.xml

import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestName
import org.xml.sax.SAXException
import java.io.ByteArrayInputStream
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.test.assertEquals

class XmlBuildTest {
	val testName = TestName()
	@Rule
	fun testName(): TestName = testName

	@Test
	fun basicTest() {
		val urlset = xml("urlset") {
			xmlns = "http://www.sitemaps.org/schemas/sitemap/0.9"

			for (i in 0..2) {
				element("url") {
					element("loc") {
						-"http://google.com/$i"
					}
				}
			}
		}

		validate(urlset.toString())
	}

	@Test
	fun customNamespaces() {
		val root = xml("root") {
			xmlns = "http://someurl.org"
			namespace("t", "http://t.org")

			element("t:element") {
				-"Test"
			}

			element("p") {
				xmlns = "http://t.co"
			}

			element("d:p") {
				namespace("d", "http://b.co")
			}
		}

		validate(root.toString())
	}

	@Test
	fun notPrettyFormatting() {
		val root = xml("root", false) {
			element("element") {
				-"Hello"
			}
			element("otherElement") {
				-"Test"
			}
		}

		validate(root.toString())
	}

	@Test
	fun multipleAttributes() {
		val root = xml("root") {
			element("test") {
				attribute("key", "value")
				attribute("otherAttr", "hello world")
			}
			element("attributes") {
				attributes(
						"test" to "value",
						"key" to "pair"
				)
			}
		}

		validate(root.toString())
	}

	@Test
	fun emptyElement() {
		validate(xml("root").toString())
	}

	@Test
	fun cdata() {
		val root = xml("root") {
			cdata("Some & xml")
		}

		validate(root.toString())
	}

	@Test
	fun updateAttribute() {
		val root = xml("root") {
			attribute("key", "value")
		}

		root["key"] = "otherValue"

		validate(root.toString())
	}

	@Test
	fun xmlEncode() {
		val root = xml("root") {
			-"&<>"
		}

		validate(root.toString())
	}

	@Test(expected = SAXException::class)
	fun invalidElementName() {
		val root = xml("invalid root")

		validateXml(root.toString())
	}

	@Test(expected = SAXException::class)
	fun invalidAttributeName() {
		val root = xml("root") {
			attribute("invalid name", "")
		}

		validateXml(root.toString())
	}

	private fun validate(actual: String) {
		//Doing a replace to cater for different line endings.
		assertEquals(TestUtils.getExpectedXml(testName.methodName), actual.replace(System.lineSeparator(), "\n"), "actual xml matches what is expected")

		validateXml(actual)
	}

	private fun validateXml(actual: String) {
		ByteArrayInputStream(actual.toByteArray()).use {
			DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(it)
		}
	}
}