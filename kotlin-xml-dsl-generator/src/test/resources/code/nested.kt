@file:Suppress("PropertyName", "LocalVariableName", "FunctionName", "RedundantVisibilityModifier")

package org.redundent.generated

import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.jvm.JvmOverloads
import org.redundent.kotlin.xml.Node

public open class Top(
  nodeName: String,
) : Node(nodeName) {
  public inner class First : Node("First") {
    public var Id: Long
      get() = get("Id")!!
      set(`value`) {
        set("Id", `value`)
      }

    public inner class Second : Node("Second") {
      public var Name: String?
        get() = get("Name")
        set(`value`) {
          set("Name", `value`)
        }
    }
  }
}

@JvmOverloads
public fun Top.First.second(Name: String? = null, __block__: Top.First.Second.() -> Unit) {
  val second = Second()
  second.apply {
    if (Name != null) {
      this.Name = Name
    }
  }
  second.apply(__block__)
  this.addNode(second)
}

public fun Top.first(Id: Long, __block__: Top.First.() -> Unit) {
  val first = First()
  first.apply {
    this.Id = Id
  }
  first.apply(__block__)
  this.addNode(first)
}

public fun top(__block__: Top.() -> Unit): Top {
  val top = Top("top")
  top.apply(__block__)
  return top
}
