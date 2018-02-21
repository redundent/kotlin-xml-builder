@file:Suppress("PropertyName", "ReplaceArrayOfWithLiteral", "LocalVariableName", "FunctionName", "RemoveEmptyClassBody")

package org.redundent.generated

import org.redundent.kotlin.xml.*

abstract class `BaseType`(nodeName: String) : Node(nodeName) {
	var `Name`: kotlin.String? by attributes
}

open class `A`(nodeName: String) : org.redundent.generated.`BaseType`(nodeName) {
	var `Top`: kotlin.String? by attributes
}

open class `B`(nodeName: String) : org.redundent.generated.`BaseType`(nodeName) {
	var `Middle`: kotlin.String? by attributes
}

fun `a`(`Top`: kotlin.String? = null,
		`Name`: kotlin.String? = null,
		__block__: `A`.() -> Unit): `A` {
	val `a` = `A`("a")
	`a`.apply {
		if (`Top` != null) {
			this.`Top` = `Top`
		}
		if (`Name` != null) {
			this.`Name` = `Name`
		}
	}
	`a`.apply(__block__)
	return `a`
}

fun `b`(`Middle`: kotlin.String? = null,
		`Name`: kotlin.String? = null,
		__block__: `B`.() -> Unit): `B` {
	val `b` = `B`("b")
	`b`.apply {
		if (`Middle` != null) {
			this.`Middle` = `Middle`
		}
		if (`Name` != null) {
			this.`Name` = `Name`
		}
	}
	`b`.apply(__block__)
	return `b`
}