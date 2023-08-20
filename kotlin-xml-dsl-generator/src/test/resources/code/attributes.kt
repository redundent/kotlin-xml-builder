@file:Suppress("PropertyName", "ReplaceArrayOfWithLiteral", "LocalVariableName", "FunctionName", "RemoveRedundantBackticks")

package org.redundent.generated

import org.redundent.kotlin.xml.*

open class `Attributes`(nodeName: String) : Node(nodeName) {
	var `uri`: kotlin.String?
		get() = get("uri")
		set(value) { set("uri", value) }
	var `base64Binary`: kotlin.ByteArray?
		get() = get("base64Binary")
		set(value) { set("base64Binary", value) }
	var `boolean`: kotlin.Boolean?
		get() = get("boolean")
		set(value) { set("boolean", value) }
	var `byte`: kotlin.Byte?
		get() = get("byte")
		set(value) { set("byte", value) }
	var `date`: java.util.Date?
		get() = get("date")
		set(value) { set("date", value) }
	var `dateTime`: java.util.Date?
		get() = get("dateTime")
		set(value) { set("dateTime", value) }
	var `decimal`: java.math.BigDecimal?
		get() = get("decimal")
		set(value) { set("decimal", value) }
	var `double`: kotlin.Double?
		get() = get("double")
		set(value) { set("double", value) }
	var `duration`: javax.xml.datatype.Duration?
		get() = get("duration")
		set(value) { set("duration", value) }
	var `float`: kotlin.Float?
		get() = get("float")
		set(value) { set("float", value) }
	var `gDay`: java.util.Date?
		get() = get("gDay")
		set(value) { set("gDay", value) }
	var `gMonth`: java.util.Date?
		get() = get("gMonth")
		set(value) { set("gMonth", value) }
	var `gMonthDay`: java.util.Date?
		get() = get("gMonthDay")
		set(value) { set("gMonthDay", value) }
	var `gYear`: java.util.Date?
		get() = get("gYear")
		set(value) { set("gYear", value) }
	var `gYearMonth`: java.util.Date?
		get() = get("gYearMonth")
		set(value) { set("gYearMonth", value) }
	var `hexBinary`: kotlin.ByteArray?
		get() = get("hexBinary")
		set(value) { set("hexBinary", value) }
	var `int`: kotlin.Int?
		get() = get("int")
		set(value) { set("int", value) }
	var `integer`: kotlin.Int?
		get() = get("integer")
		set(value) { set("integer", value) }
	var `long`: kotlin.Long?
		get() = get("long")
		set(value) { set("long", value) }
	var `negativeInteger`: kotlin.Int?
		get() = get("negativeInteger")
		set(value) { set("negativeInteger", value) }
	var `nonNegativeInteger`: kotlin.Int?
		get() = get("nonNegativeInteger")
		set(value) { set("nonNegativeInteger", value) }
	var `nonPositiveInteger`: kotlin.Int?
		get() = get("nonPositiveInteger")
		set(value) { set("nonPositiveInteger", value) }
	var `positiveInteger`: kotlin.Int?
		get() = get("positiveInteger")
		set(value) { set("positiveInteger", value) }
	var `string`: kotlin.String?
		get() = get("string")
		set(value) { set("string", value) }
	var `short`: kotlin.Short?
		get() = get("short")
		set(value) { set("short", value) }
	var `time`: java.util.Date?
		get() = get("time")
		set(value) { set("time", value) }
	var `token`: kotlin.String?
		get() = get("token")
		set(value) { set("token", value) }
	var `unsignedByte`: kotlin.Short?
		get() = get("unsignedByte")
		set(value) { set("unsignedByte", value) }
	var `unsignedInt`: kotlin.Long?
		get() = get("unsignedInt")
		set(value) { set("unsignedInt", value) }
	var `unsignedLong`: kotlin.Int?
		get() = get("unsignedLong")
		set(value) { set("unsignedLong", value) }
	var `unsignedShort`: kotlin.Int?
		get() = get("unsignedShort")
		set(value) { set("unsignedShort", value) }
}

