package org.redundent.kotlin.xml.gen.writer

import com.sun.tools.xjc.outline.EnumOutline

class XmlEnum(private val enum: EnumOutline) : Code {
	override fun write(codeWriter: CodeWriter) {
		with(codeWriter) {
			writeKotlinDoc(enum.target.documentation)
			writeln("enum class `${enum.target.shortName}` {")
			indent()

			writeln(enum.constants.joinToString(",\n\t") { "`${it.target.lexicalValue}`" })
			dedent()
			writeln("}\n")
		}
	}
}
