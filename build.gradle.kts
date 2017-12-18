import com.jfrog.bintray.gradle.BintrayExtension
import org.gradle.jvm.tasks.Jar

plugins {
	kotlin("jvm") version "1.2.0"
	`maven-publish`
	id("com.jfrog.bintray") version "1.5"
	jacoco
}

group = "org.redundent"
version = "1.3"

val kotlinVersion = "1.2.0"

repositories {
	jcenter()
}

tasks {
	val jar by getting(Jar::class)

	"sourceJar"(Jar::class) {
		from(java.sourceSets["main"].allSource)
		destinationDir = jar.destinationDir
		classifier = "sources"
	}

	"wrapper"(Wrapper::class) {
		gradleVersion = "4.4"
	}
}

dependencies {
	compile(kotlin("stdlib", kotlinVersion))
	compile("org.apache.commons:commons-lang3:3.5")

	testCompile("junit:junit:4.12")
	testCompile(kotlin("test-junit", kotlinVersion))
}

artifacts {
	add("archives", tasks["sourceJar"])
}

publishing {
	(publications) {
		"maven"(MavenPublication::class) {
			artifact(tasks["jar"])

			artifact(tasks["sourceJar"]) {
				classifier = "sources"
			}
		}
	}
}

if (rootProject.hasProperty("bintrayUser")) {
	bintray {
		user = rootProject.property("bintrayUser").toString()
		key = rootProject.property("bintrayApiKey").toString()
		setPublications("maven")
		pkg(closureOf<BintrayExtension.PackageConfig> {
			repo = "maven"
			name = "kotlin-xml-builder"
			userOrg = rootProject.property("bintrayUser").toString()
			setLicenses("Apache-2.0")
			vcsUrl = "https://github.com/redundent/kotlin-xml-builder.git"
		})
	}
}
