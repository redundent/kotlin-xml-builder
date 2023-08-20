@file:Suppress("PropertyName", "LocalVariableName", "FunctionName", "RedundantVisibilityModifier")

package org.redundent.generated

import kotlin.ByteArray
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.jvm.JvmOverloads
import org.redundent.kotlin.xml.Node

public open class StringToByteArray(
  nodeName: String,
) : Node(nodeName) {
  public var `value`: ByteArray?
    get() = get("value")
    set(`value`) {
      set("value", `value`)
    }
}

@JvmOverloads
public fun `String-To-ByteArray`(`value`: ByteArray? = null,
    __block__: StringToByteArray.() -> Unit): StringToByteArray {
  val `String-To-ByteArray` = StringToByteArray("String-To-ByteArray")
  `String-To-ByteArray`.apply {
    if (`value` != null) {
      this.`value` = `value`
    }
  }
  `String-To-ByteArray`.apply(__block__)
  return `String-To-ByteArray`
}
