@file:Suppress("PropertyName", "LocalVariableName", "FunctionName", "RedundantVisibilityModifier")

package org.redundent.generated

import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import org.redundent.kotlin.xml.Node

public open class Root(
  nodeName: String,
) : Node(nodeName)

public fun Root.name(`value`: String) {
  "name"(`value`)
}

public fun root(__block__: Root.() -> Unit): Root {
  val root = Root("root")
  root.apply(__block__)
  return root
}
