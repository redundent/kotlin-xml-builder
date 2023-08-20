@file:Suppress("PropertyName", "LocalVariableName", "FunctionName", "RedundantVisibilityModifier")

package org.redundent.generated

import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.XmlType

@XmlType(childOrder = arrayOf("comments",
		"sub-type"))
public open class MemberType(
  nodeName: String,
) : Node(nodeName) {
  public var id: String?
    get() = get("id")
    set(`value`) {
      set("id", `value`)
    }

  public fun comments(`value`: String) {
    "comments"(`value`)
  }

  public fun `sub-type`(__block__: SubType.() -> Unit): SubType {
    val `sub-type` = SubType()
    `sub-type`.apply(__block__)
    return `sub-type`
  }

  public inner class SubType : Node("SubType") {
    public fun `sub-type-element`(`value`: String) {
      "sub-type-element"(`value`)
    }
  }
}
