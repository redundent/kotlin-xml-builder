package org.redundent.kotlin.xml.gen.writer

import com.sun.tools.xjc.outline.Outline

interface Code<T> {
	fun build(outline: Outline): T
}