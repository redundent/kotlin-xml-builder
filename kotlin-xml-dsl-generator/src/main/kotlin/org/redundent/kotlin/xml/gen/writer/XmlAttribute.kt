package org.redundent.kotlin.xml.gen.writer

class XmlAttribute(val name: String, val type: String) : Code {
	override fun write(codeWriter: CodeWriter) {
		codeWriter.writeln("var `$name`: $type by attributes")
	}
}