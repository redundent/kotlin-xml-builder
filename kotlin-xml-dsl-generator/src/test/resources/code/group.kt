@file:Suppress("PropertyName", "ReplaceArrayOfWithLiteral", "LocalVariableName", "FunctionName")

package org.redundent.generated

import org.redundent.kotlin.xml.*

open class `Root`(nodeName: String) : Node(nodeName)

fun `Root`.`name`(value: kotlin.String) {
	"name"(value)
}

fun `root`(__block__: `Root`.() -> Unit): `Root` {
	val `root` = `Root`("root")
	`root`.apply(__block__)
	return `root`
}