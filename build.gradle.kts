import org.gradle.jvm.tasks.Jar

plugins {
	kotlin("jvm") version "1.2.21" apply false
	id("com.jfrog.bintray") version "1.5" apply false
	jacoco
}


extra["kotlinVersion"] = "1.2.21"

allprojects {
	group = "org.redundent"
	version = "1.4"

	repositories {
		jcenter()
	}
}

tasks {
	"wrapper"(Wrapper::class) {
		gradleVersion = "4.5.1"
	}
}
