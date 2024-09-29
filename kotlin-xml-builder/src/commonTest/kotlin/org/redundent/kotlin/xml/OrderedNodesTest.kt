package org.redundent.kotlin.xml

import kotlin.test.Test

class OrderedNodesTest {

	@Test
	fun correctOrder() {
		val xml = structured {
			second()
			first()
		}

		if (isReflectionAvailable()) {
			validate(testResults.correctOrder, xml)
		} else {
			validate(testResults.incorrectOrder, xml)
		}
	}

	@XmlType(childOrder = ["first", "second"])
	inner class Structured internal constructor() : Node("xml") {
		fun first() = "first"()
		fun second() = "second"()
	}

	private fun structured(block: Structured.() -> Unit): Structured {
		return Structured().apply(block)
	}

	companion object
}
