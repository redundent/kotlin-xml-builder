[ ![Download](https://api.bintray.com/packages/redundent/maven/kotlin-xml-dsl-generator/images/download.svg) ](https://bintray.com/redundent/maven/kotlin-xml-dsl-generator/_latestVersion)

Kotlin XML Builder DSL Generator
=

This jar can be used to generate a typesafe DSL based on an XSD file.

**NOTE**: This is experimental and may not generate the exact DSL you expect. Please report
any issues. 

License
-
Apache 2.0

Usage
=
Usage is similar to generating java code with XJC. Simply run:

```bash
java -jar <path to kotlin-xml-gsl-generator.jar> -d <dest> -p <package> <path to schema>
```

You can also use JAXB bindings files to customize some of the generator code. Note that this is
very experimental and not guaranteed to work with all customizations.
