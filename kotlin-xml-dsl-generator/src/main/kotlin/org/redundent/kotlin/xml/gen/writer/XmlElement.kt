package org.redundent.kotlin.xml.gen.writer

import com.squareup.kotlinpoet.*
import com.sun.tools.xjc.model.CClassInfo
import com.sun.tools.xjc.model.CNonElement
import com.sun.tools.xjc.outline.Outline

class XmlElement(
    val name: String,
    val type: CNonElement,
    private val tagName: String,
    private val documentation: String?,
    private val parent: CClassInfo? = null
) : Code<FunSpec> {

    override fun build(outline: Outline): FunSpec {
        val rootElement = parent == null

        val funSpec = FunSpec.Companion.builder(name)
        if (!rootElement) {
            funSpec.receiver(parent!!.fullType())
        }

        documentation?.also { funSpec.addKdoc(it) }

        val codeBlock = CodeBlock.builder()

        if (type is CClassInfo) {
            val parent = type.parent()
            val typeName = type.fullType()

            if (type.hasOptionalAttributes) {
                funSpec.addAnnotation(JvmOverloads::class.asClassName())
            }

            funSpec.addParameters(type.attributesAsParameters(outline))
            funSpec.addParameter(
                "__block__",
                LambdaTypeName.get(
                    receiver = typeName,
                    returnType = Unit::class.asClassName()
                )
            )

            if (rootElement) {
                funSpec.returns(typeName)
            }

            if (parent is CClassInfo) {
                codeBlock.addStatement("val %N = %N()", tagName, typeName.simpleName)
            } else {
                codeBlock.addStatement("val %N = %T(%S)", tagName, typeName, tagName)
            }

            if (type.allAttributes.isNotEmpty()) {
                codeBlock
                    .beginControlFlow("%N.apply", tagName)

                for (attr in type.allAttributes) {
                    val attrName = attr.xmlName.localPart
                    if (!attr.isRequired) {
                        codeBlock
                            .beginControlFlow("if (%N != null)", attrName)
                            .addStatement("this.%N = %N", attrName, attrName)
                            .endControlFlow()
                    } else {
                        codeBlock.addStatement("this.%N = %N", attrName, attrName)
                    }
                }
                codeBlock.endControlFlow()
            }

            codeBlock.addStatement("%N.apply(__block__)", tagName)

            if (rootElement) {
                codeBlock.addStatement("return %N", tagName)
            } else {
                codeBlock.addStatement("this.addNode(%N)", tagName)
            }
        } else {
            val t = mapType(type.type.fullName())
            funSpec.addParameter("value", t)

            if (t != String::class.asClassName()) {
                codeBlock.addStatement("%S(%N.toString())", tagName, "value")
            } else {
                codeBlock.addStatement("%S(%N)", tagName, "value")
            }
        }

        return funSpec.addCode(codeBlock.build()).build()
    }

    fun CClassInfo.attributesAsParameters(outline: Outline): List<ParameterSpec> {
        if (allAttributes.isEmpty()) {
            return emptyList()
        }

        val sortedAttributes = allAttributes.sortedWith { o1, o2 ->
            (if (o1.isRequired) 0 else 1).compareTo(if (o2.isRequired) 0 else 1)
        }

        return sortedAttributes.map {
            val field = outline.getField(it)
            val builder = ParameterSpec.builder(
                it.xmlName.localPart,
                mapType(field.rawType.fullName()).copy(nullable = !it.isRequired)
            )
            if (!it.isRequired) {
                builder.defaultValue("null")
            }

            builder.build()
        }
    }

    fun CClassInfo.fullType(): ClassName {
        val seq = generateSequence(this) { it.parent() as? CClassInfo? }
            .toList()
            .reversed()

        val first = seq.first()
        var typeName = ClassName(first.ownerPackage.name(), first.shortName)
        for (t in seq.drop(1)) {
            typeName = typeName.nestedClass(t.shortName)
        }

        return typeName
    }
}