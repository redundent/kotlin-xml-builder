package org.redundent.kotlin.xml

import java.text.SimpleDateFormat
import java.util.*

const val DEFAULT_URLSET_NAMESPACE = "http://www.sitemaps.org/schemas/sitemap/0.9"

class UrlSet internal constructor(): Node("urlset") {
	init {
		xmlns = DEFAULT_URLSET_NAMESPACE
	}

	fun url(
			loc: String,
			lastmod: Date? = null,
			changefreq: ChangeFreq? = null,
			priority: Double? = null) {
		"url" {
			"loc"(loc)

			lastmod?.let {
				"lastmod"(formatDate(it))
			}

			changefreq?.let {
				"changefreq"(it.name)
			}

			priority?.let {
				"priority"(it.toString())
			}
		}
	}
}

class Sitemapindex internal constructor(): Node("sitemapindex") {
	init {
		xmlns = DEFAULT_URLSET_NAMESPACE
	}

	fun sitemap(
			loc: String,
			lastmod: Date? = null) {
		"sitemap" {
			"loc"(loc)

			lastmod?.let {
				"lastmod"(formatDate(it))
			}
		}
	}
}

enum class ChangeFreq {
	always,
	hourly,
	daily,
	weekly,
	monthly,
	yearly,
	never
}

private fun formatDate(date: Date): String {
	return SimpleDateFormat("yyyy-MM-dd").format(date)
}

fun urlset(init: UrlSet.() -> Unit) = UrlSet().apply(init)

fun sitemapindex(init: Sitemapindex.() -> Unit) = Sitemapindex().apply(init)