@JvmOverloads
fun `attributes`(`uri`: kotlin.String? = null,
				 `base64Binary`: kotlin.ByteArray? = null,
				 `boolean`: kotlin.Boolean? = null,
				 `byte`: kotlin.Byte? = null,
				 `date`: java.util.Date? = null,
				 `dateTime`: java.util.Date? = null,
				 `decimal`: java.math.BigDecimal? = null,
				 `double`: kotlin.Double? = null,
				 `duration`: javax.xml.datatype.Duration? = null,
				 `float`: kotlin.Float? = null,
				 `gDay`: java.util.Date? = null,
				 `gMonth`: java.util.Date? = null,
				 `gMonthDay`: java.util.Date? = null,
				 `gYear`: java.util.Date? = null,
				 `gYearMonth`: java.util.Date? = null,
				 `hexBinary`: kotlin.ByteArray? = null,
				 `int`: kotlin.Int? = null,
				 `integer`: kotlin.Int? = null,
				 `long`: kotlin.Long? = null,
				 `negativeInteger`: kotlin.Int? = null,
				 `nonNegativeInteger`: kotlin.Int? = null,
				 `nonPositiveInteger`: kotlin.Int? = null,
				 `positiveInteger`: kotlin.Int? = null,
				 `string`: kotlin.String? = null,
				 `short`: kotlin.Short? = null,
				 `time`: java.util.Date? = null,
				 `token`: kotlin.String? = null,
				 `unsignedByte`: kotlin.Short? = null,
				 `unsignedInt`: kotlin.Long? = null,
				 `unsignedLong`: kotlin.Int? = null,
				 `unsignedShort`: kotlin.Int? = null,
				 __block__: `Attributes`.() -> Unit): `Attributes` {
	val `attributes` = `Attributes`("attributes")
	`attributes`.apply {
		if (`uri` != null) {
			this.`uri` = `uri`
		}
		if (`base64Binary` != null) {
			this.`base64Binary` = `base64Binary`
		}
		if (`boolean` != null) {
			this.`boolean` = `boolean`
		}
		if (`byte` != null) {
			this.`byte` = `byte`
		}
		if (`date` != null) {
			this.`date` = `date`
		}
		if (`dateTime` != null) {
			this.`dateTime` = `dateTime`
		}
		if (`decimal` != null) {
			this.`decimal` = `decimal`
		}
		if (`double` != null) {
			this.`double` = `double`
		}
		if (`duration` != null) {
			this.`duration` = `duration`
		}
		if (`float` != null) {
			this.`float` = `float`
		}
		if (`gDay` != null) {
			this.`gDay` = `gDay`
		}
		if (`gMonth` != null) {
			this.`gMonth` = `gMonth`
		}
		if (`gMonthDay` != null) {
			this.`gMonthDay` = `gMonthDay`
		}
		if (`gYear` != null) {
			this.`gYear` = `gYear`
		}
		if (`gYearMonth` != null) {
			this.`gYearMonth` = `gYearMonth`
		}
		if (`hexBinary` != null) {
			this.`hexBinary` = `hexBinary`
		}
		if (`int` != null) {
			this.`int` = `int`
		}
		if (`integer` != null) {
			this.`integer` = `integer`
		}
		if (`long` != null) {
			this.`long` = `long`
		}
		if (`negativeInteger` != null) {
			this.`negativeInteger` = `negativeInteger`
		}
		if (`nonNegativeInteger` != null) {
			this.`nonNegativeInteger` = `nonNegativeInteger`
		}
		if (`nonPositiveInteger` != null) {
			this.`nonPositiveInteger` = `nonPositiveInteger`
		}
		if (`positiveInteger` != null) {
			this.`positiveInteger` = `positiveInteger`
		}
		if (`string` != null) {
			this.`string` = `string`
		}
		if (`short` != null) {
			this.`short` = `short`
		}
		if (`time` != null) {
			this.`time` = `time`
		}
		if (`token` != null) {
			this.`token` = `token`
		}
		if (`unsignedByte` != null) {
			this.`unsignedByte` = `unsignedByte`
		}
		if (`unsignedInt` != null) {
			this.`unsignedInt` = `unsignedInt`
		}
		if (`unsignedLong` != null) {
			this.`unsignedLong` = `unsignedLong`
		}
		if (`unsignedShort` != null) {
			this.`unsignedShort` = `unsignedShort`
		}
	}
	`attributes`.apply(__block__)
	return `attributes`
}