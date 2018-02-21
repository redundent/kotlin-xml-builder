@file:Suppress("PropertyName", "ReplaceArrayOfWithLiteral", "LocalVariableName", "FunctionName", "RemoveEmptyClassBody")

package org.redundent.generated

import org.redundent.kotlin.xml.*

open class `MethodName`(nodeName: String) : Node(nodeName) {
}

fun `MethodName`.`type1`(__block__: `Type1`.() -> Unit) {
	val `type1` = `Type1`("type1")
	`type1`.apply(__block__)
	this.addNode(`type1`)
}

fun `MethodName`.`type2`(__block__: `Type2`.() -> Unit) {
	val `type2` = `Type2`("type2")
	`type2`.apply(__block__)
	this.addNode(`type2`)
}

fun `MethodName`.`type3`(__block__: `Type3`.() -> Unit) {
	val `type3` = `Type3`("type3")
	`type3`.apply(__block__)
	this.addNode(`type3`)
}

open class `Type1`(nodeName: String) : Node(nodeName) {
}

open class `Type2`(nodeName: String) : Node(nodeName) {
}

open class `Type3`(nodeName: String) : Node(nodeName) {
}

fun `Method-Name`(__block__: `MethodName`.() -> Unit): `MethodName` {
	val `Method-Name` = `MethodName`("Method-Name")
	`Method-Name`.apply(__block__)
	return `Method-Name`
}