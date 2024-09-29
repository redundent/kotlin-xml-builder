plugins {
//	id("org.jlleitschuh.gradle.ktlint")
	conventions.publishing
	conventions.`kotlin-multiplatform`
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
