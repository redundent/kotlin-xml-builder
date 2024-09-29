package org.redundent.kotlin.xml

import kotlin.test.Test

class NodeJvmTest : TestBase() {
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
			"addElementsAfter",
			xml,
			PrintOptions(
				singleLineTextElements = true,
				useSelfClosingTags = true
			)
		)
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
			"addElementsBefore",
			xml,
			PrintOptions(
				singleLineTextElements = true,
				useSelfClosingTags = true
			)
		)
	}
}
