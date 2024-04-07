package org.redundent.kotlin.xml

import kotlin.test.Test
import kotlinx.datetime.LocalDate

class sitemapTest : TestBase() {
	@Test
	fun basicTest() {
		val urlset = urlset {
			for (i in 1..3) {
				url("http://blog.redundent.org/post/$i")
			}
		}

		validate("basicTest", urlset)
	}

	@Test
	fun allElements() {
		val urlset = urlset {
			url(
				"http://blog.redundent.org",
				LocalDate.parse("2017-10-24"),
				ChangeFreq.hourly,
				14.0
			)
		}
		validate("allElements", urlset)
	}

	@Test
	fun sitemapIndex() {
		val sitemapIndex = sitemapindex {
			sitemap("http://blog.redundent.org/sitemap1.xml", LocalDate.parse("2017-10-24"))
			sitemap("http://blog.redundent.org/sitemap2.xml", LocalDate.parse("2016-01-01"))
			sitemap("http://blog.redundent.org/sitemap3.xml")
		}

		validate("sitemapIndex", sitemapIndex)
	}
}
