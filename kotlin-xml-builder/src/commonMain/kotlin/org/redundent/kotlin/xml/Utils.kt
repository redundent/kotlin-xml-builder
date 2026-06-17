package org.redundent.kotlin.xml

internal fun escapeValue(value: Any?, xmlVersion: XmlVersion, useCharacterReference: Boolean = false): String? {
	val asString = value?.toString() ?: return null

	if (useCharacterReference) {
		return referenceCharacter(asString)
	}

	return when (xmlVersion) {
		XmlVersion.V10 -> escapeXml10(asString)
		XmlVersion.V11 -> escapeXml11(asString)
	}
}

private fun escapeXml10(text: String): String {
	val filtered = buildString {
		var i = 0
		while (i < text.length) {
			val c = text[i]
			val codePoint: Int
			val charsConsumed: Int
			if (c.isHighSurrogate() && i + 1 < text.length) {
				val low = text[i + 1]
				if (low.isLowSurrogate()) {
					codePoint = toCodePoint(c, low)
					charsConsumed = 2
				} else {
					codePoint = c.code
					charsConsumed = 1
				}
			} else {
				codePoint = c.code
				charsConsumed = 1
			}

			if (isXml10Valid(codePoint)) {
				if (charsConsumed == 1) {
					append(c)
				} else {
					append(c)
					append(text[i + 1])
				}
			}
			i += charsConsumed
		}
	}

	return filtered.replace("&", "&amp;")
		.replace("<", "&lt;")
		.replace(">", "&gt;")
		.replace("\"", "&quot;")
		.replace("'", "&apos;")
}

private fun escapeXml11(text: String): String {
	val sb = StringBuilder(text.length)
	var i = 0
	while (i < text.length) {
		val c = text[i]

		when (c) {
			'<' -> {
				sb.append("&lt;")
				i++
				continue
			}
			'>' -> {
				sb.append("&gt;")
				i++
				continue
			}
			'&' -> {
				sb.append("&amp;")
				i++
				continue
			}
			'"' -> {
				sb.append("&quot;")
				i++
				continue
			}
			'\'' -> {
				sb.append("&apos;")
				i++
				continue
			}
		}

		val codePoint: Int
		val charsConsumed: Int
		if (c.isHighSurrogate() && i + 1 < text.length) {
			val low = text[i + 1]
			if (low.isLowSurrogate()) {
				codePoint = toCodePoint(c, low)
				charsConsumed = 2
			} else {
				codePoint = c.code
				charsConsumed = 1
			}
		} else {
			codePoint = c.code
			charsConsumed = 1
		}

		if (isXml11Valid(codePoint)) {
			if (isXml11Restricted(codePoint)) {
				sb.append("&#").append(codePoint).append(";")
			} else {
				if (charsConsumed == 1) {
					sb.append(c)
				} else {
					sb.append(c)
					sb.append(text[i + 1])
				}
			}
		}
		i += charsConsumed
	}
	return sb.toString()
}

private fun toCodePoint(high: Char, low: Char): Int {
	// Formula from java.lang.Character.toCodePoint
	return (((high.code - 0xD800) * 0x400) + (low.code - 0xDC00) + 0x10000)
}

private fun isXml10Valid(code: Int): Boolean {
	return code == 0x9 || code == 0xA || code == 0xD ||
		(code in 0x20..0xD7FF) ||
		(code in 0xE000..0xFFFD) ||
		(code in 0x10000..0x10FFFF)
}

private fun isXml11Valid(code: Int): Boolean {
	return (code in 0x1..0xD7FF) ||
		(code in 0xE000..0xFFFD) ||
		(code in 0x10000..0x10FFFF)
}

private fun isXml11Restricted(code: Int): Boolean {
	return (code in 0x1..0x8) ||
		(code == 0xB || code == 0xC) ||
		(code in 0xE..0x1F) ||
		(code in 0x7F..0x84) ||
		(code in 0x86..0x9F)
}

internal fun referenceCharacter(asString: String): String {
	val builder = StringBuilder()

	asString.toCharArray().forEach { character ->
		when (character) {
			'\'' -> builder.append("&#39;")
			'&' -> builder.append("&#38;")
			'<' -> builder.append("&#60;")
			'>' -> builder.append("&#62;")
			'"' -> builder.append("&#34;")
			else -> builder.append(character)
		}
	}

	return builder.toString()
}

internal fun buildName(name: String, namespace: Namespace?): String =
	if (namespace == null || namespace.isDefault) name else "${namespace.name}:$name"

fun unsafe(value: Any?): Unsafe = Unsafe(value)
