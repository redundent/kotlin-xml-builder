package org.redundent.kotlin.xml.gen.writer

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.sun.tools.xjc.outline.Outline

class XmlAttribute(
	val name: String,
	val type: TypeName,
	val namespaceUri: String?,
	val prefix: String?
) : Code<PropertySpec> {
	override fun build(outline: Outline): PropertySpec =
		PropertySpec.builder(name, type)
			.getter(
				FunSpec.getterBuilder()
					.addStatement("return get(%S)%L", name, if (type.isNullable) "" else "!!")
					.build()
			)
			.setter(
				FunSpec.setterBuilder()
					.addParameter("value", type)
					.addStatement("set(%S, %N)", name, "value")
					.build()
			)
			.mutable(true)
			.build()
}