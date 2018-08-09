package org.redundent.kotlin.xml.gen.writer

import com.sun.tools.xjc.model.CClassInfo
import com.sun.tools.xjc.model.CNonElement

class XmlElement(
		val name: String,
		val type: CNonElement,
		private val tagName: String,
		private val documentation: String?,
		private val parent: CClassInfo? = null) : Code {

	override fun write(codeWriter: CodeWriter) {
		val rootElement = parent == null
		with (codeWriter) {
			writeKotlinDoc(documentation)

			val funLine = "fun ${if (!rootElement) "`${parent!!.shortName}`." else ""}`$name`"

			if (type is CClassInfo && type.hasOptionalAttributes) {
				writeln("@${JvmOverloads::class.simpleName}")
			}

			write(funLine)

			if (type is CClassInfo) {
				val blockParamType = "${((type.parent() as? CClassInfo)?.let { "`${it.shortName}`." } ?: "")}`${type.shortName}`"

				writeln("(${type.attributesAsParameters(funLine.length + (currentIndex * 4) + 1)}__block__: $blockParamType.() -> Unit)${if (rootElement) ": `${type.shortName}`" else ""} {", false)
				indent()
				writeln("val `$tagName` = `${type.shortName}`(${if (type.parent() is CClassInfo) "" else "\"$tagName\""})")
				if (type.allAttributes.isNotEmpty()) {
					writeln("`$tagName`.apply {")
					indent()
					for (attr in type.allAttributes) {
						val attrName = attr.xmlName.localPart
						if (!attr.isRequired) {
							writeln("if (`$attrName` != null) {")
							writeln("\tthis.`$attrName` = `$attrName`")
							writeln("}")
						} else {
							writeln("this.`$attrName` = `$attrName`")
						}
					}
					dedent()
					writeln("}")
				}

				writeln("`$tagName`.apply(__block__)")
				if (rootElement) {
					writeln("return `$tagName`")
				} else {
					writeln("this.addNode(`$tagName`)")
				}

				dedent()
				writeln("}\n")
			} else {
				val t = CodeWriter.mapType(type.type.fullName())
				writeln("(value: $t) {", false)
				indent()
				writeln("\"$tagName\"(value${if (t != String::class.qualifiedName) ".toString()" else ""})")
				dedent()
				writeln("}\n")
			}
		}
	}
}