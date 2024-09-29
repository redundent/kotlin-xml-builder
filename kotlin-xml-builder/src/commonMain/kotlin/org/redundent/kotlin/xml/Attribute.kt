package org.redundent.kotlin.xml

import kotlin.jvm.JvmOverloads

data class Attribute @JvmOverloads constructor(
	val name: String,
	val value: Any,
	val namespace: Namespace? = null
)
