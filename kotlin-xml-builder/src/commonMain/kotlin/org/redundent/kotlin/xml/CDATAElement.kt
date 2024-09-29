package org.redundent.kotlin.xml

/**
 * Similar to a [TextElement] except that the inner text is wrapped inside a `<![CDATA[]]>` tag.
 */
class CDATAElement internal constructor(text: String) : TextElement(text) {
//	override val typeId: String = "CDATAElement"

	override fun renderedText(printOptions: PrintOptions): String {
		fun String.escapeCData(): String {
			val cdataEnd = "]]>"
			val cdataStart = "<![CDATA["
			return this
				// split cdataEnd into two pieces so XML parser doesn't recognize it
				.replace(cdataEnd, "]]$cdataEnd$cdataStart>")
		}

		return "<![CDATA[${text.escapeCData()}]]>"
	}

	override fun equals(other: Any?): Boolean = super.equals(other) && other is CDATAElement

	// Need to use Class here to avoid a normal TextElement and a CDATAElement having the same hashCode if they have
	// the same text
	override fun hashCode(): Int {
		var hash = 7
		hash = 31 * hash + super.hashCode()
		hash = 31 * hash + (this::class.simpleName?.hashCode() ?: 0)
//		hash = 31 * hash + (this::class.qualifiedName?.hashCode() ?: 0)
		return hash
	}
}
