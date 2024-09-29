//import kotlin.test.Test
//import kotlin.test.assertNotEquals
//import org.redundent.kotlin.xml.CDATAElement
//
//class FooTest {
//	@Test
//	fun x() {
//		val t1 = TextElement("a")
//		val t2 = SubTextElement("a")
//		assertNotEquals(t1, t2)
//	}
//}
//
//open class TextElement(
//	val text: String
//) {
//	override fun equals(other: Any?): Boolean {
//		if (this === other) return true
//		if (other !is TextElement) return false
//		if (text != other.text) return false
//		return true
//	}
//	override fun hashCode(): Int = text.hashCode()
//}
//
//class SubTextElement(text: String) : TextElement(text) {
//	override fun equals(other: Any?): Boolean {
//		if (this === other) return true
//		if (!super.equals(other)) return false
//		if (other !is CDATAElement) return false
//		return true
//	}
//
//	override fun hashCode(): Int {
//		var hash = 7
//		hash = 31 * hash + super.hashCode()
//		hash = 31 * hash + "SubTextElement".hashCode()
//		return hash
//	}
//}
