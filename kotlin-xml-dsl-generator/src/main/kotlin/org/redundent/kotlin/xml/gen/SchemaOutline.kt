package org.redundent.kotlin.xml.gen

import com.sun.tools.xjc.model.CClassInfo
import com.sun.tools.xjc.model.CClassInfoParent
import com.sun.tools.xjc.outline.Outline
import org.redundent.kotlin.xml.gen.writer.XmlClass
import org.redundent.kotlin.xml.gen.writer.XmlEnum

class SchemaOutline(outline: Outline, private val opts: ExOptions) {
	private val innerClasses = outline.classes.filter { it.target.parent() is CClassInfo }.groupBy { it.target.parent() as CClassInfo }
	private val rootClasses = outline.classes.filter { it.target.parent() is CClassInfoParent.Package }

	val classes = rootClasses.sortedBy { if (it.target.isAbstract) 0 else 1 }.map { XmlClass(it, opts) }
	val enums = outline.enums.map(::XmlEnum)

	init {
		classes.forEach(this::recurseInnerClasses)
	}

	private fun recurseInnerClasses(clazz: XmlClass) {
		val innerClasses = innerClasses[clazz.clazz.target]
		innerClasses?.forEach {
			val innerClass = XmlClass(it, opts, innerClass = true)
			clazz.addInnerClass(innerClass)

			recurseInnerClasses(innerClass)
		}
	}
}