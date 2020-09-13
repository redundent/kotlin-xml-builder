package org.redundent.kotlin.xml

import org.apache.commons.lang3.StringEscapeUtils
import java.lang.StringBuilder

internal fun escapeValue(value: Any?, xmlVersion: XmlVersion, useCharacterReference: Boolean = false): String? {
	val asString = value?.toString() ?: return null

	if (useCharacterReference) {
		return referenceCharacter(asString)
	}

	return when(xmlVersion) {
		XmlVersion.V10 -> StringEscapeUtils.escapeXml10(asString)
		XmlVersion.V11 -> StringEscapeUtils.escapeXml11(asString)
	}
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