@file:Suppress("PropertyName", "LocalVariableName", "FunctionName", "RedundantVisibilityModifier")

package org.redundent.generated

import java.math.BigDecimal
import java.util.Date
import javax.xml.datatype.Duration
import kotlin.Boolean
import kotlin.Byte
import kotlin.ByteArray
import kotlin.Double
import kotlin.Float
import kotlin.Int
import kotlin.Long
import kotlin.Short
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import org.redundent.kotlin.xml.Node

public open class AttributesRequired(
  nodeName: String,
) : Node(nodeName) {
  public var uri: String
    get() = get("uri")!!
    set(`value`) {
      set("uri", `value`)
    }

  public var base64Binary: ByteArray
    get() = get("base64Binary")!!
    set(`value`) {
      set("base64Binary", `value`)
    }

  public var boolean: Boolean
    get() = get("boolean")!!
    set(`value`) {
      set("boolean", `value`)
    }

  public var byte: Byte
    get() = get("byte")!!
    set(`value`) {
      set("byte", `value`)
    }

  public var date: Date
    get() = get("date")!!
    set(`value`) {
      set("date", `value`)
    }

  public var dateTime: Date
    get() = get("dateTime")!!
    set(`value`) {
      set("dateTime", `value`)
    }

  public var decimal: BigDecimal
    get() = get("decimal")!!
    set(`value`) {
      set("decimal", `value`)
    }

  public var double: Double
    get() = get("double")!!
    set(`value`) {
      set("double", `value`)
    }

  public var duration: Duration
    get() = get("duration")!!
    set(`value`) {
      set("duration", `value`)
    }

  public var float: Float
    get() = get("float")!!
    set(`value`) {
      set("float", `value`)
    }

  public var gDay: Date
    get() = get("gDay")!!
    set(`value`) {
      set("gDay", `value`)
    }

  public var gMonth: Date
    get() = get("gMonth")!!
    set(`value`) {
      set("gMonth", `value`)
    }

  public var gMonthDay: Date
    get() = get("gMonthDay")!!
    set(`value`) {
      set("gMonthDay", `value`)
    }

  public var gYear: Date
    get() = get("gYear")!!
    set(`value`) {
      set("gYear", `value`)
    }

  public var gYearMonth: Date
    get() = get("gYearMonth")!!
    set(`value`) {
      set("gYearMonth", `value`)
    }

  public var hexBinary: ByteArray
    get() = get("hexBinary")!!
    set(`value`) {
      set("hexBinary", `value`)
    }

  public var int: Int
    get() = get("int")!!
    set(`value`) {
      set("int", `value`)
    }

  public var integer: Int
    get() = get("integer")!!
    set(`value`) {
      set("integer", `value`)
    }

  public var long: Long
    get() = get("long")!!
    set(`value`) {
      set("long", `value`)
    }

  public var negativeInteger: Int
    get() = get("negativeInteger")!!
    set(`value`) {
      set("negativeInteger", `value`)
    }

  public var nonNegativeInteger: Int
    get() = get("nonNegativeInteger")!!
    set(`value`) {
      set("nonNegativeInteger", `value`)
    }

  public var nonPositiveInteger: Int
    get() = get("nonPositiveInteger")!!
    set(`value`) {
      set("nonPositiveInteger", `value`)
    }

  public var positiveInteger: Int
    get() = get("positiveInteger")!!
    set(`value`) {
      set("positiveInteger", `value`)
    }

  public var string: String
    get() = get("string")!!
    set(`value`) {
      set("string", `value`)
    }

  public var short: Short
    get() = get("short")!!
    set(`value`) {
      set("short", `value`)
    }

  public var time: Date
    get() = get("time")!!
    set(`value`) {
      set("time", `value`)
    }

  public var token: String
    get() = get("token")!!
    set(`value`) {
      set("token", `value`)
    }

  public var unsignedByte: Short
    get() = get("unsignedByte")!!
    set(`value`) {
      set("unsignedByte", `value`)
    }

  public var unsignedInt: Long
    get() = get("unsignedInt")!!
    set(`value`) {
      set("unsignedInt", `value`)
    }

  public var unsignedLong: Int
    get() = get("unsignedLong")!!
    set(`value`) {
      set("unsignedLong", `value`)
    }

  public var unsignedShort: Int
    get() = get("unsignedShort")!!
    set(`value`) {
      set("unsignedShort", `value`)
    }
}

public fun attributesRequired(
  uri: String,
  base64Binary: ByteArray,
  boolean: Boolean,
  byte: Byte,
  date: Date,
  dateTime: Date,
  decimal: BigDecimal,
  double: Double,
  duration: Duration,
  float: Float,
  gDay: Date,
  gMonth: Date,
  gMonthDay: Date,
  gYear: Date,
  gYearMonth: Date,
  hexBinary: ByteArray,
  int: Int,
  integer: Int,
  long: Long,
  negativeInteger: Int,
  nonNegativeInteger: Int,
  nonPositiveInteger: Int,
  positiveInteger: Int,
  string: String,
  short: Short,
  time: Date,
  token: String,
  unsignedByte: Short,
  unsignedInt: Long,
  unsignedLong: Int,
  unsignedShort: Int,
  __block__: AttributesRequired.() -> Unit,
): AttributesRequired {
  val attributesRequired = AttributesRequired("attributesRequired")
  attributesRequired.apply {
    this.uri = uri
    this.base64Binary = base64Binary
    this.boolean = boolean
    this.byte = byte
    this.date = date
    this.dateTime = dateTime
    this.decimal = decimal
    this.double = double
    this.duration = duration
    this.float = float
    this.gDay = gDay
    this.gMonth = gMonth
    this.gMonthDay = gMonthDay
    this.gYear = gYear
    this.gYearMonth = gYearMonth
    this.hexBinary = hexBinary
    this.int = int
    this.integer = integer
    this.long = long
    this.negativeInteger = negativeInteger
    this.nonNegativeInteger = nonNegativeInteger
    this.nonPositiveInteger = nonPositiveInteger
    this.positiveInteger = positiveInteger
    this.string = string
    this.short = short
    this.time = time
    this.token = token
    this.unsignedByte = unsignedByte
    this.unsignedInt = unsignedInt
    this.unsignedLong = unsignedLong
    this.unsignedShort = unsignedShort
  }
  attributesRequired.apply(__block__)
  return attributesRequired
}
