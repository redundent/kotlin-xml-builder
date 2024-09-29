package org.redundent.kotlin.xml

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertSame

class NodeCommonTest {

	@Test
	fun equalsNull() {
		val xml = xml("test")

		assertFalse(xml.equals(null))
	}

	@Test
	fun equalsDifferentType() {
		val xml = xml("test")
		val other = TextElement("test")

		assertFalse(xml == other)
	}

	@Test
	fun equalsDifferentName() {
		val xml1 = xml("test1")
		val xml2 = xml("test2")

		assertNotEquals(xml1, xml2)
		assertNotEquals(xml2, xml1)
	}

	@Test
	fun equalsSame() {
		val xml1 = xml("complex_node", encoding = "utf-8", version = XmlVersion.V11) {
			xmlns = "https://test.com"
			namespace("t", "https://t.co")

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
			xmlns = "https://test.com"
			namespace("t", "https://t.co")

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
	fun equalsSlightDifference() {
		val xml1 = xml("complex_node", encoding = "utf-8", version = XmlVersion.V11) {
			xmlns = "https://test.com"
			namespace("t", "https://t.co")

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
			xmlns = "https://test.com"
			namespace("t", "https://t.co")

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

	@Suppress("ReplaceGetOrSet")
	@Test
	fun set() {
		val xml = xml("root")

		xml.set("myAttr", "myValue")

		assertEquals("myValue" as String?, xml.get("myAttr"))
	}

	@Suppress("ReplaceGetOrSet")
	@Test
	fun setNull() {
		val xml = xml("root")

		xml.set("myAttr", "myValue")
		assertEquals("myValue" as String?, xml.get("myAttr"))

		xml.set("myAttr", null)
		assertFalse(xml.hasAttribute("myAttr"))
	}

	@Test
	fun addElementsVarargs() {
		val xml = xml("root")

		val text = TextElement("test")
		val cdata = CDATAElement("cdata")

		xml.addElements(text, cdata)

		assertSame(text, xml.children[0], "first child is text element")
		assertSame(cdata, xml.children[1], "second child is cdata element")
	}

	@Test
	fun addElementsIterable() {
		val xml = xml("root")

		val text = TextElement("test")
		val cdata = CDATAElement("cdata")

		xml.addElements(listOf(text, cdata))

		assertSame(text, xml.children[0], "first child is text element")
		assertSame(cdata, xml.children[1], "second child is cdata element")
	}

	@Test
	fun addElementsAfterNotFound() {
		val xml = xml("root")
		val text = TextElement("test")
		xml.addElements(text)

		val after = CDATAElement("cdata")

		assertFailsWith<IllegalArgumentException> {
			xml.addElementsAfter(after, TextElement("new"))
		}
	}

	@Test
	fun addElementsBeforeNotFound() {
		val xml = xml("root")
		val text = TextElement("test")
		xml.addElements(text)

		val before = CDATAElement("cdata")

		assertFailsWith<IllegalArgumentException> {
			xml.addElementsBefore(before, TextElement("new"))
		}
	}
}
