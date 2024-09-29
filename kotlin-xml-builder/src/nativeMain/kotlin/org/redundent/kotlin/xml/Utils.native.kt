package org.redundent.kotlin.xml

import kotlin.reflect.KClass

internal actual val isReflectionAvailable: Boolean
	get() = false

internal actual fun findXmlType(cls: KClass<*>): XmlType? = null

internal actual val systemLineSeparator: String = "\n"
