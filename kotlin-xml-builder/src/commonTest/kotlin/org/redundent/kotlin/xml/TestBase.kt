package org.redundent.kotlin.xml

import kotlin.test.assertEquals

private val testData = mapOf(
	"NodeTest/addElementsAfter.xml" to """
		<root>
			<first/>
			<second/>
			<third/>
			<new1/>
			<new2/>
			<fourth/>
			<fifth/>
		</root>
	""".trimIndent(),
	"NodeTest/addElementsBefore.xml" to """
		<root>
			<first/>
			<second/>
			<new1/>
			<new2/>
			<third/>
			<fourth/>
			<fifth/>
		</root>
	""".trimIndent(),
	"SitemapTest/basicTest.xml" to """
		<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
			<url>
				<loc>
					http://blog.redundent.org/post/1
				</loc>
			</url>
			<url>
				<loc>
					http://blog.redundent.org/post/2
				</loc>
			</url>
			<url>
				<loc>
					http://blog.redundent.org/post/3
				</loc>
			</url>
		</urlset>
	""".trimIndent(),
	"SitemapTest/allElements.xml" to """
		<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
			<url>
				<loc>
					http://blog.redundent.org
				</loc>
				<lastmod>
					2017-10-24
				</lastmod>
				<changefreq>
					hourly
				</changefreq>
				<priority>
					0.5
				</priority>
			</url>
		</urlset>
	""".trimIndent(),
	"SitemapTest/sitemapIndex.xml" to """
		<sitemapindex xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
			<sitemap>
				<loc>
					http://blog.redundent.org/sitemap1.xml
				</loc>
				<lastmod>
					2017-10-24
				</lastmod>
			</sitemap>
			<sitemap>
				<loc>
					http://blog.redundent.org/sitemap2.xml
				</loc>
				<lastmod>
					2016-01-01
				</lastmod>
			</sitemap>
			<sitemap>
				<loc>
					http://blog.redundent.org/sitemap3.xml
				</loc>
			</sitemap>
		</sitemapindex>
	""".trimIndent(),
	"XmlBuilderTest/cdata.xml" to """
		<root>
			<![CDATA[Some & xml]]>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/comment.xml" to """
		<root>
			<!-- my comment &#45;&#45;> -->
			<someNode>
				value
			</someNode>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/basicTest.xml" to """
		<urlset xmlns="https://www.sitemaps.org/schemas/sitemap/0.9">
			<url>
				<loc>
					https://google.com/0
				</loc>
			</url>
			<url>
				<loc>
					https://google.com/1
				</loc>
			</url>
			<url>
				<loc>
					https://google.com/2
				</loc>
			</url>
		</urlset>
	""".trimIndent(),
	"XmlBuilderTest/emptyRoot.xml" to "<root/>",
	"XmlBuilderTest/xmlEncode.xml" to """
		<root>
			&amp;&lt;&gt;
		</root>
	""".trimIndent(),
	"XmlBuilderTest/addElement.xml" to """
		<root>
			<a/>
			<b/>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/parseCData.xml" to """
		<root>
			<![CDATA[
		Some & xml
		]]>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/whitespace.xml" to "<root><a> </a><b>\n</b></root>",
	"XmlBuilderTest/emptyString.xml" to "<root><a/> <b/></root>",
	"XmlBuilderTest/cdataNesting.xml" to """
		<root>
			<![CDATA[<![CDATA[Some & xml]]]]><![CDATA[>]]>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/elementValue.xml" to """
		<root>
			<name>
				value
			</name>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/emptyElement.xml" to """
		<root>
			<test/>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/doctypePublic.xml" to """
		<!DOCTYPE root PUBLIC "-//redundent//PUBLIC DOCTYPE//EN" "test.dtd">
		<root/>
	""".trimIndent(),
	"XmlBuilderTest/doctypeSimple.xml" to """
		<!DOCTYPE root>
		<root/>
	""".trimIndent(),
	"XmlBuilderTest/doctypeSystem.xml" to """
		<!DOCTYPE root SYSTEM "test.dtd">
		<root/>
	""".trimIndent(),
	"XmlBuilderTest/removeElement.xml" to """
		<root>
			<a/>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/parseBasicTest.xml" to """
		<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
			<url>
				<loc>
					http://google.com/0
				</loc>
			</url>
			<url>
				<loc>
					http://google.com/1
				</loc>
			</url>
			<url>
				<loc>
					http://google.com/2
				</loc>
			</url>
		</urlset>
	""".trimIndent(),
	"XmlBuilderTest/parseXmlEncode.xml" to """
		<root>
			&amp;&lt;&gt;
		</root>
	""".trimIndent(),
	"XmlBuilderTest/replaceElement.xml" to """
		<root>
			<a/>
			<c/>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/selfClosingTag.xml" to """
		<root>
			<element/>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/addElementAfter.xml" to """
		<root>
			<a/>
			<c/>
			<b/>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/elementAsString.xml" to """
		<root>
			<name>
				value
			</name>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/updateAttribute.xml" to """<root key="otherValue"/>""",
	"XmlBuilderTest/zeroSpaceIndent.xml" to """
		<root>
		<element>
		Hello
		</element>
		<otherElement>
		Test
		</otherElement>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/addElementBefore.xml" to """
		<root>
			<a/>
			<c/>
			<b/>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/customNamespaces.xml" to """
		<root xmlns="https://someurl.org" xmlns:t="https://t.org">
			<t:element>
				Test
			</t:element>
			<p xmlns="https://t.co"/>
			<d:p xmlns:d="https://b.co"/>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/noSelfClosingTag.xml" to """
		<root>
			<element></element>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/quoteInAttribute.xml" to """<root attr="My &quot; Attribute value &apos;"/>""",
	"XmlBuilderTest/advancedNamespaces.xml" to """
		<a:root xmlns="https://ns2.org" xmlns:a="https://ns1.org">
			<b:node xmlns:b="https://ns3.org" xmlns:c="https://ns4.org" attr1="value" c:attr2="value"/>
			<child xmlns:d="https://ns5.org" xmlns:e="https://ns6.org" d:key1="value1" d:key2="value2" e:key3="value3" a:key4="value4">
				<d:sub e:key5="value5"/>
			</child>
		</a:root>
	""".trimIndent(),
	"XmlBuilderTest/characterReference.xml" to """
		<root>
			<element>Hello &#38; Goodbye</element>
			<otherElement>Test</otherElement>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/multipleAttributes.xml" to """
		<root>
			<test key="value" otherAttr="hello world"/>
			<attributes test="value" key="pair"/>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/notPrettyFormatting.xml" to "<root><element>Hello</element><otherElement>Test</otherElement></root>",
	"XmlBuilderTest/parseCDataWhitespace.xml" to """
		<root>
			<![CDATA[


		    	    some
		free         
				form			
			            text	      
			            

		]]>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/unsafeAttributeValue.xml" to """
		<root test="&#456;">
			&#123;
		</root>
	""".trimIndent(),
	"XmlBuilderTest/parseCustomNamespaces.xml" to """
		<root xmlns="http://someurl.org" xmlns:t="http://t.org">
			<t:element>
				Test
			</t:element>
			<p xmlns="http://t.co"/>
			<d:p xmlns:d="http://b.co"/>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/processingInstruction.xml" to """
		<root>
			<?SomeProcessingInstruction?>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/singleLineTextElement.xml" to """
		<root>
			<element>Hello</element>
			<otherElement>Test</otherElement>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/singleLineCDATAElement.xml" to """
		<root>
			<element><![CDATA[Some & xml]]></element>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/specialCharInAttribute.xml" to """<root attr="&amp; &lt; &gt; &quot; &apos;"/>""",
	"XmlBuilderTest/parseMultipleAttributes.xml" to """
		<root>
			<test key="value" otherAttr="hello world"/>
			<attributes test="value" key="pair"/>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/addElementAfterLastChild.xml" to """
		<root>
			<a/>
			<b/>
			<c/>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/elementAsStringWithAttributes.xml" to """
		<root>
			<name attr="value" attr2="other"/>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/zeroSpaceIndentNoPrettyFormatting.xml" to "<root><element>Hello</element><otherElement>Test</otherElement></root>",
	"XmlBuilderTest/globalProcessingInstructionElement.xml" to """
		<?xml-stylesheet key="value" href="http://blah"?>
		<root>
			<element/>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/singleLineProcessingInstructionElement.xml" to """
		<root>
			<element><?SomeProcessingInstruction?></element>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/elementAsStringWithAttributesAndContent.xml" to """
		<root>
			<name attr="value">
				Content
			</name>
		</root>
	""".trimIndent(),
	"XmlBuilderTest/singleLineProcessingInstructionElementWithAttributes.xml" to """
		<root>
			<element><?SomeProcessingInstruction key="value"?></element>
		</root>
	""".trimIndent(),
	"OrderedNodesTest/correctOrder.xml" to """
		<xml>
			<first/>
			<second/>
		</xml>
	""".trimIndent()
)

open class TestBase {
	protected fun validate(xml: Node, printOptions: PrintOptions, testName: String) {
		val actual = xml.toString(printOptions)

		// Doing a replace to cater for different line endings.
		assertEquals(getExpectedXml(testName), actual.replace("\r\n", "\n"), "actual xml matches what is expected")
	}

	protected fun getExpectedXml(name: String): String {
		return testData[name]?.replace("\r\n", "\n")?.trim() ?: throw IllegalArgumentException("Could not found test data with $name")
	}
}
