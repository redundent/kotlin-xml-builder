package org.redundent.kotlin.xml

import kotlin.reflect.KClass

internal actual fun KClass<*>.annotations(): List<Annotation> {
	if (!isReflectionAvailable()) return emptyList()

	return this.annotations
}

internal actual fun isReflectionAvailable(): Boolean =
	isReflectionAvailableImpl

private val isReflectionAvailableImpl: Boolean by lazy {
	try {
		{}::class.annotations.isEmpty()
		true
	} catch (ex: KotlinReflectionNotSupportedError) {
		false
	}
}
