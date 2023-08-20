package org.redundent.kotlin.xml.gen.writer

import com.squareup.kotlinpoet.*
import com.sun.tools.xjc.outline.ClassOutline
import com.sun.tools.xjc.outline.Outline
import org.redundent.kotlin.xml.gen.ExOptions
import javax.xml.bind.annotation.XmlType

class XmlClass(
    val clazz: ClassOutline,
    private val opts: ExOptions,
    private val innerClass: Boolean = false
) : Code<TypeSpec> {
    val name: String = clazz.target.shortName
    val xmlns: String? =
        if (clazz.target.isElement && clazz.target.elementName.namespaceURI.isNotBlank()) clazz.target.elementName.namespaceURI else null

    private val superClass: ClassName = if (clazz.superClass?.target != null) {
        ClassName(clazz.superClass.target.ownerPackage.name(), clazz.superClass.target.shortName)
    } else {
        ClassName("org.redundent.kotlin.xml", "Node")
    }
    private val abstract: Boolean = clazz.target.isAbstract
    private val attributes: MutableList<XmlAttribute> = ArrayList()
    val memberElements: MutableList<XmlElement> = ArrayList()
    var rootElement: XmlElement? = null
    val innerClasses: MutableList<XmlClass> = ArrayList()

    private val modifier = when {
        abstract -> KModifier.ABSTRACT
        innerClass -> KModifier.INNER
        else -> KModifier.OPEN
    }

    init {
        clazz.target.attributes.map {
            val field = clazz.parent().getField(it)
            val name = it.xmlName.localPart
            val type = mapType(field.rawType.fullName()).copy(nullable = !it.isRequired)

            XmlAttribute(
                name,
                type,
                it.xmlName.namespaceURI,
                it.xmlName.prefix
            )
        }.forEach { attributes.add(it) }

        clazz.target.elements.forEach { element ->
            element.types.forEach { type ->
                memberElements.add(
                    XmlElement(
                        type.tagName.localPart,
                        type.target,
                        type.tagName.localPart,
                        element.documentation,
                        if (opts.useMemberFunctions) null else clazz.target
                    )
                )
            }
        }

        if (clazz.target.isElement) {
            val name = clazz.target.elementName.localPart
            rootElement = XmlElement(name, clazz.target, name, clazz.target.documentation)
        }
    }

    override fun build(outline: Outline): TypeSpec {
        val classBuilder = TypeSpec.classBuilder(name)
            .addModifiers(modifier)
            .superclass(superClass)

        if (!innerClass) {
            classBuilder
                .primaryConstructor(
                    FunSpec.constructorBuilder()
                        .addParameter("nodeName", String::class.asClassName())
                        .build()
                )
                .addSuperclassConstructorParameter("nodeName")
        } else {
            classBuilder.addSuperclassConstructorParameter("%S", name)
        }

        clazz.target.documentation?.also { classBuilder.addKdoc(it) }

        if (clazz.target.isOrdered && clazz.target.elements.size > 1) {
            classBuilder.addAnnotation(
                AnnotationSpec.builder(ClassName("org.redundent.kotlin.xml", "XmlType"))
                    .addMember(
                        "childOrder = arrayOf(%L)",
                        clazz.target.elements.joinToString(",\n\t\t") { "\"${it.types.first().tagName.localPart}\"" }
                    )
                    .build()
            )
        }

        if (xmlns != null) {
            classBuilder.addInitializerBlock(
                CodeBlock.builder()
                    .addStatement("xmlns = %S", xmlns)
                    .build()
            )
        }
        if (attributes.isNotEmpty()) {
            classBuilder.addProperties(
                attributes.map { it.build(outline) }
            )
        }

        if (opts.useMemberFunctions) {
            if (memberElements.isNotEmpty()) {
                classBuilder.addFunctions(
                    memberElements.map { it.build(outline) }
                )
            }
        }

        if (innerClasses.isNotEmpty()) {
            classBuilder.addTypes(
                innerClasses.map { it.build(outline) }
            )
        }

        return classBuilder.build()
    }

    fun addInnerClass(xmlClass: XmlClass) {
        innerClasses.add(xmlClass)
    }
}