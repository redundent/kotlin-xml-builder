package org.redundent.kotlin.xml.gen

import org.junit.Test
import java.io.File

class SimpleSchemGen : AbstractGenTest() {
	@Test
	fun sitemap() = run()

	@Test
	fun attributes() = run()

	@Test
	fun `attributes-required`() = run()

	@Test
	fun nested() = run()

	@Test
	fun referencedType() = run()

	@Test
	fun group() = run()

	@Test
	fun extension() = run()

	@Test
	fun `string-to-bytearray`() = run(withBindingFile = true)

	@Test
	fun `target-namespace`() = run()

	@Test
	fun choice() = run()
}