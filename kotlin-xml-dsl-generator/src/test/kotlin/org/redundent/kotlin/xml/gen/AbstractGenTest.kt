package org.redundent.kotlin.xml.gen

import org.junit.Rule
import org.junit.rules.TestName
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.test.assertEquals

abstract class AbstractGenTest {
	@Suppress("MemberVisibilityCanBePrivate")
	@get:Rule
	val testName = TestName()

	protected fun run(withBindingFile: Boolean = false, vararg additionalArgs: String) {
		val schema = javaClass.getResourceAsStream("/schema/${testName.methodName}.xsd")
		val code = getExpectedClassText()

		val file = File.createTempFile("schema", ".xsd").apply {
			outputStream().use {
				schema.copyTo(it)
			}
		}

		val opts = ExOptions().apply { parseArguments(arrayOf("-p", "org.redundent.generated", file.absolutePath) + additionalArgs) }

		if (withBindingFile) {
			val binding = javaClass.getResourceAsStream("/schema/${testName.methodName}.jxb")
			val bindingFile = File.createTempFile("binding", ".jxb").apply {
				writer().use { writer ->
					binding.reader().forEachLine { line ->
						writer.appendln(line.replace("@schema@", "file:/${file.absolutePath}"))
					}
				}
			}

			opts.addBindFile(bindingFile)
		}

		val text = cleanText(DslGenerator(opts).generate().replace(System.lineSeparator(), "\n"))

		assertEquals(code, text, "generated code is not the same")
	}

	private fun getExpectedClassText(): String {
		val inputStream = getInputStream()
		inputStream.use {
			return InputStreamReader(it).readText().replace(System.lineSeparator(), "\n")
		}
	}

	private fun getInputStream(): InputStream {
		val resName = "/code/${testName.methodName}.kt"
		return javaClass.getResourceAsStream(resName)
	}

	private fun cleanText(text: String): String {
		return text.split("\n").joinToString("\n", transform = String::trimEnd)
	}
}