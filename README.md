[![Build Status](https://travis-ci.org/redundent/kotlin-xml-builder.svg?branch=master)](https://travis-ci.org/redundent/kotlin-xml-builder)
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

```kotlin
val people = xml("people") {
    xmlns = "http://example.com/people"
    element("person") {
        attribute("id", "1")
        element("firstName") {
            -"John"
        }
        element("lastName") {
            -"Doe"
        }
        element("phone") {
            -"555-555-5555"
        }
    }
}

val asString = people.toString()
```
produces
```xml
<people xmlns="http://github.com/people">
    <person id="1">
        <firstName>John</firstName>
        <lastName>Doe</lastName>
        <phone>555-555-5555</phone>
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
        element("person") {
            attribute("id", person.id)
            element("firstName") {
                -person.firstName
            }
            element("lastName") {
                -person.lastName
            }
            element("phone") {
                -person.phone
            }
        }
    }    
}

val asString = people.toString()
```
produces
```xml
<people xmlns="http://github.com/people">
    <person id="1">
        <firstName>John</firstName>
        <lastName>Doe</lastName>
        <phone>555-555-5555</phone>
    </person>
    <person id="2">
        <firstName>Jane</firstName>
        <lastName>Doe</lastName>
        <phone>555-555-6666</phone>
    </person>
</people>
```
