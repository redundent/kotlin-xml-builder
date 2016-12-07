package org.redundent.kotlin.xml

import java.io.File
import java.io.InputStreamReader

class TestUtils {
	companion object {
		fun getExpectedXml(testName: String): String {
			val inputStream = TestUtils::class.java.getResourceAsStream("/test-results/$testName.xml")
			inputStream.use {
				return InputStreamReader(it).readText()
			}
		}
	}
}