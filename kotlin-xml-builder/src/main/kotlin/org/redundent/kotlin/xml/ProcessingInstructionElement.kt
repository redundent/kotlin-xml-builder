package org.redundent.kotlin.xml

/**
 * Similar to a [TextElement] except that the inner text is wrapped inside <??> tag.
 */
class ProcessingInstructionElement internal constructor(text: String) : TextElement(text) {
	override fun renderedText(printOptions: PrintOptions): String =
		"<?$text?>"
}