package org.redundent.kotlin.xml

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

class NodeTest {
	@Test
	fun `equals null`() {
		val xml = xml("test")

		assertFalse(xml.equals(null))
	}

	@Test
	fun `equals different type`() {
		val xml = xml("test")
		val other = TextElement("test")

		assertFalse(xml == other)
	}

	@Test
	fun `equals different name`() {
		val xml1 = xml("test1")
		val xml2 = xml("test2")

		assertNotEquals(xml1, xml2)
		assertNotEquals(xml2, xml1)
	}

	@Test
	fun equals() {
		val xml1 = xml("complex_node", encoding = "utf-8", version = XmlVersion.V11) {
			xmlns = "http://test.com"
			namespace("t", "http://t.co")

			globalProcessingInstruction("global_pi", "global" to "top_level")

			attribute("attr", "value")
			attribute("other_attr", "some text & more")

			processingInstruction("blah", "pi_attr" to "value")

			"child1"("text")

			"child2" {
				comment("comment1")
			}
		}

		val xml2 = xml("complex_node", encoding = "utf-8", version = XmlVersion.V11) {
			xmlns = "http://test.com"
			namespace("t", "http://t.co")

			globalProcessingInstruction("global_pi", "global" to "top_level")

			attribute("attr", "value")
			attribute("other_attr", "some text & more")

			processingInstruction("blah", "pi_attr" to "value")

			"child1"("text")

			"child2" {
				comment("comment1")
			}
		}

		assertEquals(xml1, xml2)
		assertEquals(xml2, xml1)
	}

	@Test
	fun `equals slight difference`() {
		val xml1 = xml("complex_node", encoding = "utf-8", version = XmlVersion.V11) {
			xmlns = "http://test.com"
			namespace("t", "http://t.co")

			globalProcessingInstruction("global_pi", "global" to "top_level")

			attribute("attr", "value")
			attribute("other_attr", "some text & more")

			processingInstruction("blah", "pi_attr" to "value")

			"child1"("text")

			"child2" {
				comment("comment1")
			}
		}

		val xml2 = xml("complex_node", encoding = "utf-8", version = XmlVersion.V11) {
			xmlns = "http://test.com"
			namespace("t", "http://t.co")

			globalProcessingInstruction("global_pi", "global" to "top_level")

			attribute("attr", "value")
			attribute("other_attr", "some text & more")

			processingInstruction("blah", "pi_attr" to "value")

			"child1"("text")

			"child2" {
				comment("comment2")
			}
		}

		assertNotEquals(xml1, xml2)
		assertNotEquals(xml2, xml1)
	}
}