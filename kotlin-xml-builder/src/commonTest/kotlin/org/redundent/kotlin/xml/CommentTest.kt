package org.redundent.kotlin.xml

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals

class CommentTest {
	@Test
	fun test_hash_code() {
		val comment = Comment("test")

		assertEquals(comment.text.hashCode(), comment.hashCode())
	}

	@Test
	fun equals_null() {
		val comment = Comment("test")

		assertFalse(comment.equals(null))
	}

	@Test
	fun equals_different_type() {
		val comment = Comment("test")
		val other = CDATAElement("test")

		assertFalse(comment.equals(other))
	}

	@Test
	fun equals_different_text() {
		val comment1 = Comment("comment1")
		val comment2 = Comment("comment2")

		assertNotEquals(comment1, comment2)
		assertNotEquals(comment2, comment1)
	}

	@Test
	fun testEquals() {
		val comment1 = Comment("comment")
		val comment2 = Comment("comment")

		assertEquals(comment1, comment2)
		assertEquals(comment2, comment1)
	}
}
