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
	fun equalsNull() {
		val text = CDATAElement("test")

		assertFalse(text.equals(null))
	}

	@Test
	fun equalsDifferentType() {
		val text = CDATAElement("test")
		val other = TextElement("test")

		// TODO why does this only pass on JVM?
		assertNotEquals(actual = other, illegal = text)
		//assertNotEquals(actual = text, illegal = other)
	}

	@Test
	fun equalsDifferentText() {
		val text1 = CDATAElement("text1")
		val text2 = CDATAElement("text2")

		assertNotEquals(text1, text2)
		assertNotEquals(text2, text1)
	}

	@Test
	fun testEquality() {
		val text1 = CDATAElement("text1")
		val text2 = CDATAElement("text1")

		assertEquals(text1, text2)
		assertEquals(text2, text1)
	}
}
