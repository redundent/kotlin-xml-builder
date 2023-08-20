@file:Suppress("PropertyName", "LocalVariableName", "FunctionName", "RedundantVisibilityModifier")

package org.redundent.generated

import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.jvm.JvmOverloads
import org.redundent.kotlin.xml.Node

public open class Code(
  nodeName: String,
) : Node(nodeName) {
  init {
    xmlns = "http://code.redundent.org/schemas"
  }

  public var id: Long?
    get() = get("id")
    set(`value`) {
      set("id", `value`)
    }
}

@JvmOverloads
public fun code(id: Long? = null, __block__: Code.() -> Unit): Code {
  val code = Code("code")
  code.apply {
    if (id != null) {
      this.id = id
    }
  }
  code.apply(__block__)
  return code
}
