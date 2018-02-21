@file:Suppress("PropertyName", "ReplaceArrayOfWithLiteral", "LocalVariableName", "FunctionName", "RemoveEmptyClassBody")

package org.redundent.generated

import org.redundent.kotlin.xml.*

open class `Code`(nodeName: String) : Node(nodeName) {
	init {
		xmlns = "http://code.redundent.org/schemas"
	}

	var `id`: kotlin.Long? by attributes
}

fun `code`(`id`: kotlin.Long? = null,
		   __block__: `Code`.() -> Unit): `Code` {
	val `code` = `Code`("code")
	`code`.apply {
		if (`id` != null) {
			this.`id` = `id`
		}
	}
	`code`.apply(__block__)
	return `code`
}