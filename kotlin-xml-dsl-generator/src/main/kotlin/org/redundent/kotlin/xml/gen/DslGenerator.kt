package org.redundent.kotlin.xml.gen

import com.sun.codemodel.JCodeModel
import com.sun.tools.xjc.BadCommandLineException
import com.sun.tools.xjc.ErrorReceiver
import com.sun.tools.xjc.ModelLoader
import com.sun.tools.xjc.Options
import com.sun.tools.xjc.generator.bean.BeanGenerator
import org.redundent.kotlin.xml.gen.writer.CodeWriter
import org.xml.sax.SAXParseException
import java.io.File

fun main(args: Array<String>) {
	try {
		val opts = ExOptions().apply { parseArguments(args) }
		val output = File(opts.targetDir, "${opts.defaultPackage.replace(".", File.separator)}${File.separator}schema.kt")

		println("\nGenerating schema to ${output.absolutePath}")

		val generated = DslGenerator(opts, output).generate()

		output.parentFile.mkdirs()

		output.writer().use {
			it.append(generated)
		}
	} catch (e: BadCommandLineException) {
		// there was an error in the command line.
		// print usage and abort.
		if (e.message != null) {
			println("\n${e.message}")
			println()
		}

		usage()
		System.exit(-1)
	}
}

fun usage() {
	println("""Usage: java -jar kotlin-xml-dsl-generator.jar [-options ...] <schema file> ... [-b <bindinfo>] ...

Options:
  -b <file>          :  specify external JAXB bindings files
                        This is experimental
  -d <dir>           :  generated files will go into this directory
  -p <pkg>           :  specifies the target package"""
	)
}

class DslGenerator(private val opts: ExOptions, private val output: File) {
	fun generate(): String {
		val model = ModelLoader.load(opts, JCodeModel(), ErrReceiver())
				?: throw BadCommandLineException("Something failed generating the code model")

		val outline = BeanGenerator.generate(model, ErrReceiver())

		val codeWriter = CodeWriter(outline, opts.defaultPackage, output.absolutePath)
		val schemaOutline = SchemaOutline(outline, opts)

		codeWriter.writeSuppress {
			addAll(
				listOf(
					"PropertyName",
					"LocalVariableName",
					"FunctionName",
					"RedundantVisibilityModifier"
				)
			)

			if (schemaOutline.enums.isNotEmpty()) {
				add("EnumEntryName")
			}
		}

		schemaOutline.enums
			.map { it.build(outline) }
			.forEach { codeWriter.kotlinFile.addType(it) }

		for (kls in schemaOutline.classes) {
			codeWriter.kotlinFile.addType(kls.build(outline))

			if (!opts.useMemberFunctions) {
				val allMembers = kls.innerClasses.flatMap { it.memberElements } +
						kls.memberElements

				allMembers
					.map { it.build(outline) }
					.forEach { codeWriter.kotlinFile.addFunction(it) }
			}

			if (kls.rootElement != null) {
				codeWriter.kotlinFile.addFunction(kls.rootElement!!.build(outline))
			}
		}

		return codeWriter.asText()
	}
}

class ErrReceiver : ErrorReceiver() {
	override fun warning(exception: SAXParseException) {
		println(exception)
	}

	override fun info(exception: SAXParseException) {
		println(exception)
	}

	override fun error(exception: SAXParseException) = throw exception
	override fun fatalError(exception: SAXParseException) = throw exception
}

class ExOptions : Options() {
	var useMemberFunctions = false

	override fun parseArgument(args: Array<out String>, i: Int): Int {
		if (args[i] == "--use-member-functions") {
			useMemberFunctions = true
			return 1
		}

		return super.parseArgument(args, i)
	}
}