@file:Suppress("PropertyName", "LocalVariableName", "FunctionName", "RedundantVisibilityModifier")

package org.redundent.generated

import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.XmlType

@XmlType(childOrder = arrayOf("Child1",
		"child2",
		"CHILD3"))
public open class ChildPropOrder(
  nodeName: String,
) : Node(nodeName)

public fun ChildPropOrder.Child1(`value`: String) {
  "Child1"(`value`)
}

public fun ChildPropOrder.child2(`value`: String) {
  "child2"(`value`)
}

public fun ChildPropOrder.CHILD3(`value`: String) {
  "CHILD3"(`value`)
}

public fun ChildPropOrder(__block__: ChildPropOrder.() -> Unit): ChildPropOrder {
  val ChildPropOrder = ChildPropOrder("ChildPropOrder")
  ChildPropOrder.apply(__block__)
  return ChildPropOrder
}
