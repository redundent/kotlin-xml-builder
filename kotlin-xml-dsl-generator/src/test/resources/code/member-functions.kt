@file:Suppress("PropertyName", "ReplaceArrayOfWithLiteral", "LocalVariableName", "FunctionName", "RemoveRedundantBackticks")

package org.redundent.generated

import org.redundent.kotlin.xml.*

@XmlType(childOrder = arrayOf("comments",
		"sub-type"))
open class `MemberType`(nodeName: String) : Node(nodeName) {
	var `id`: kotlin.String? by attributes

	fun `comments`(value: kotlin.String) {
		"comments"(value)
	}

	fun `sub-type`(__block__: `MemberType`.`SubType`.() -> Unit): `SubType` {
		val `sub-type` = `SubType`()
		`sub-type`.apply(__block__)
		return `sub-type`
	}

	inner class `SubType` : Node("SubType") {
		fun `sub-type-element`(value: kotlin.String) {
			"sub-type-element"(value)
		}
	}
}