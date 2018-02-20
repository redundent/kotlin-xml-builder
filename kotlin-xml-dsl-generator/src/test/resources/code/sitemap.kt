@file:Suppress("PropertyName", "ReplaceArrayOfWithLiteral", "LocalVariableName")

package org.redundent.generated

import org.redundent.kotlin.xml.*

/**
 * OPTIONAL: Indicates how frequently the content at a particular URL is
 * likely to change. The value "always" should be used to describe
 * documents that change each time they are accessed. The value "never"
 * should be used to describe archived URLs. Please note that web
 * crawlers may not necessarily crawl pages marked "always" more often.
 * Consider this element as a friendly suggestion and not a command.
 */
enum class `TChangeFreq` {
	`always`,
	`hourly`,
	`daily`,
	`weekly`,
	`monthly`,
	`yearly`,
	`never`
}

/**
 * Container for a set of up to 50,000 document elements.
 * This is the root element of the XML file.
 */
open class `Urlset`(nodeName: String) : Node(nodeName) {
	init {
		xmlns = "http://www.sitemaps.org/schemas/sitemap/0.9"
	}

}

fun `Urlset`.`url`(__block__: `TUrl`.() -> Unit) {
	val `url` = `TUrl`("url")
	`url`.apply(__block__)
	this.addNode(`url`)
}

/**
 * Container for the data needed to describe a document to crawl.
 */
@XmlType(childOrder = arrayOf("loc",
		"lastmod",
		"changefreq",
		"priority"))
open class `TUrl`(nodeName: String) : Node(nodeName) {
}

fun `TUrl`.`loc`(value: kotlin.String) {
	"loc"(value)
}

fun `TUrl`.`lastmod`(value: kotlin.String) {
	"lastmod"(value)
}

fun `TUrl`.`changefreq`(value: org.redundent.generated.TChangeFreq) {
	"changefreq"(value.toString())
}

fun `TUrl`.`priority`(value: java.math.BigDecimal) {
	"priority"(value.toString())
}

/**
 * Container for a set of up to 50,000 document elements.
 * This is the root element of the XML file.
 */
fun `urlset`(__block__: `Urlset`.() -> Unit): `Urlset` {
	val `urlset` = `Urlset`("urlset")
	`urlset`.apply(__block__)
	return `urlset`
}