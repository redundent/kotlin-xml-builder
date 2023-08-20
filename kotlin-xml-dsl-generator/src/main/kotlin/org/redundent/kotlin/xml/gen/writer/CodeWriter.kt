package org.redundent.kotlin.xml.gen.writer

import com.sun.tools.xjc.model.CAttributePropertyInfo
import com.sun.tools.xjc.model.CClassInfo
import com.sun.tools.xjc.model.CElementPropertyInfo
import com.sun.tools.xjc.model.CPropertyInfo
import com.sun.tools.xjc.model.CTypeInfo
import com.sun.tools.xjc.outline.Outline
import com.sun.tools.xjc.reader.xmlschema.bindinfo.BindInfo
import com.sun.xml.xsom.XSComponent
import java.util.Date
import kotlin.math.floor

class CodeWriter(private val outline: Outline) {
	private var output = StringBuilder()

	var currentIndex = 0
		private set

	fun writePackage(packageName: String) {
		writeln("package $packageName\n")
	}

	fun writeImport(importStatement: String) {
		writeln("import $importStatement")
	}

	fun writeKotlinDoc(doc: String?) {
		if (doc.isNullOrBlank()) {
			return
		}

		writeln("/**")
		doc.split("\n").map(String::trim).filter(String::isNotBlank).forEach { writeln(" * $it") }
		writeln(" */")
	}

	fun indent() = currentIndex++

	fun dedent() = currentIndex--

	fun write(text: String, writeIndex: Boolean = true) {
		if (writeIndex) {
			output.append((0 until currentIndex).joinToString("") { "\t" })
		}
		output.append(text)
	}

	fun writeSuppress(block: MutableList<String>.() -> Unit) {
		val list = ArrayList<String>()
		list.block()

		writeln("@file:Suppress(${list.joinToString(", ") { "\"$it\"" }})")
		writeln()
	}

	fun writeln(text: String = "", writeIndex: Boolean = true) {
		if (text.isNotEmpty()) {
			write(text, writeIndex)
		}
		output.append("\n")
	}

	fun writeBlock(block: () -> String) {
		val text = block().trimStart()
		text.split("\n").forEach { writeln(it) }
	}

	fun asText(): String = output.toString().trim()

	fun trimLastNewLine() {
		if (output.endsWith('\n')) {
			output = StringBuilder(output.substring(0, output.lastIndex))
		}
	}

	fun CClassInfo.attributesAsParameters(indentLength: Int): String {
		if (allAttributes.isEmpty()) {
			return ""
		}

		val sortedAttributes = allAttributes.sortedWith { o1, o2 -> (if (o1.isRequired) 0 else 1).compareTo(if (o2.isRequired) 0 else 1) }

		val numOfTabs = floor(indentLength.toDouble() / 4.0).toInt()
		val numOfSpaces = indentLength % 4

		val delimiter = ",\n${(0..numOfTabs).joinToString("\t") { "" }}${(0..numOfSpaces).joinToString(" ") { "" }}"

		return sortedAttributes.joinToString(delimiter) {
			val field = outline.getField(it)
			"`${it.xmlName.localPart}`: ${mapType(field.rawType.fullName())}${if (!it.isRequired) "? = null" else ""}"
		} + delimiter
	}

	companion object {
		fun mapType(type: String): String {
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
}

val CClassInfo.attributes: List<CAttributePropertyInfo>
	get() = properties.mapNotNull { it as? CAttributePropertyInfo }
val CClassInfo.allAttributes: List<CAttributePropertyInfo>
	get() = generateSequence(this) { it.baseClass }.toList().flatMap { it.properties.mapNotNull { p -> p as? CAttributePropertyInfo } }
val CClassInfo.hasOptionalAttributes: Boolean
	get() = allAttributes.any { !it.isRequired }
val CClassInfo.elements: List<CElementPropertyInfo>
	get() = properties.mapNotNull { it as? CElementPropertyInfo }
val CPropertyInfo.documentation: String?
	get() = schemaComponent?.documentation
val CTypeInfo.documentation: String?
	get() = schemaComponent?.documentation
val XSComponent.documentation: String?
	get() = annotation?.annotation?.let { it as? BindInfo }?.documentation
