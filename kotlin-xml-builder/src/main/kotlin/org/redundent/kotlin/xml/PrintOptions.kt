package org.redundent.kotlin.xml

class PrintOptions(
		/**
		 * Whether to print newlines and tabs while rendering the document.
		 */
		val pretty: Boolean = true,

		/**
		 * Whether to print a single text element on the same line.
		 *
		 * ```
		 * <element>
		 *     text value
		 * </element>
		 * ```
		 *
		 * vs
		 *
		 * ```
		 * <element>text value</element>
		 * ```
		 */
		val singleLineTextElements: Boolean = false,

		/**
		 * Whether to use "self closing" tags for empty elements.
		 *
		 * ```
		 * <element></element>
		 * ```
		 *
		 * vs
		 *
		 * ```
		 * <element />
		 * ```
		 */
		val useSelfClosingTags: Boolean = true) {

	internal var xmlVersion: XmlVersion = XmlVersion.V10
}