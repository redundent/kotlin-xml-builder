package org.redundent.kotlin.xml

/**
 * An element type that has some text in it.
 *
 * For example:
 * ```xml
 * <loc>http://blog.redundent.org</loc>
 * ```
 */
open class TextElement internal constructor(
	val text: String,
	private val unsafe: Boolean = false,
) : Element {
	private fun isEmpty() = text.trim('\n', '\r').isBlank()

	override fun render(builder: Appendable, indent: String, printOptions: PrintOptions) {
		if (isEmpty()) {
			return
		}

		val lineEnding = getLineEnding(printOptions)

		builder.append("$indent${renderedText(printOptions)}$lineEnding")
	}

	internal fun renderSingleLine(builder: Appendable, printOptions: PrintOptions) {
		builder.append(renderedText(printOptions))
	}

	internal open fun renderedText(printOptions: PrintOptions): String? {
		return if (unsafe) {
			text
		} else {
			escapeValue(text, printOptions.xmlVersion, printOptions.useCharacterReference)
		}
	}

	override fun toString(): String {
		return buildString {
			append("TextElement(")
			append("text=$text")
			append(", ")
			append("unsafe=$unsafe")
			append(")")
		}
	}

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is TextElement) return false
		if (text != other.text) return false
		return true
	}

	override fun hashCode(): Int {
		return text.hashCode()
	}
}
