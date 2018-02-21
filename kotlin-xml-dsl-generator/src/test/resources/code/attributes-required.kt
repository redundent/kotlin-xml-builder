@file:Suppress("PropertyName", "ReplaceArrayOfWithLiteral", "LocalVariableName", "FunctionName", "RemoveEmptyClassBody")

package org.redundent.generated

import org.redundent.kotlin.xml.*

open class `AttributesRequired`(nodeName: String) : Node(nodeName) {
	var `uri`: kotlin.String by attributes
	var `base64Binary`: kotlin.ByteArray by attributes
	var `boolean`: kotlin.Boolean by attributes
	var `byte`: kotlin.Byte by attributes
	var `date`: java.util.Date by attributes
	var `dateTime`: java.util.Date by attributes
	var `decimal`: java.math.BigDecimal by attributes
	var `double`: kotlin.Double by attributes
	var `duration`: javax.xml.datatype.Duration by attributes
	var `float`: kotlin.Float by attributes
	var `gDay`: java.util.Date by attributes
	var `gMonth`: java.util.Date by attributes
	var `gMonthDay`: java.util.Date by attributes
	var `gYear`: java.util.Date by attributes
	var `gYearMonth`: java.util.Date by attributes
	var `hexBinary`: kotlin.ByteArray by attributes
	var `int`: kotlin.Int by attributes
	var `integer`: kotlin.Int by attributes
	var `long`: kotlin.Long by attributes
	var `negativeInteger`: kotlin.Int by attributes
	var `nonNegativeInteger`: kotlin.Int by attributes
	var `nonPositiveInteger`: kotlin.Int by attributes
	var `positiveInteger`: kotlin.Int by attributes
	var `string`: kotlin.String by attributes
	var `short`: kotlin.Short by attributes
	var `time`: java.util.Date by attributes
	var `token`: kotlin.String by attributes
	var `unsignedByte`: kotlin.Short by attributes
	var `unsignedInt`: kotlin.Long by attributes
	var `unsignedLong`: kotlin.Int by attributes
	var `unsignedShort`: kotlin.Int by attributes
}

fun `attributesRequired`(`uri`: kotlin.String,
						 `base64Binary`: kotlin.ByteArray,
						 `boolean`: kotlin.Boolean,
						 `byte`: kotlin.Byte,
						 `date`: java.util.Date,
						 `dateTime`: java.util.Date,
						 `decimal`: java.math.BigDecimal,
						 `double`: kotlin.Double,
						 `duration`: javax.xml.datatype.Duration,
						 `float`: kotlin.Float,
						 `gDay`: java.util.Date,
						 `gMonth`: java.util.Date,
						 `gMonthDay`: java.util.Date,
						 `gYear`: java.util.Date,
						 `gYearMonth`: java.util.Date,
						 `hexBinary`: kotlin.ByteArray,
						 `int`: kotlin.Int,
						 `integer`: kotlin.Int,
						 `long`: kotlin.Long,
						 `negativeInteger`: kotlin.Int,
						 `nonNegativeInteger`: kotlin.Int,
						 `nonPositiveInteger`: kotlin.Int,
						 `positiveInteger`: kotlin.Int,
						 `string`: kotlin.String,
						 `short`: kotlin.Short,
						 `time`: java.util.Date,
						 `token`: kotlin.String,
						 `unsignedByte`: kotlin.Short,
						 `unsignedInt`: kotlin.Long,
						 `unsignedLong`: kotlin.Int,
						 `unsignedShort`: kotlin.Int,
						 __block__: `AttributesRequired`.() -> Unit): `AttributesRequired` {
	val `attributesRequired` = `AttributesRequired`("attributesRequired")
	`attributesRequired`.apply {
		this.`uri` = `uri`
		this.`base64Binary` = `base64Binary`
		this.`boolean` = `boolean`
		this.`byte` = `byte`
		this.`date` = `date`
		this.`dateTime` = `dateTime`
		this.`decimal` = `decimal`
		this.`double` = `double`
		this.`duration` = `duration`
		this.`float` = `float`
		this.`gDay` = `gDay`
		this.`gMonth` = `gMonth`
		this.`gMonthDay` = `gMonthDay`
		this.`gYear` = `gYear`
		this.`gYearMonth` = `gYearMonth`
		this.`hexBinary` = `hexBinary`
		this.`int` = `int`
		this.`integer` = `integer`
		this.`long` = `long`
		this.`negativeInteger` = `negativeInteger`
		this.`nonNegativeInteger` = `nonNegativeInteger`
		this.`nonPositiveInteger` = `nonPositiveInteger`
		this.`positiveInteger` = `positiveInteger`
		this.`string` = `string`
		this.`short` = `short`
		this.`time` = `time`
		this.`token` = `token`
		this.`unsignedByte` = `unsignedByte`
		this.`unsignedInt` = `unsignedInt`
		this.`unsignedLong` = `unsignedLong`
		this.`unsignedShort` = `unsignedShort`
	}
	`attributesRequired`.apply(__block__)
	return `attributesRequired`
}