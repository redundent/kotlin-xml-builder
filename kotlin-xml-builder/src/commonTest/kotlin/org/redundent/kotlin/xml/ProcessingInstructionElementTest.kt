package org.redundent.kotlin.xml

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

class ProcessingInstructionElementTest {
	@Test
	fun testHashCode() {
		val text = ProcessingInstructionElement("test", emptyMap())

		assertNotEquals(text.text.hashCode(), text.hashCode(), "ProcessingInstructionElement hashcode is not just text.hashCode()")
	}

	@Test
	fun equals_null() {
		val text = ProcessingInstructionElement("test", emptyMap())

		assertFalse(text.equals(null))
	}

	@Test
	fun equals_different_text() {
		val text1 = ProcessingInstructionElement("text1", emptyMap())
		val text2 = ProcessingInstructionElement("text2", emptyMap())

		assertNotEquals(text1, text2)
		assertNotEquals(text2, text1)
	}

	@Test
	fun testEquals() {
		val text1 = ProcessingInstructionElement("text1", emptyMap())
		val text2 = ProcessingInstructionElement("text1", emptyMap())

		assertEquals(text1, text2)
		assertEquals(text2, text1)
	}

	@Test
	fun equals_attributes_same_order() {
		val text1 = ProcessingInstructionElement("text1", linkedMapOf("attr1" to "value1", "attr2" to "value2"))
		val text2 = ProcessingInstructionElement("text1", linkedMapOf("attr1" to "value1", "attr2" to "value2"))

		assertEquals(text1, text2)
		assertEquals(text2, text1)
	}

	@Test
	fun equals_attributes_different_order() {
		val text1 = ProcessingInstructionElement("text1", linkedMapOf("attr1" to "value1", "attr2" to "value2"))
		val text2 = ProcessingInstructionElement("text1", linkedMapOf("attr2" to "value2", "attr1" to "value1"))

		assertEquals(text1, text2)
		assertEquals(text2, text1)
	}
}
