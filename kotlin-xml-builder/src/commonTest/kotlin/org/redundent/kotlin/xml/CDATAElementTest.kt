package org.redundent.kotlin.xml

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

class CDATAElementTest {
	@Test
	fun testHashCode() {
		val text = CDATAElement("test")

		assertNotEquals(text.text.hashCode(), text.hashCode(), "CDATA hashcode is not just text.hashCode()")
	}

	@Test
	fun equals_null() {
		val text = CDATAElement("test")

		assertFalse(text.equals(null))
	}

	@Test
	fun equals_different_text() {
		val text1 = CDATAElement("text1")
		val text2 = CDATAElement("text2")

		assertNotEquals(text1, text2)
		assertNotEquals(text2, text1)
	}

	@Test
	fun testEquals() {
		val text1 = CDATAElement("text1")
		val text2 = CDATAElement("text1")

		assertEquals(text1, text2)
		assertEquals(text2, text1)
	}
}
