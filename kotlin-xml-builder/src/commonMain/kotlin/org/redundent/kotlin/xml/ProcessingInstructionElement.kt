package org.redundent.kotlin.xml

/**
 * Similar to a [TextElement] except that the inner text is wrapped inside <??> tag.
 */
class ProcessingInstructionElement internal constructor(text: String, private val attributes: Map<String, String>) :
	TextElement(text) {
	override fun renderedText(printOptions: PrintOptions): String {
		return "<?$text${renderAttributes()}?>"
	}

	private fun renderAttributes(): String {
		if (attributes.isEmpty()) {
			return ""
		}

		return " " + attributes.entries.joinToString(" ") {
			"${it.key}=\"${it.value}\""
		}
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null) return false
		if (this::class != other::class) return false
		if (other !is ProcessingInstructionElement) return false
		return text == other.text
	}

	override fun hashCode(): Int {
		var result = super.hashCode()
		result = 31 * result + attributes.hashCode()
		return result
	}
}
