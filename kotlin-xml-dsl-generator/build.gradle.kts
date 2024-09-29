import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
	conventions.publishing
	conventions.`kotlin-jvm`
	id("com.gradleup.shadow") version "8.3.2"
	id("org.jlleitschuh.gradle.ktlint")
}

tasks {
	jar {
		manifest {
			attributes(mapOf("Main-Class" to "org.redundent.kotlin.xml.gen.DslGeneratorKt"))
		}
	}

	withType<ShadowJar> {
		archiveClassifier.set(null as String?)
	}
}

dependencies {
	implementation(kotlin("reflect"))
	implementation("org.glassfish.jaxb:jaxb-xjc:2.3.8")

	testImplementation(projects.kotlinXmlBuilder)
	testImplementation(kotlin("test"))
}

publishing {
	publications {
		register<MavenPublication>("maven") {
			artifact(tasks.shadowJar)
			artifact(tasks.sourcesJar)
		}
	}
}
