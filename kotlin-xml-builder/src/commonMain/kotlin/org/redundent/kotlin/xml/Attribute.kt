package org.redundent.kotlin.xml

data class Attribute @kotlin.jvm.JvmOverloads constructor(
	val name: String,
	val value: Any,
	val namespace: Namespace? = null
)
