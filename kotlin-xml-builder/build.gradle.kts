import kotlin.io.path.createDirectories
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name
import kotlin.io.path.nameWithoutExtension
import kotlin.io.path.readText
import kotlin.io.path.writeText
import org.gradle.api.tasks.PathSensitivity.RELATIVE

plugins {
	conventions.`kotlin-multiplatform`
	conventions.publishing
	//id("org.jlleitschuh.gradle.ktlint")
}

kotlin {
	sourceSets {
		commonMain {
			dependencies {
			}
		}
		commonTest {
			dependencies {
				implementation(kotlin("test"))
			}
		}
		jvmMain {
			dependencies {
				compileOnly(kotlin("reflect"))
			}
		}
	}
}

val generateTestResultAccessors by tasks.registering {
	description = "Generate Kotlin classes so tests can access result data."
	// KMP does not have common support for accessing resources.

	val testResultsDir = layout.projectDirectory.dir("test-results")
	inputs.dir(testResultsDir).withPropertyName("testResultsDir")
		.normalizeLineEndings()
		.withPathSensitivity(RELATIVE)

	val outputDir = temporaryDir.toPath()
	outputs.dir(outputDir).withPropertyName("outputDir")

	doLast {
		testResultsDir.asFile.toPath().listDirectoryEntries().forEach { dir ->
			val clsName = dir.name
			val testDataClsName = "${dir.name}Results"
			val xmlData = dir.listDirectoryEntries("*.xml").joinToString("\n") { xmlFile ->
				"""
				|val ${xmlFile.nameWithoutExtension}: String
				|  get() = ${"\"\"\""}${xmlFile.readText()}${"\"\"\""}
				|
				""".trimMargin()
			}
			outputDir.resolve("${testDataClsName}.kt").apply {
				parent.createDirectories()
				writeText(
					"""
					|package org.redundent.kotlin.xml
					|
					|internal object $testDataClsName {
					|
					|$xmlData
					|
					|}
					|
					|internal val ${clsName}.Companion.testResults: $testDataClsName 
					|  get() = $testDataClsName
					|
					""".trimMargin()
				)
			}
		}
	}
}

kotlin {
	sourceSets.commonTest {
		kotlin.srcDir(generateTestResultAccessors)
	}
}
