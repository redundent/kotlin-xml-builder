package org.redundent.kotlin.xml

import kotlin.reflect.KClass

internal actual val isReflectionAvailable: Boolean by lazy {
	try {
		{ }::class.annotations.isEmpty()
		true
	} catch (ex: KotlinReflectionNotSupportedError) {
		false
	}
}

internal actual fun findXmlType(cls: KClass<*>): XmlType? {
	return cls.annotations.find { it is XmlType } as? XmlType
}

internal actual val systemLineSeparator: String
	get() = System.lineSeparator()
