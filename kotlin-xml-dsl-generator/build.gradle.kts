import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
	kotlin("jvm")
	id("com.github.johnrengelman.shadow") version "5.2.0"
	`maven-publish`
	signing
	id("org.jlleitschuh.gradle.ktlint")
}

val kotlinVersion: String by rootProject.extra

tasks {
	val jar by getting(Jar::class) {
		manifest {
			attributes(mapOf("Main-Class" to "org.redundent.kotlin.xml.gen.DslGeneratorKt"))
		}
	}

	withType<ShadowJar> {
		archiveClassifier.set(null as String?)
	}

	register<Jar>("sourceJar") {
		from(sourceSets["main"].allSource)
		destinationDirectory.set(jar.destinationDirectory)
		archiveClassifier.set("sources")
	}
}

dependencies {
	implementation(kotlin("stdlib", kotlinVersion))
	implementation(kotlin("reflect", kotlinVersion))
	implementation("org.glassfish.jaxb:jaxb-xjc:2.3.8")

	testImplementation(project(":kotlin-xml-builder"))
	testImplementation("junit:junit:4.13.1")
	testImplementation(kotlin("test-junit", kotlinVersion))
}

publishing {
	publications {
		register<MavenPublication>("maven") {
			artifact(tasks["shadowJar"])

			artifact(tasks["sourceJar"]) {
				classifier = "sources"
			}
		}
	}
}
