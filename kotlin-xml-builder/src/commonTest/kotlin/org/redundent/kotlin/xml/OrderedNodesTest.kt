package org.redundent.kotlin.xml

import kotlin.test.Test

class OrderedNodesTest : TestBase() {
	@Test
	fun correctOrder() {
		val xml = structured {
			second()
			first()
		}

		validate(xml, PrintOptions(), "OrderedNodesTest/correctOrder.xml")
	}

	class Structured internal constructor() : Node("xml") {
		init {
			childOrder = arrayOf("first", "second")
		}

		fun first() = "first"()
		fun second() = "second"()
	}

	private fun structured(block: Structured.() -> Unit): Structured {
		return Structured().apply(block)
	}
}
