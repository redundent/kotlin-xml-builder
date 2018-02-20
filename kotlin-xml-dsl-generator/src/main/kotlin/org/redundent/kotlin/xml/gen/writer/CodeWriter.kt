package org.redundent.kotlin.xml.gen.writer

import com.sun.tools.xjc.model.*
import com.sun.tools.xjc.outline.ClassOutline
import com.sun.tools.xjc.outline.Outline
import com.sun.tools.xjc.reader.xmlschema.bindinfo.BindInfo
import com.sun.xml.xsom.XSComponent
import java.util.*

class CodeWriter(private val outline: Outline) {
	private val output = StringBuilder()

	private var currentIndex = 0

	fun writePackage(packageName: String) {
		writeln("package $packageName\n")
	}

	fun writeImport(importStatement: String) {
		writeln("import $importStatement")
	}

	private fun writeClass(clazz: ClassOutline, block: () -> Unit) {
		val target = clazz.target
		val modifier = if (target.isAbstract) "abstract" else "open"
		val superClass = clazz.superClass?.target?.shortName ?: "Node"
		writeln("$modifier class `${target.shortName}`(nodeName: String) : $superClass(nodeName) {")
		indent()

		block()

		dedent()
		writeln("}\n")
	}

	private fun writeKotlinDoc(doc: String?) {
		if (doc == null || doc.isBlank()) {
			return
		}

		writeln("/**")
		doc.split("\n").map(String::trim).filter(String::isNotBlank).forEach { writeln(" * $it") }
		writeln(" */")
	}

	private fun indent() = currentIndex++

	private fun dedent() = currentIndex--

	private fun write(text: String, writeIndex: Boolean = true) {
		if (writeIndex) {
			output.append((0 until currentIndex).joinToString("") { "\t" })
		}
		output.append(text)
	}

	fun writeln(text: String, writeIndex: Boolean = true) {
		write(text, writeIndex)
		output.append("\n")
	}

	fun asText(): String = output.toString().trim()

	fun writeSimpleTypes() {
		outline.enums.forEach { simpleType ->
			writeKotlinDoc(simpleType.target.documentation)
			writeln("enum class `${simpleType.target.shortName}` {")
			indent()

			writeln(simpleType.constants.joinToString(",\n\t") { "`${it.target.lexicalValue}`" })
			dedent()
			writeln("}\n")
		}
	}

	fun writeClasses() {
		outline.classes.sortedBy { if (it.target.isAbstract) 0 else 1 }.forEach { clazz ->
			writeKotlinDoc(clazz.target.documentation)

			if (clazz.target.isOrdered && clazz.target.elements.size > 1) {
				writeln("@XmlType(childOrder = arrayOf(${clazz.target.elements.joinToString(",\n\t\t") { "\"${it.getName(false)}\"" }}))")
			}

			writeClass(clazz) {
				if (clazz.target.isElement) {
					val ns = clazz.target.elementName.namespaceURI
					if (ns != null && ns.isNotEmpty()) {
						writeln("init {")
						writeln("\txmlns = \"$ns\"")
						writeln("}\n")
					}
				}

				appendAttributes(clazz.target)
			}

			processElements(clazz.target)
		}

		outline.classes.filter { it.target.isElement }.forEach { element ->
			val name = element.target.elementName.localPart
			processElement("`$name`", name, element.target, element.target.documentation, true)
		}
	}

	private fun processElements(clazz: CClassInfo) {
		clazz.elements.forEach { element ->
			element.types.forEach { type ->
				processElement("`${clazz.shortName}`.`${type.tagName.localPart}`", type.tagName.localPart,
						type.target, element.documentation)
			}
		}
	}

	private fun processElement(functionName: String, tagName: String, type: CNonElement,
							   documentation: String?, rootElement: Boolean = false) {
		writeKotlinDoc(documentation)

		val funLine = "fun $functionName"
		write(funLine)

		if (type is CClassInfo) {
			writeln("(${type.attributesAsParameters(funLine.length + 1)}__block__: `${type.shortName}`.() -> Unit)${if (rootElement) ": `${type.shortName}`" else ""} {")
			indent()
			writeln("val `$tagName` = `${type.shortName}`(\"$tagName\")")
			if (type.attributes.isNotEmpty()) {
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
			val t = mapType(type.type.fullName())
			writeln("(value: $t) {\n\t\"$tagName\"(value${if (t != String::class.qualifiedName) ".toString()" else ""})\n}\n")
		}
	}

	private fun appendAttributes(clazz: CClassInfo) {
		val attributeNodes = clazz.attributes
		for (an in attributeNodes) {
			val field = outline.getField(an)
			val kotlinType = "${mapType(field.rawType.fullName())}${if (!an.isRequired) "?" else ""}"
			val kotlinName = "`${an.xmlName.localPart}`"

			writeKotlinDoc(an.documentation)
			writeln("var $kotlinName: $kotlinType by attributes")
		}
	}

	private fun CClassInfo.attributesAsParameters(indentLength: Int): String {
		if (allAttributes.isEmpty()) {
			return ""
		}

		val sortedAttributes = allAttributes.sortedWith(Comparator { o1, o2 -> Integer.compare(if (o1.isRequired) 0 else 1, if (o2.isRequired) 0 else 1) })

		val numOfTabs = Math.floor(indentLength.toDouble() / 4.0).toInt()
		val numOfSpaces = indentLength % 4

		val delimiter = ",\n${(0..numOfTabs).joinToString("\t") { "" }}${(0..numOfSpaces).joinToString(" ") { "" }}"

		return sortedAttributes.joinToString(delimiter) {
			val field = outline.getField(it)
			"`${it.xmlName.localPart}`: ${mapType(field.rawType.fullName())}${if (!it.isRequired) "? = null" else ""}"
		} + delimiter
	}

	private fun mapType(type: String): String {
		return when (type) {
			"int",
			java.lang.Integer::class.java.name,
			java.math.BigInteger::class.java.name -> Int::class.qualifiedName
			"long",
			java.lang.Long::class.java.name -> Long::class.qualifiedName
			"boolean",
			java.lang.Boolean::class.java.name -> Boolean::class.qualifiedName
			"double",
			java.lang.Double::class.java.name -> Double::class.qualifiedName
			"float",
			java.lang.Float::class.java.name -> Float::class.qualifiedName
			java.lang.String::class.java.name,
			javax.xml.namespace.QName::class.java.name -> String::class.qualifiedName
			"byte",
			java.lang.Byte::class.java.name -> Byte::class.qualifiedName
			"short",
			java.lang.Short::class.java.name -> Short::class.qualifiedName
			"byte[]" -> ByteArray::class.qualifiedName
			javax.xml.datatype.XMLGregorianCalendar::class.java.name -> Date::class.qualifiedName
			else -> type
		}!!
	}
}

val CClassInfo.attributes: List<CAttributePropertyInfo>
	get() = properties.mapNotNull { it as? CAttributePropertyInfo }
val CClassInfo.allAttributes: List<CAttributePropertyInfo>
	get() = generateSequence(this) { it.baseClass }.toList().flatMap { it.properties.mapNotNull { it as? CAttributePropertyInfo } }
val CClassInfo.elements: List<CElementPropertyInfo>
	get() = properties.mapNotNull { it as? CElementPropertyInfo }
val CPropertyInfo.documentation: String?
	get() = schemaComponent?.documentation
val CTypeInfo.documentation: String?
	get() = schemaComponent?.documentation
val XSComponent.documentation: String?
	get() = annotation?.annotation?.let { it as? BindInfo }?.documentation
