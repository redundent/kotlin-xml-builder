@file:Suppress("PropertyName", "LocalVariableName", "FunctionName", "RedundantVisibilityModifier")

package org.redundent.generated

import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.jvm.JvmOverloads
import org.redundent.kotlin.xml.Node

public abstract class BaseType(
  nodeName: String,
) : Node(nodeName) {
  public var Name: String?
    get() = get("Name")
    set(`value`) {
      set("Name", `value`)
    }
}

public open class A(
  nodeName: String,
) : BaseType(nodeName) {
  public var Top: String?
    get() = get("Top")
    set(`value`) {
      set("Top", `value`)
    }
}

@JvmOverloads
public fun a(
  Top: String? = null,
  Name: String? = null,
  __block__: A.() -> Unit,
): A {
  val a = A("a")
  a.apply {
    if (Top != null) {
      this.Top = Top
    }
    if (Name != null) {
      this.Name = Name
    }
  }
  a.apply(__block__)
  return a
}

public open class B(
  nodeName: String,
) : BaseType(nodeName) {
  public var Middle: String?
    get() = get("Middle")
    set(`value`) {
      set("Middle", `value`)
    }
}

@JvmOverloads
public fun b(
  Middle: String? = null,
  Name: String? = null,
  __block__: B.() -> Unit,
): B {
  val b = B("b")
  b.apply {
    if (Middle != null) {
      this.Middle = Middle
    }
    if (Name != null) {
      this.Name = Name
    }
  }
  b.apply(__block__)
  return b
}
