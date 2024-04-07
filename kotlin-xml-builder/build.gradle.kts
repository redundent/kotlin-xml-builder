plugins {
//	id("org.jlleitschuh.gradle.ktlint")
	conventions.publishing
	conventions.`kotlin-multiplatform`
//	id("com.goncalossilva.resources") version "0.4.1"
}

kotlin {
	sourceSets {
		commonMain {
			dependencies {
				implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
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
