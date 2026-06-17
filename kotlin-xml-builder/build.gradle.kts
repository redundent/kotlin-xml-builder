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
