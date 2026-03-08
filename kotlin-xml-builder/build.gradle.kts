import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
	kotlin("multiplatform")
	`maven-publish`
	signing
	id("org.jlleitschuh.gradle.ktlint")
}

kotlin {
	jvm()
	js(IR) {
		browser()
		nodejs()
	}
	iosArm64()
	linuxX64()
	linuxArm64()
	mingwX64()
//
//	val hostOs = System.getProperty("os.name")
//	val isMingwX64 = hostOs.startsWith("Windows")
//	val nativeTarget: KotlinNativeTarget
//	when {
//		hostOs == "Mac OS X" -> nativeTarget = macosX64("native")
//		hostOs == "Linux" -> nativeTarget = linuxX64("native")
//		isMingwX64 -> nativeTarget = mingwX64("native")
//		else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
//	}

	sourceSets {
		val commonTest by getting {
			dependencies {
				implementation(kotlin("test"))
			}
		}
		val jvmTest by getting {
			dependencies {
				implementation(kotlin("test-junit"))
			}
		}
	}
}
