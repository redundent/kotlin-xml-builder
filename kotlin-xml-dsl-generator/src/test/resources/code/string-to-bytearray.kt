@file:Suppress("PropertyName", "ReplaceArrayOfWithLiteral", "LocalVariableName", "FunctionName", "RemoveRedundantBackticks")

package org.redundent.generated

import org.redundent.kotlin.xml.*

open class `StringToByteArray`(nodeName: String) : Node(nodeName) {
	var `value`: kotlin.ByteArray? by attributes
}

@JvmOverloads
fun `String-To-ByteArray`(`value`: kotlin.ByteArray? = null,
						  __block__: `StringToByteArray`.() -> Unit): `StringToByteArray` {
	val `String-To-ByteArray` = `StringToByteArray`("String-To-ByteArray")
	`String-To-ByteArray`.apply {
		if (`value` != null) {
			this.`value` = `value`
		}
	}
	`String-To-ByteArray`.apply(__block__)
	return `String-To-ByteArray`
}