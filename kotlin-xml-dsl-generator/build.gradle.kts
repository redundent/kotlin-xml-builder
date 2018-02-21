import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.kotlin.dsl.get
import com.jfrog.bintray.gradle.BintrayExtension


plugins {
	kotlin("jvm")
	id("com.github.johnrengelman.shadow") version "2.0.2"
	id("com.jfrog.bintray")
	`maven-publish`
}

val kotlinVersion: String by rootProject.extra

tasks {
	val jar by getting(Jar::class) {
		manifest {
			attributes(mapOf("Main-Class" to "org.redundent.kotlin.xml.gen.DslGeneratorKt"))
		}
	}

	val shadowJar by getting(ShadowJar::class) {
		classifier = null
	}
}

dependencies {
	compileOnly(kotlin("stdlib", kotlinVersion))
	compile(kotlin("reflect", kotlinVersion))
	compile("org.glassfish.jaxb:jaxb-xjc:2.3.0")

	testCompile(project(":kotlin-xml-builder"))
	testCompile("junit:junit:4.12")
	testCompile(kotlin("test-junit", kotlinVersion))
}

publishing {
	(publications) {
		"maven"(MavenPublication::class) {
			artifact(tasks["shadowJar"])
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
			name = "kotlin-xml-dsl-generator"
			userOrg = rootProject.property("bintrayUser").toString()
			setLicenses("Apache-2.0")
			vcsUrl = "https://github.com/redundent/kotlin-xml-builder.git"
		})
	}
}
