@file:Suppress("PropertyName", "LocalVariableName", "FunctionName", "RedundantVisibilityModifier")

package org.redundent.generated

import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import org.redundent.kotlin.xml.Node

public open class DuplicateNameContainer(
  nodeName: String,
) : Node(nodeName) {
  public inner class DuplicateName : org.redundent.generated.DuplicateName("DuplicateName")
}

public
    fun DuplicateNameContainer.DuplicateName(__block__: DuplicateNameContainer.DuplicateName.() -> Unit) {
  val DuplicateName = DuplicateName()
  DuplicateName.apply(__block__)
  this.addNode(DuplicateName)
}

public open class DuplicateNames(
  nodeName: String,
) : DuplicateNameContainer(nodeName)

public fun DuplicateNames(__block__: DuplicateNames.() -> Unit): DuplicateNames {
  val DuplicateNames = DuplicateNames("DuplicateNames")
  DuplicateNames.apply(__block__)
  return DuplicateNames
}

public open class DuplicateName(
  nodeName: String,
) : Node(nodeName)
