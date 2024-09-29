package org.redundent.kotlin.xml

/**
 * Similar to a [TextElement] except that the inner text is wrapped inside <??> tag.
 */
class ProcessingInstructionElement internal constructor(
	text: String,
	private val attributes: Map<String, String>,
) : TextElement(text) {

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

	override fun toString(): String {
		return buildString {
			append("ProcessingInstructionElement(")
			append("text=$text")
			append(", ")
			append("attributes=${attributes}")
			append(")")
		}
	}

	override fun equals(other: Any?): Boolean {
		if (!super.equals(other)) return false
		if (other !is ProcessingInstructionElement) return false
		if (attributes != other.attributes) return false
		return true
	}

	override fun hashCode(): Int {
		var result = super.hashCode()
		result = 31 * result + attributes.hashCode()
		return result
	}
}
