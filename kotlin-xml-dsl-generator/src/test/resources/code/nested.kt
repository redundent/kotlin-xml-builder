@file:Suppress("PropertyName", "ReplaceArrayOfWithLiteral", "LocalVariableName")

package org.redundent.generated

import org.redundent.kotlin.xml.*

open class `Top`(nodeName: String) : Node(nodeName) {
}

fun `Top`.`first`(`Id`: kotlin.Long,
				  __block__: `First`.() -> Unit) {
	val `first` = `First`("first")
	`first`.apply {
		this.`Id` = `Id`
	}
	`first`.apply(__block__)
	this.addNode(`first`)
}

open class `First`(nodeName: String) : Node(nodeName) {
	var `Id`: kotlin.Long by attributes
}

fun `First`.`second`(`Name`: kotlin.String? = null,
					 __block__: `Second`.() -> Unit) {
	val `second` = `Second`("second")
	`second`.apply {
		if (`Name` != null) {
			this.`Name` = `Name`
		}
	}
	`second`.apply(__block__)
	this.addNode(`second`)
}

open class `Second`(nodeName: String) : Node(nodeName) {
	var `Name`: kotlin.String? by attributes
}

fun `top`(__block__: `Top`.() -> Unit): `Top` {
	val `top` = `Top`("top")
	`top`.apply(__block__)
	return `top`
}