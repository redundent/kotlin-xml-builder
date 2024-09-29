package org.redundent.kotlin.xml

//import java.text.SimpleDateFormat
//import java.util.*

const val DEFAULT_URLSET_NAMESPACE = "http://www.sitemaps.org/schemas/sitemap/0.9"

class UrlSet internal constructor() : Node("urlset") {
	init {
		xmlns = DEFAULT_URLSET_NAMESPACE
	}

	fun url(
		loc: String,
		lastmod: String? = null,
//		lastmod: Date? = null,
		changefreq: ChangeFreq? = null,
		priority: Double? = null
	) {
		"url" {
			"loc"(loc)

			lastmod?.let {
				"lastmod"(it)
//				"lastmod"(formatDate(it))
			}

			changefreq?.let {
				"changefreq"(it.name)
			}

			priority?.let {
				val value = buildString {
					append(it.toString())
					if (!endsWith(".0")) {
						append(".0")
					}
				}
				"priority"(value)
			}
		}
	}
}

class Sitemapindex internal constructor() : Node("sitemapindex") {
	init {
		xmlns = DEFAULT_URLSET_NAMESPACE
	}

	fun sitemap(
		loc: String,
		lastmod: String? = null
//		lastmod: Date? = null
	) {
		"sitemap" {
			"loc"(loc)

			lastmod?.let {
				"lastmod"(it)
//				"lastmod"(formatDate(it))
			}
		}
	}
}

@Suppress("EnumEntryName", "ktlint:enum-entry-name-case")
enum class ChangeFreq {
	always,
	hourly,
	daily,
	weekly,
	monthly,
	yearly,
	never
}

//private fun formatDate(date: Date): String {
//	return SimpleDateFormat("yyyy-MM-dd").format(date)
//}

fun urlset(init: UrlSet.() -> Unit) = UrlSet().apply(init)

fun sitemapindex(init: Sitemapindex.() -> Unit) = Sitemapindex().apply(init)
