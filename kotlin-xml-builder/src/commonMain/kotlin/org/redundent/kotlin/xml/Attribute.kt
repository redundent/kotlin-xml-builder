package org.redundent.kotlin.xml

data class Attribute constructor(
	val name: String,
	val value: Any,
	val namespace: Namespace? = null
)
