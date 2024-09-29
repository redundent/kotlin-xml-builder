package org.redundent.kotlin.xml.gen

import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.test.assertEquals

abstract class AbstractGenTest {
	protected fun run(testName: String, withBindingFile: Boolean = false, vararg additionalArgs: String) {

		val schema = javaClass.getResourceAsStream("/schema/$testName.xsd")
			?: throw FileNotFoundException("/schema/$testName.xsd as not found")

		val code = getExpectedClassText(testName)

		val file = File.createTempFile("schema", ".xsd").apply {
			outputStream().use {
				schema.copyTo(it)
			}
		}

		val opts = ExOptions().apply {
			parseArguments(
				arrayOf("-p", "org.redundent.generated", file.absolutePath) +
					additionalArgs,
			)
		}

		if (withBindingFile) {
			val binding = javaClass.getResourceAsStream("/schema/$testName.jxb")
				?: throw FileNotFoundException("/schema/$testName.jxb as not found")

			val bindingText = binding.bufferedReader().readText()
				.replace("@schema@", "file:${file}")

			val bindingFile = File.createTempFile("binding", ".jxb").apply {
				writeText(bindingText)
			}

			opts.addBindFile(bindingFile)
		}

		val text = cleanText(DslGenerator(opts).generate().replace(System.lineSeparator(), "\n"))

		assertEquals(code, text, "generated code is not the same")
	}

	private fun getExpectedClassText(testName: String): String {
		val inputStream = getInputStream(testName)
		inputStream.use {
			return InputStreamReader(it).readText().replace(System.lineSeparator(), "\n")
		}
	}

	private fun getInputStream(testName: String): InputStream {
		val resName = "/code/$testName.kt"
		return javaClass.getResourceAsStream(resName)
			?: throw FileNotFoundException("$resName as not found")
	}

	private fun cleanText(text: String): String {
		return text.split("\n").joinToString("\n", transform = String::trimEnd)
	}
}
