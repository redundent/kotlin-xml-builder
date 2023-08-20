@file:Suppress("PropertyName", "LocalVariableName", "FunctionName", "RedundantVisibilityModifier",
    "EnumEntryName")

package org.redundent.generated

import java.math.BigDecimal
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import org.redundent.kotlin.xml.Node
import org.redundent.kotlin.xml.XmlType

/**
 * OPTIONAL: Indicates how frequently the content at a particular URL is
 * likely to change. The value "always" should be used to describe
 * documents that change each time they are accessed. The value "never"
 * should be used to describe archived URLs. Please note that web
 * crawlers may not necessarily crawl pages marked "always" more often.
 * Consider this element as a friendly suggestion and not a command.
 */
public enum class TChangeFreq {
  always,
  hourly,
  daily,
  weekly,
  monthly,
  yearly,
  never,
}

/**
 * Container for a set of up to 50,000 document elements.
 * This is the root element of the XML file.
 */
public open class Urlset(
  nodeName: String,
) : Node(nodeName) {
  init {
    xmlns = "http://www.sitemaps.org/schemas/sitemap/0.9"
  }
}

public fun Urlset.url(__block__: TUrl.() -> Unit) {
  val url = TUrl("url")
  url.apply(__block__)
  this.addNode(url)
}

/**
 * Container for a set of up to 50,000 document elements.
 * This is the root element of the XML file.
 */
public fun urlset(__block__: Urlset.() -> Unit): Urlset {
  val urlset = Urlset("urlset")
  urlset.apply(__block__)
  return urlset
}

/**
 * Container for the data needed to describe a document to crawl.
 */
@XmlType(childOrder = arrayOf("loc",
		"lastmod",
		"changefreq",
		"priority"))
public open class TUrl(
  nodeName: String,
) : Node(nodeName)

public fun TUrl.loc(`value`: String) {
  "loc"(`value`)
}

public fun TUrl.lastmod(`value`: String) {
  "lastmod"(`value`)
}

public fun TUrl.changefreq(`value`: TChangeFreq) {
  "changefreq"(`value`.toString())
}

public fun TUrl.priority(`value`: BigDecimal) {
  "priority"(`value`.toString())
}
