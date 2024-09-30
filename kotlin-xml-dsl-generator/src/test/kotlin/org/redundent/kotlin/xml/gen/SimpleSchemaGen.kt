package org.redundent.kotlin.xml.gen

import kotlin.test.Test

class SimpleSchemaGen : AbstractGenTest() {
	@Test
	fun sitemap() = run("sitemap")

	@Test
	fun attributes() = run("attributes")

	@Test
	fun `attributes-required`() = run("attributes-required")

	@Test
	fun nested() = run("nested")

	@Test
	fun referencedType() = run("referencedType")

	@Test
	fun group() = run("group")

	@Test
	fun extension() = run("extension")

	@Test
	fun `string-to-bytearray`() = run("string-to-bytearray", withBindingFile = true)

	@Test
	fun `target-namespace`() = run("target-namespace")

	@Test
	fun choice() = run("choice")

	@Test
	fun `duplicate-names`() = run("duplicate-names")

	@Test
	fun `child-prop-order`() = run("child-prop-order")

	@Test
	fun `member-functions`() = run("member-functions", additionalArgs = arrayOf("--use-member-functions"))
}
