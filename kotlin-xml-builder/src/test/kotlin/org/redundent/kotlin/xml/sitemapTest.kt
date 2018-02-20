package org.redundent.kotlin.xml

import org.junit.Test
import java.text.SimpleDateFormat

class sitemapTest : XmlBuilderTestBase() {
	@Test
	fun basicTest() {
		val urlset = urlset {
			for (i in 1..3) {
				url("http://blog.redundent.org/post/$i")
			}
		}

		validate(urlset)
	}

	@Test
	fun allElements() {
		val urlset = urlset {
			url("http://blog.redundent.org",
					SimpleDateFormat("yyyy-MM-dd").parse("2017-10-24"),
					ChangeFreq.hourly,
					14.0)
		}
		validate(urlset)
	}

	@Test
	fun sitemapIndex() {
		val format = SimpleDateFormat("yyyy-MM-dd")
		val sitemapIndex = sitemapindex {
			sitemap("http://blog.redundent.org/sitemap1.xml", format.parse("2017-10-24"))
			sitemap("http://blog.redundent.org/sitemap2.xml", format.parse("2016-01-01"))
			sitemap("http://blog.redundent.org/sitemap3.xml")
		}

		validate(sitemapIndex)
	}
}