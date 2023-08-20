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
import kotlin.jvm.JvmOverloads
import org.redundent.kotlin.xml.Node

public open class Attributes(
  nodeName: String,
) : Node(nodeName) {
  public var uri: String?
    get() = get("uri")
    set(`value`) {
      set("uri", `value`)
    }

  public var base64Binary: ByteArray?
    get() = get("base64Binary")
    set(`value`) {
      set("base64Binary", `value`)
    }

  public var boolean: Boolean?
    get() = get("boolean")
    set(`value`) {
      set("boolean", `value`)
    }

  public var byte: Byte?
    get() = get("byte")
    set(`value`) {
      set("byte", `value`)
    }

  public var date: Date?
    get() = get("date")
    set(`value`) {
      set("date", `value`)
    }

  public var dateTime: Date?
    get() = get("dateTime")
    set(`value`) {
      set("dateTime", `value`)
    }

  public var decimal: BigDecimal?
    get() = get("decimal")
    set(`value`) {
      set("decimal", `value`)
    }

  public var double: Double?
    get() = get("double")
    set(`value`) {
      set("double", `value`)
    }

  public var duration: Duration?
    get() = get("duration")
    set(`value`) {
      set("duration", `value`)
    }

  public var float: Float?
    get() = get("float")
    set(`value`) {
      set("float", `value`)
    }

  public var gDay: Date?
    get() = get("gDay")
    set(`value`) {
      set("gDay", `value`)
    }

  public var gMonth: Date?
    get() = get("gMonth")
    set(`value`) {
      set("gMonth", `value`)
    }

  public var gMonthDay: Date?
    get() = get("gMonthDay")
    set(`value`) {
      set("gMonthDay", `value`)
    }

  public var gYear: Date?
    get() = get("gYear")
    set(`value`) {
      set("gYear", `value`)
    }

  public var gYearMonth: Date?
    get() = get("gYearMonth")
    set(`value`) {
      set("gYearMonth", `value`)
    }

  public var hexBinary: ByteArray?
    get() = get("hexBinary")
    set(`value`) {
      set("hexBinary", `value`)
    }

  public var int: Int?
    get() = get("int")
    set(`value`) {
      set("int", `value`)
    }

  public var integer: Int?
    get() = get("integer")
    set(`value`) {
      set("integer", `value`)
    }

  public var long: Long?
    get() = get("long")
    set(`value`) {
      set("long", `value`)
    }

  public var negativeInteger: Int?
    get() = get("negativeInteger")
    set(`value`) {
      set("negativeInteger", `value`)
    }

  public var nonNegativeInteger: Int?
    get() = get("nonNegativeInteger")
    set(`value`) {
      set("nonNegativeInteger", `value`)
    }

  public var nonPositiveInteger: Int?
    get() = get("nonPositiveInteger")
    set(`value`) {
      set("nonPositiveInteger", `value`)
    }

  public var positiveInteger: Int?
    get() = get("positiveInteger")
    set(`value`) {
      set("positiveInteger", `value`)
    }

  public var string: String?
    get() = get("string")
    set(`value`) {
      set("string", `value`)
    }

  public var short: Short?
    get() = get("short")
    set(`value`) {
      set("short", `value`)
    }

  public var time: Date?
    get() = get("time")
    set(`value`) {
      set("time", `value`)
    }

  public var token: String?
    get() = get("token")
    set(`value`) {
      set("token", `value`)
    }

  public var unsignedByte: Short?
    get() = get("unsignedByte")
    set(`value`) {
      set("unsignedByte", `value`)
    }

  public var unsignedInt: Long?
    get() = get("unsignedInt")
    set(`value`) {
      set("unsignedInt", `value`)
    }

  public var unsignedLong: Int?
    get() = get("unsignedLong")
    set(`value`) {
      set("unsignedLong", `value`)
    }

  public var unsignedShort: Int?
    get() = get("unsignedShort")
    set(`value`) {
      set("unsignedShort", `value`)
    }
}

@JvmOverloads
public fun attributes(
  uri: String? = null,
  base64Binary: ByteArray? = null,
  boolean: Boolean? = null,
  byte: Byte? = null,
  date: Date? = null,
  dateTime: Date? = null,
  decimal: BigDecimal? = null,
  double: Double? = null,
  duration: Duration? = null,
  float: Float? = null,
  gDay: Date? = null,
  gMonth: Date? = null,
  gMonthDay: Date? = null,
  gYear: Date? = null,
  gYearMonth: Date? = null,
  hexBinary: ByteArray? = null,
  int: Int? = null,
  integer: Int? = null,
  long: Long? = null,
  negativeInteger: Int? = null,
  nonNegativeInteger: Int? = null,
  nonPositiveInteger: Int? = null,
  positiveInteger: Int? = null,
  string: String? = null,
  short: Short? = null,
  time: Date? = null,
  token: String? = null,
  unsignedByte: Short? = null,
  unsignedInt: Long? = null,
  unsignedLong: Int? = null,
  unsignedShort: Int? = null,
  __block__: Attributes.() -> Unit,
): Attributes {
  val attributes = Attributes("attributes")
  attributes.apply {
    if (uri != null) {
      this.uri = uri
    }
    if (base64Binary != null) {
      this.base64Binary = base64Binary
    }
    if (boolean != null) {
      this.boolean = boolean
    }
    if (byte != null) {
      this.byte = byte
    }
    if (date != null) {
      this.date = date
    }
    if (dateTime != null) {
      this.dateTime = dateTime
    }
    if (decimal != null) {
      this.decimal = decimal
    }
    if (double != null) {
      this.double = double
    }
    if (duration != null) {
      this.duration = duration
    }
    if (float != null) {
      this.float = float
    }
    if (gDay != null) {
      this.gDay = gDay
    }
    if (gMonth != null) {
      this.gMonth = gMonth
    }
    if (gMonthDay != null) {
      this.gMonthDay = gMonthDay
    }
    if (gYear != null) {
      this.gYear = gYear
    }
    if (gYearMonth != null) {
      this.gYearMonth = gYearMonth
    }
    if (hexBinary != null) {
      this.hexBinary = hexBinary
    }
    if (int != null) {
      this.int = int
    }
    if (integer != null) {
      this.integer = integer
    }
    if (long != null) {
      this.long = long
    }
    if (negativeInteger != null) {
      this.negativeInteger = negativeInteger
    }
    if (nonNegativeInteger != null) {
      this.nonNegativeInteger = nonNegativeInteger
    }
    if (nonPositiveInteger != null) {
      this.nonPositiveInteger = nonPositiveInteger
    }
    if (positiveInteger != null) {
      this.positiveInteger = positiveInteger
    }
    if (string != null) {
      this.string = string
    }
    if (short != null) {
      this.short = short
    }
    if (time != null) {
      this.time = time
    }
    if (token != null) {
      this.token = token
    }
    if (unsignedByte != null) {
      this.unsignedByte = unsignedByte
    }
    if (unsignedInt != null) {
      this.unsignedInt = unsignedInt
    }
    if (unsignedLong != null) {
      this.unsignedLong = unsignedLong
    }
    if (unsignedShort != null) {
      this.unsignedShort = unsignedShort
    }
  }
  attributes.apply(__block__)
  return attributes
}
