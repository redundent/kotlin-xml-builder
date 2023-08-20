@file:Suppress("PropertyName", "LocalVariableName", "FunctionName", "RedundantVisibilityModifier")

package org.redundent.generated

import kotlin.String
import kotlin.Suppress
import org.redundent.kotlin.xml.Node

public open class RootType(
  nodeName: String,
) : Node(nodeName) {
  public var id: String?
    get() = get("id")
    set(`value`) {
      set("id", `value`)
    }
}

public fun RootType.comments(`value`: String) {
  "comments"(`value`)
}
