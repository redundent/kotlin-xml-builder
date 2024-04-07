package org.redundent.kotlin.xml

/**
 * Similar to a [TextElement] except that the inner text is wrapped inside a `<![CDATA[]]>` tag.
 */
class CDATAElement internal constructor(text: String) : TextElement(text) {
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

	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other == null) return false
		if (this::class != other::class) return false
		if (other !is CDATAElement) return false
		return text == other.text
	}

	// Need to use Class here to avoid a normal TextElement and a CDATAElement having the same hashCode if they have
	// the same text
	override fun hashCode(): Int {
		var hash = 7
		hash = 31 * hash + super.hashCode()
		hash = 31 * hash + "org.redundent.kotlin.xml.CDATAElement".hashCode()
		return hash
	}
}
