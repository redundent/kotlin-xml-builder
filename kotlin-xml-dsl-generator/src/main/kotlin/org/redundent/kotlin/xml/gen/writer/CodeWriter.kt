package org.redundent.kotlin.xml.gen.writer

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.asClassName
import com.sun.tools.xjc.model.CAttributePropertyInfo
import com.sun.tools.xjc.model.CClassInfo
import com.sun.tools.xjc.model.CElementPropertyInfo
import com.sun.tools.xjc.model.CPropertyInfo
import com.sun.tools.xjc.model.CTypeInfo
import com.sun.tools.xjc.outline.Outline
import com.sun.tools.xjc.reader.xmlschema.bindinfo.BindInfo
import com.sun.xml.xsom.XSComponent
import java.util.Comparator
import java.util.Date

class CodeWriter(
	private val outline: Outline,
	packageName: String,
	fileName: String
) {
	val kotlinFile = FileSpec.builder(packageName, fileName)
	private var output = StringBuilder()

	var currentIndex = 0
		private set

	fun writeKotlinDoc(doc: String?) {
		if (doc == null || doc.isBlank()) {
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

		kotlinFile.addAnnotation(
			AnnotationSpec.builder(Suppress::class.asClassName())
				.addMember("%L", list.joinToString(", ") { "\"$it\"" })
				.build()
		)
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

	fun asText(): String = kotlinFile.build().toString()

	fun trimLastNewLine() {
		if (output.endsWith('\n')) {
			output = StringBuilder(output.substring(0, output.lastIndex))
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
	get() = fixDoc(schemaComponent?.documentation)
val CTypeInfo.documentation: String?
	get() = fixDoc(schemaComponent?.documentation)
val XSComponent.documentation: String?
	get() = annotation?.annotation?.let { it as? BindInfo }?.documentation

private fun fixDoc(doc: String?): String? =
	doc
		?.split("\n")
		?.map(String::trim)
		?.filter(String::isNotBlank)
		?.joinToString("\n")