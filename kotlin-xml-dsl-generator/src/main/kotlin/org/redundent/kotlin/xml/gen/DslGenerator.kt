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
		val opts = Options().apply { parseArguments(args) }
		val output = File(opts.targetDir, "${opts.defaultPackage.replace(".", File.separator)}${File.separator}schema.kt")

		println("\nGenerating schema to ${output.absolutePath}")

		val generated = DslGenerator(opts).generate()

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

class DslGenerator(private val opts: Options) {
	fun generate(): String {
		val model = ModelLoader.load(opts, JCodeModel(), ErrReceiver()) ?: throw BadCommandLineException("Something failed generating the code model")

		val outline = BeanGenerator.generate(model, ErrReceiver())

		val codeWriter = CodeWriter(outline)
		codeWriter.writeln("@file:Suppress(\"PropertyName\", \"ReplaceArrayOfWithLiteral\", \"LocalVariableName\", \"FunctionName\", \"RemoveEmptyClassBody\")\n")
		codeWriter.writePackage(opts.defaultPackage)
		codeWriter.writeImport("org.redundent.kotlin.xml.*\n")

		codeWriter.writeSimpleTypes()

		codeWriter.writeClasses()

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