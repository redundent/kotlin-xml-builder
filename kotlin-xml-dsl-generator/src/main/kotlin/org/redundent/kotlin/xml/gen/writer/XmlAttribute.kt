package org.redundent.kotlin.xml.gen.writer

class XmlAttribute(
		val name: String,
		val type: String,
		private val isRequired: Boolean
) : Code {
	override fun write(codeWriter: CodeWriter) {
		codeWriter.writeln("var `$name`: $type")
		codeWriter.indent()
		codeWriter.writeln("get() = get(\"$name\")${if (isRequired) "!!" else ""}")
		codeWriter.writeln("set(value) { set(\"$name\", value) }")
		codeWriter.dedent()
	}
}