package org.redundent.kotlin.xml.gen.writer

import com.squareup.kotlinpoet.TypeSpec
import com.sun.tools.xjc.outline.EnumOutline
import com.sun.tools.xjc.outline.Outline

class XmlEnum(private val enum: EnumOutline) : Code<TypeSpec> {
	override fun build(outline: Outline): TypeSpec {
		val enumBuilder = TypeSpec.enumBuilder(enum.target.shortName)
		enum.target.documentation?.also { enumBuilder.addKdoc(it) }
		enum.constants.forEach { enumBuilder.addEnumConstant(it.target.lexicalValue) }
		return enumBuilder.build()
	}
}