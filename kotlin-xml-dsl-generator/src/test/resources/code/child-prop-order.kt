@file:Suppress("PropertyName", "ReplaceArrayOfWithLiteral", "LocalVariableName", "FunctionName")

package org.redundent.generated

import org.redundent.kotlin.xml.*

@XmlType(childOrder = arrayOf("Child1",
		"child2",
		"CHILD3"))
open class `ChildPropOrder`(nodeName: String) : Node(nodeName)

fun `ChildPropOrder`.`Child1`(value: kotlin.String) {
	"Child1"(value)
}

fun `ChildPropOrder`.`child2`(value: kotlin.String) {
	"child2"(value)
}

fun `ChildPropOrder`.`CHILD3`(value: kotlin.String) {
	"CHILD3"(value)
}

fun `ChildPropOrder`(__block__: `ChildPropOrder`.() -> Unit): `ChildPropOrder` {
	val `ChildPropOrder` = `ChildPropOrder`("ChildPropOrder")
	`ChildPropOrder`.apply(__block__)
	return `ChildPropOrder`
}