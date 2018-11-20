import org.gradle.jvm.tasks.Jar

plugins {
	kotlin("jvm") version "1.2.60" apply false
	id("com.jfrog.bintray") version "1.8.4" apply false
	jacoco
}

extra["kotlinVersion"] = "1.2.60"

allprojects {
	group = "org.redundent"
	version = "1.4.4"

	repositories {
		jcenter()
	}
}