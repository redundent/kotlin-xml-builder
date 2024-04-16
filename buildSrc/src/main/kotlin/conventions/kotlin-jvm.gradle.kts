package conventions

import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
	id("conventions.base")
	kotlin("jvm")
}

kotlin {
	jvmToolchain(8)

	compilerOptions {
		languageVersion = KotlinVersion.KOTLIN_1_6
	}
}

java {
	withSourcesJar()
}
