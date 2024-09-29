plugins {
	conventions.`kotlin-multiplatform`
	conventions.publishing
	//id("org.jlleitschuh.gradle.ktlint")
}

kotlin {
	sourceSets {
		commonMain {
			dependencies {
				//implementation(kotlin("reflect"))
			}
		}
		commonTest {
			dependencies {
				implementation(kotlin("test"))
			}
		}
	}
}
