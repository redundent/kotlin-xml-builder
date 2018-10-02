@file:Suppress("PropertyName", "ReplaceArrayOfWithLiteral", "LocalVariableName", "FunctionName", "RemoveRedundantBackticks")

package org.redundent.generated

import org.redundent.kotlin.xml.*

open class `Top`(nodeName: String) : Node(nodeName) {
	inner class `First` : Node("First") {
		var `Id`: kotlin.Long by attributes

		inner class `Second` : Node("Second") {
			var `Name`: kotlin.String? by attributes
		}
	}

	@JvmOverloads
	fun `First`.`second`(`Name`: kotlin.String? = null,
						 __block__: `First`.`Second`.() -> Unit) {
		val `second` = `Second`()
		`second`.apply {
			if (`Name` != null) {
				this.`Name` = `Name`
			}
		}
		`second`.apply(__block__)
		this.addNode(`second`)
	}
}

fun `Top`.`first`(`Id`: kotlin.Long,
				  __block__: `Top`.`First`.() -> Unit) {
	val `first` = `First`()
	`first`.apply {
		this.`Id` = `Id`
	}
	`first`.apply(__block__)
	this.addNode(`first`)
}

fun `top`(__block__: `Top`.() -> Unit): `Top` {
	val `top` = `Top`("top")
	`top`.apply(__block__)
	return `top`
}