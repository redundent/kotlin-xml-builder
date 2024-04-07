package conventions

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
	id("conventions.base")
	kotlin("multiplatform")
}

kotlin {
	jvm()

	js(IR) {
		browser()
		nodejs()
	}

	//region Kotlin Native targets
	// According to https://kotlinlang.org/docs/native-target-support.html
	// Tier 1
	macosX64()
	macosArm64()
	iosSimulatorArm64()
	iosX64()

	// Tier 2
	linuxX64()
	linuxArm64()
	watchosSimulatorArm64()
	watchosX64()
	watchosArm32()
	watchosArm64()
	tvosSimulatorArm64()
	tvosX64()
	tvosArm64()
	iosArm64()

	// Tier 3
	mingwX64()
	//endregion

	jvmToolchain(8)

	@OptIn(ExperimentalKotlinGradlePluginApi::class)
	compilerOptions {
		languageVersion = KotlinVersion.KOTLIN_1_6
		apiVersion = KotlinVersion.KOTLIN_1_6
	}
}

tasks.withType<Test>().configureEach {
	useJUnitPlatform()
}
