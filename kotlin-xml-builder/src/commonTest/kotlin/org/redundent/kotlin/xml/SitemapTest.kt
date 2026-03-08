package org.redundent.kotlin.xml

import kotlin.test.Test

class SitemapTest : TestBase() {
	@Test
	fun basic_test() {
		val urlset = urlset {
			for (i in 1..3) {
				url("http://blog.redundent.org/post/$i")
			}
		}

		validate(urlset, PrintOptions(), "SitemapTest/basicTest.xml")
	}

	@Test
	fun all_elements() {
		val urlset = urlset {
			url(
				"http://blog.redundent.org",
				"2017-10-24",
				ChangeFreq.hourly,
				0.5
			)
		}
		validate(urlset, PrintOptions(), "SitemapTest/allElements.xml")
	}

	@Test
	fun sitemap_index() {
		val sitemapIndex = sitemapindex {
			sitemap("http://blog.redundent.org/sitemap1.xml", "2017-10-24")
			sitemap("http://blog.redundent.org/sitemap2.xml", "2016-01-01")
			sitemap("http://blog.redundent.org/sitemap3.xml")
		}

		validate(sitemapIndex, PrintOptions(), "SitemapTest/sitemapIndex.xml")
	}
}
