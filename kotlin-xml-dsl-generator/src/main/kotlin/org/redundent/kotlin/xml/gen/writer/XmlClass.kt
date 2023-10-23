package org.redundent.kotlin.xml.gen.writer

import com.sun.tools.xjc.outline.ClassOutline
import org.redundent.kotlin.xml.gen.ExOptions

class XmlClass(
	val clazz: ClassOutline,
	private val opts: ExOptions,
	innerClass: Boolean = false
) : Code {
	val name: String = clazz.target.shortName
	val xmlns: String? =
		if (clazz.target.isElement && clazz.target.elementName.namespaceURI.isNotBlank()) clazz.target.elementName.namespaceURI else null

	private val superClassName: String =
		clazz.superClass?.target?.let { "${it.ownerPackage.name()}.`${it.shortName}`" } ?: "Node"
	private val abstract: Boolean = clazz.target.isAbstract
	private val attributes: MutableList<XmlAttribute> = ArrayList()
	private val memberElements: MutableList<XmlElement> = ArrayList()
	private var rootElement: XmlElement? = null
	private val innerClasses: MutableList<XmlClass> = ArrayList()

	private val modifier = when {
		abstract -> "abstract"
		innerClass -> "inner"
		else -> "open"
	}

	private val constructorArg = if (!innerClass) "(nodeName: String)" else ""
	private val superClassConstructorArg = if (innerClass) "\"$name\"" else "nodeName"

	private val hasBody: Boolean
		get() = attributes.isNotEmpty() || xmlns != null || innerClasses.isNotEmpty() || (opts.useMemberFunctions && memberElements.isNotEmpty())

	init {
		clazz.target.attributes.map {
			val field = clazz.parent().getField(it)
			val name = it.xmlName.localPart
			val type = "${CodeWriter.mapType(field.rawType.fullName())}${if (!it.isRequired) "?" else ""}"

			XmlAttribute(name, type, it.isRequired)
		}.forEach { attributes.add(it) }

		clazz.target.elements.forEach { element ->
			element.types.forEach { type ->
				memberElements.add(
					XmlElement(
						type.tagName.localPart,
						type.target,
						type.tagName.localPart,
						element.documentation,
						if (opts.useMemberFunctions) null else clazz.target
					)
				)
			}
		}

		if (clazz.target.isElement) {
			val name = clazz.target.elementName.localPart
			rootElement = XmlElement(name, clazz.target, name, clazz.target.documentation)
		}
	}

	override fun write(codeWriter: CodeWriter) {
		with(codeWriter) {
			writeKotlinDoc(clazz.target.documentation)

			if (clazz.target.isOrdered && clazz.target.elements.size > 1) {
				writeln("@XmlType(childOrder = arrayOf(${clazz.target.elements.joinToString(",\n\t\t") { "\"${it.types.first().tagName.localPart}\"" }}))")
			}
			writeln("$modifier class `$name`$constructorArg : $superClassName($superClassConstructorArg)${if (hasBody) " {" else "\n"}")
			indent()

			if (xmlns != null) {
				writeBlock {
					"""
init {
	xmlns = "$xmlns"
}
"""
				}
			}

			if (attributes.isNotEmpty()) {
				attributes.forEach { it.write(this) }
				writeln()
			}

			if (opts.useMemberFunctions) {
				if (memberElements.isNotEmpty()) {
					memberElements.forEach { it.write(this) }
				}
			}

			if (innerClasses.isNotEmpty()) {
				innerClasses.forEach { it.write(this) }
			}

			if (hasBody) {
				trimLastNewLine()
			}

			dedent()

			if (hasBody) {
				writeln("}\n")
			}

			if (!opts.useMemberFunctions) {
				memberElements.forEach { it.write(this) }
			}

			rootElement?.write(this)
		}
	}

	fun addInnerClass(xmlClass: XmlClass) {
		innerClasses.add(xmlClass)
	}
}
