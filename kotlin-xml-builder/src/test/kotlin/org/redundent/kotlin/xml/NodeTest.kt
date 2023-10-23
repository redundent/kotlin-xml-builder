package org.redundent.kotlin.xml

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertSame

class NodeTest : TestBase() {
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
	fun `equals slight difference`() {
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
	fun `set null`() {
		val xml = xml("root")

		xml.set("myAttr", "myValue")
		assertEquals("myValue" as String?, xml.get("myAttr"))

		xml.set("myAttr", null)
		assertFalse(xml.hasAttribute("myAttr"))
	}

	@Test
	fun `addElements varargs`() {
		val xml = xml("root")

		val text = TextElement("test")
		val cdata = CDATAElement("cdata")

		xml.addElements(text, cdata)

		assertSame(text, xml.children[0], "first child is text element")
		assertSame(cdata, xml.children[1], "second child is cdata element")
	}

	@Test
	fun `addElements iterable`() {
		val xml = xml("root")

		val text = TextElement("test")
		val cdata = CDATAElement("cdata")

		xml.addElements(listOf(text, cdata))

		assertSame(text, xml.children[0], "first child is text element")
		assertSame(cdata, xml.children[1], "second child is cdata element")
	}

	@Test(expected = IllegalArgumentException::class)
	fun `addElementsAfter not found`() {
		val xml = xml("root")
		val text = TextElement("test")
		xml.addElements(text)

		val after = CDATAElement("cdata")

		xml.addElementsAfter(after, TextElement("new"))
	}

	@Test
	fun addElementsAfter() {
		val after = node("third")

		val xml = xml("root") {
			"first"("")
			"second"("")
			addElement(after)
			"fourth"("")
			"fifth"("")
		}

		xml.addElementsAfter(
			after,
			node("new1"),
			node("new2")
		)

		validate(
			xml,
			PrintOptions(
				singleLineTextElements = true,
				useSelfClosingTags = true
			)
		)
	}

	@Test(expected = IllegalArgumentException::class)
	fun `addElementsBefore not found`() {
		val xml = xml("root")
		val text = TextElement("test")
		xml.addElements(text)

		val before = CDATAElement("cdata")

		xml.addElementsBefore(before, TextElement("new"))
	}

	@Test
	fun addElementsBefore() {
		val before = node("third")

		val xml = xml("root") {
			"first"("")
			"second"("")
			addElement(before)
			"fourth"("")
			"fifth"("")
		}

		xml.addElementsBefore(
			before,
			node("new1"),
			node("new2")
		)

		validate(
			xml,
			PrintOptions(
				singleLineTextElements = true,
				useSelfClosingTags = true
			)
		)
	}
}
