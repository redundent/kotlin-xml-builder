@file:Suppress("PropertyName", "ReplaceArrayOfWithLiteral", "LocalVariableName", "FunctionName", "RemoveRedundantBackticks")

package org.redundent.generated

import org.redundent.kotlin.xml.*

open class `RootType`(nodeName: String) : Node(nodeName) {
	var `id`: kotlin.String?
		get() = get("id")
		set(value) { set("id", value) }
}

fun `RootType`.`comments`(value: kotlin.String) {
	"comments"(value)
}