package org.redundent.kotlin.xml

import org.apache.commons.lang3.StringEscapeUtils

internal fun escapeValue(value: Any?, xmlVersion: XmlVersion): String? {
	val asString = value?.toString() ?: return null

	return when(xmlVersion) {
		XmlVersion.V10 -> StringEscapeUtils.escapeXml10(asString)
		XmlVersion.V11 -> StringEscapeUtils.escapeXml11(asString)
	}
}