package org.redundent.kotlin.xml

import org.junit.Test

class OrderedNodesTest : TestBase() {
	@Test
	fun correctOrder() {
		val xml = structured {
			second()
			first()
		}

		validate(xml)
	}

	@XmlType(childOrder = ["first", "second"])
	inner class Structured internal constructor() : Node("xml") {
		fun first() = "first"()
		fun second() = "second"()
	}

	private fun structured(block: Structured.() -> Unit): Structured {
		return Structured().apply(block)
	}
}
