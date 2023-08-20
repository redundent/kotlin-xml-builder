package org.redundent.kotlin.xml.gen.writer

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asClassName
import com.sun.tools.xjc.model.CTypeInfo
import java.math.BigDecimal

import java.util.Date

fun mapType(type: String): ClassName {
    return when (type) {
        "int",
        java.lang.Integer::class.java.name,
        java.math.BigInteger::class.java.name -> Int::class.asClassName()
        "long",
        java.lang.Long::class.java.name -> Long::class.asClassName()
        java.math.BigDecimal::class.java.name -> BigDecimal::class.asClassName()
        "boolean",
        java.lang.Boolean::class.java.name -> Boolean::class.asClassName()
        "double",
        java.lang.Double::class.java.name -> Double::class.asClassName()
        "float",
        java.lang.Float::class.java.name -> Float::class.asClassName()
        java.lang.String::class.java.name,
        javax.xml.namespace.QName::class.java.name -> String::class.asClassName()
        "byte",
        java.lang.Byte::class.java.name -> Byte::class.asClassName()
        "short",
        java.lang.Short::class.java.name -> Short::class.asClassName()
        "byte[]" -> ByteArray::class.asClassName()
        javax.xml.datatype.XMLGregorianCalendar::class.java.name -> Date::class.asClassName()
        else -> {
            if (type.contains(".")) {
                ClassName(
                    type.substringBeforeLast('.'),
                    type.substringAfterLast('.')
                )
            } else {
                ClassName("org.redundent.kotlin.xml", type)
            }
        }
    }
}
