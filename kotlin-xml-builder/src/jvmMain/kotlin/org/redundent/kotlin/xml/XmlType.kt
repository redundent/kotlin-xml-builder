package org.redundent.kotlin.xml

@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class XmlType(val childOrder: Array<String>)
