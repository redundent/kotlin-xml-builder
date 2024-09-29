package org.redundent.kotlin.xml

import kotlin.reflect.KClass

internal actual fun KClass<*>.annotations(): List<Annotation> =
	emptyList()

internal actual fun isReflectionAvailable(): Boolean = false
