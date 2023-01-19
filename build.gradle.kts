plugins {
	kotlin("jvm") version "1.3.60" apply false
	id("com.jfrog.bintray") version "1.8.4" apply false
	id("com.bmuschko.nexus") version "2.3.1" apply false
	jacoco
}

extra["kotlinVersion"] = "1.3.60"

allprojects {
	group = "org.redundent"
	version = "1.9.0"

	repositories {
		mavenCentral()
	}
}