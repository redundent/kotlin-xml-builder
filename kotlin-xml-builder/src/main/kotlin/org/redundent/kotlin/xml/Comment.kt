package org.redundent.kotlin.xml

/**
 * Similar to a [TextElement] except that the inner text is wrapped inside a comment tag <!-- -->
 * Note that "--" will be replaced with "&#45;&#45;"
 */
class Comment internal constructor(val text: String) : Element {
	override fun render(builder: Appendable, indent: String, printOptions: PrintOptions) {
		val lineEnding = getLineEnding(printOptions)

		builder.append("$indent<!-- ${text.replace("--", "&#45;&#45;")} -->$lineEnding")
	}
}