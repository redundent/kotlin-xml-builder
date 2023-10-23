![CI](https://github.com/redundent/kotlin-xml-builder/workflows/CI/badge.svg)
[![Download](https://maven-badges.herokuapp.com/maven-central/org.redundent/kotlin-xml-builder/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.redundent/kotlin-xml-builder)

Kotlin XML Builder
=

This library can be used to build xml documents from Kotlin code.
It is based on the HTML builder described in the [Kotlin docs](https://kotlinlang.org/docs/reference/type-safe-builders.html)

It is designed to be lightweight and fast. There isn't any validation except to 
escape text to not violate xml standards.

License
-
Apache 2.0

Usage
=
To use in Gradle, simply add the Maven Central repository and then add the following dependency.
```gradle
repositories {
    mavenCentral()
}

dependencies {
    compile("org.redundent:kotlin-xml-builder:[VERSION]")
}
```

Similarly in Maven:
```xml
<dependencies>
    <dependency>
        <groupId>org.redundent</groupId>
        <artifactId>kotlin-xml-builder</artifactId>
        <version>[VERSION]</version>
    </dependency>
</dependencies>
```

Example
=
```kotlin
val people = xml("people") {
    xmlns = "http://example.com/people"
    "person" {
        attribute("id", 1)
        "firstName" {
            -"John"
        }
        "lastName" {
            -"Doe"
        }
        "phone" {
            -"555-555-5555"
        }
    }
}

val asString = people.toString()
```
produces
```xml
<people xmlns="http://example.com/people">
    <person id="1">
        <firstName>
            John
        </firstName>
        <lastName>
            Doe
        </lastName>
        <phone>
            555-555-5555
        </phone>
    </person>
</people>
```

```kotlin
class Person(val id: Long, val firstName: String, val lastName: String, val phone: String)

val listOfPeople = listOf(
    Person(1, "John", "Doe", "555-555-5555"),
    Person(2, "Jane", "Doe", "555-555-6666")
)

val people = xml("people") {
    xmlns = "http://example.com/people"
    for (person in listOfPeople) {
        "person" {
            attribute("id", person.id)
            "firstName" {
                -person.firstName
            }
            "lastName" {
                -person.lastName
            }
            "phone" {
                -person.phone
            }
        }
    }    
}

val asString = people.toString()
```
produces
```xml
<people xmlns="http://example.com/people">
    <person id="1">
        <firstName>
            John
        </firstName>
        <lastName
            >Doe
        </lastName>
        <phone>
            555-555-5555
        </phone>
    </person>
    <person id="2">
        <firstName>
            Jane
        </firstName>
        <lastName>
            Doe
        </lastName>
        <phone>
            555-555-6666
        </phone>
    </person>
</people>
```

### Namespaces
Version 1.8.0 added more precise control over how namespaces are used. You can add a namespace to any element or attribute
and the correct node/attribute name will be used automatically. When using the new namespace aware methods, you no longer
need to manually add the namespace to the element.

See examples of `< 1.8.0` and `>= 1.8.0` below to produce the following xml

```xml
<t:root xmlns:t="https://ns.org">
    <t:element t:key="value"/>
</t:root>
```

#### < 1.8.0
```kotlin
xml("t:root") {
    namespace("t", "https://ns.org")
    "t:element"("t:key" to "value")
}
```

#### &gt;= 1.8.0
```kotlin
val ns = Namespace("t", "https://ns.org")
xml("root", ns) {
    "element"(ns, Attribute("key", "value", ns))
}
```

You can also use the `Namespace("https://ns.org")` constructor to create a Namespace object that represents the default xmlns.

#### Things to be aware of

* Previously, all namespace declarations would get added to the attributes maps immediately. That no long happens. All
namespaces get added at render time. To retrieve a list of the current namespaces of a node, use the `namespaces` property.
* When a namespace is provided for a node or attribute, it will be declared on that element IF it is not already declared
on the root element or one of the element's parents.

### Processing Instructions
You can add processing instructions to any element by using the `processingInstruction` method.

```kotlin
xml("root") {
    processingInstruction("instruction")
}
```

```xml
<root>
    <?instruction?>
</root>
```

#### Global Instructions
Similarly you can add a global (top-level) instruction by call `globalProcessingInstruction` on the
root node. This method only applies to the root. If it is called on any other element, it will be ignored.


```kotlin
xml("root") {
    globalProcessingInstruction("xml-stylesheet", "type" to "text/xsl", "href" to "style.xsl")
}
```

```xml
<?xml-stylesheet type="text/xsl" href="style.xsl"?>
<root/>
```

## DOCTYPE

As of version 1.7.4, you can specify a DTD (Document Type Declaration).

```kotlin
xml("root") {
    doctype(systemId = "mydtd.dtd")
}
```

```xml
<!DOCTYPE root SYSTEM "mydtd.dtd">
<root/>
```

### Limitations with DTD

Complex DTDs are not supported.

## Unsafe
You can now use unsafe text for element and attribute values.
```kotlin
xml("root") {
	unsafeText("<xml/>")
}
```
produces
```xml
<root>
    <xml/>
</root>
```

```kotlin
xml("root") {
	attribute("attr", unsafe("&#123;"))
}
```
produces
```xml
<root attr="&amp;#123;"/>
```

## Print Options
You can now control how your xml will look when rendered by passing the new PrintOptions class as an argument to `toString`.

`pretty` - This is the default and will produce the xml you see above.

`singleLineTextElements` - This will render single text element nodes on a single line if `pretty` is true
```xml
<root>
    <element>value</element>
</root>
```
as opposed to:
```xml
<root>
    <element>
        value
    </element>
</root>
```
`useSelfClosingTags` - Use `<element/>` instead of `<element></element>` for empty tags

`useCharacterReference` - Use character references instead of escaped characters. i.e. `&#39;` instead of `&apos;`

## Reading XML
You can also read xml documents using the `parse` methods. They provide basic 
xml parsing and will build a `Node` element to build upon.

For more advanced consuming, check out [konsume-xml](https://gitlab.com/mvysny/konsume-xml).
It includes many more features for consuming documents.

Release Notes
=============
Version 1.9.1
-
* Adding `addElement`, `addElements`, `addElementsBefore`, `addElementsAfter`, `removeElement`,
`removeElements`, and `replaceElement` to Node.\
Thanks to [@csmile2](https://github.com/csmile2) for requesting this!
* Deprecating `addNode`, `addNodeBefore`, `addNodeAfter`, `removeNode`, and `replaceNode` in favor of Element methods.
* Adding ktlint checks

Version 1.9.0
-
* Adding `unsafe` and `unsafeText` methods to allow for unescaped values in elements and attributes.\
Thanks to [@krzysztofsroga](https://github.com/krzysztofsroga) for requesting this!

**BREAKING CHANGES**
* The `attributes` property on a node will now return an immutable map (`Map` instead of `LinkedHashMap`). This property can no longer be used to\
manipulate the attributes. Use `set`/`removeAttribute` for that.
 
Version 1.8.0
-
* Adding more precise functionality for xml namespaces, allowing typesafe building of elements and attributes with namespaces.\
Thanks to [@asm0dey](https://github.com/asm0dey) for requesting this!

Version 1.7.4
-
* Adding ability to add a DTD.\
Thanks to [@anskotid](https://github.com/anskotid) for raising this!

Version 1.7.3
-
* Making `private fun parse(Document)` public.\
Thanks to [@rkklai](https://github.com/rkklai) for requesting this!

Version 1.7.2
-
* Fixing issue where a text element that contains an empty string doesn't respect `useSelfClosingTags`.\
Thanks to [@d-wojciechowski](https://github.com/d-wojciechowski) for finding this!

Version 1.7.1
-
* Added new PrintOptions to control the indent character(s) used. `indent` default is `\t`.\
Thanks to [@vRallev](https://github.com/vRallev) for adding this!

Version 1.7
-
**POTENTIAL BREAKING CHANGES**
* All node types override `equals` and `hashCode`. This could change the behavior of putting nodes in a Set or Map.\
Thanks to [@cbartolome](https://github.com/cbartolome) for requesting this!

Version 1.6.1
-
* Added new PrintOptions to use character references instead of HTML names.\
Thanks to [@senecal-jjs](https://github.com/senecal-jjs) and [@sleddog](https://github.com/sleddor) for
adding this!

Version 1.6.0
-
* Updated README

**BREAKING CHANGES**
* The `kotlin-reflect` dependency has been removed from the transitive dependnecies.
This module is only used for controlling element order using `@XmlType`.
If your project depends on that feature, you will need to have `kotlin-reflect` on the 
runtime classpath.\
Thanks to [@mvysny](https://github.com/mvysny) for requesting this!

Version 1.5.4
-
* Adding new global processing instructions.\
Thanks to [@rjaros](https://github.com/rjaros) for requsting this!

Version 1.5.3
-
* Fixing single line text element rendering for processing instructures and CData elements.\
Thanks to [@jonathan-yan](https://github.com/jonathan-yan) for fixing this!

Version 1.5.2
-
* Added ability to specify the xml version. This affects both the prolog and text escaping.\
Thanks to [@ZR8C](https://github.com/ZR8C) for finding this!

Version 1.5.1
-
* Added ability to add processing instructions. Use the new `processingInstruction` method to do this.\
Thanks to [@endofhome](https://github.com/endofhome) for adding this!
* Added ability to add comments. Use the new `comment` method to do this.\
Thanks to [@ejektaflex](https://github.com/ejektaflex) for requesting this!

Version 1.5.0
-
* Added more robust PrintOptions class to allow for more control over how xml is structured.\
Fixes issue #16
* Attribute values are now fully escaped properly.\
Thanks to [@pkulak](https://github.com/pkulak) for finding and fixing this!

**BREAKING CHANGES**
* Changed Element.render method signature to use kotlin.text.Appenable instead of kotlin.text.StringBuilder.
Any custom element types created will need to be updated

Version 1.4.5
-
* Fixed incorrect handling of CDATA elements. `prettyFormat` should not alter the CDATA content.
* Fixed nested CData elements\
Big thanks to [@TWiStErRob](https://github.com/TWiStErRob) for finding and fixing both of these!

Version 1.4.4
-
* Switched to Kotlin's `Charsets` instead of using `java.nio.charset.StandardCharsets`. `StandardCharsets` is not available in some versions of Android.\
Thanks to [@little-fish](https://github.com/little-fish) for submitting and fixing this!

Version 1.4.3
-
**BREAKING CHANGES**
* Moved `prettyFormat` to a parameter of `toString()`. `prettyFormat` in the constructor or util method is no longer available. Fixes issue #7

Version 1.4.2
-
* Fixes issue #6

Version 1.4.1
-
* Upgrading Gradle to 4.9
* Upgrading Kotlin to 1.2.60
* Fix issue #4
* Tweak generation code to be more concise
* Add flag to code generation to allow for member functions instead of extension functions

Version 1.4
-
**BREAKING CHANGES**
* `org.redundent.kotlin.xml.Node.name` has been renamed to `org.redundent.kotlin.xml.Node.nodeName` to avoid clashes with attributes called `name`.
---
* Adding DSL generator project to generate kotlin-xml-builder DSL from a schema file.
See [kotlin-xml-dsl-generator](./kotlin-xml-dsl-generator) for details

Version 1.3
-
* Added ability to parse an xml document into a builder object using the new `parse` method.
* Upgrading Gradle and Kotlin versions.

Version 1.2
-
* Added a `sitemap` method to allow for easy generation of sitemaps (which is what this project was created for in the first place).
* Added `String.invoke` for elements allowing you to specify elements by just their name (see docs above)
* Added some searching methods to search child nodes. `filter`, `first`, `firstOrNull`, and `exists`.
* Added some mutation methods to allow you to add/remove/replace nodes in an element. 

Version 1.1
-
* Added convenience method for elements with just a name and value. `element("name", "value")`
* Removed `ns` method. Please use `namespace(...)` instead.
* Upgraded Gradle version
