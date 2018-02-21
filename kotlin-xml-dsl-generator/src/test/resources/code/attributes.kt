@file:Suppress("PropertyName", "ReplaceArrayOfWithLiteral", "LocalVariableName", "FunctionName", "RemoveEmptyClassBody")

package org.redundent.generated

import org.redundent.kotlin.xml.*

open class `Attributes`(nodeName: String) : Node(nodeName) {
	var `uri`: kotlin.String? by attributes
	var `base64Binary`: kotlin.ByteArray? by attributes
	var `boolean`: kotlin.Boolean? by attributes
	var `byte`: kotlin.Byte? by attributes
	var `date`: java.util.Date? by attributes
	var `dateTime`: java.util.Date? by attributes
	var `decimal`: java.math.BigDecimal? by attributes
	var `double`: kotlin.Double? by attributes
	var `duration`: javax.xml.datatype.Duration? by attributes
	var `float`: kotlin.Float? by attributes
	var `gDay`: java.util.Date? by attributes
	var `gMonth`: java.util.Date? by attributes
	var `gMonthDay`: java.util.Date? by attributes
	var `gYear`: java.util.Date? by attributes
	var `gYearMonth`: java.util.Date? by attributes
	var `hexBinary`: kotlin.ByteArray? by attributes
	var `int`: kotlin.Int? by attributes
	var `integer`: kotlin.Int? by attributes
	var `long`: kotlin.Long? by attributes
	var `negativeInteger`: kotlin.Int? by attributes
	var `nonNegativeInteger`: kotlin.Int? by attributes
	var `nonPositiveInteger`: kotlin.Int? by attributes
	var `positiveInteger`: kotlin.Int? by attributes
	var `string`: kotlin.String? by attributes
	var `short`: kotlin.Short? by attributes
	var `time`: java.util.Date? by attributes
	var `token`: kotlin.String? by attributes
	var `unsignedByte`: kotlin.Short? by attributes
	var `unsignedInt`: kotlin.Long? by attributes
	var `unsignedLong`: kotlin.Int? by attributes
	var `unsignedShort`: kotlin.Int? by attributes
}

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