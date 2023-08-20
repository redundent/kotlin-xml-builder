
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.jfrog.bintray.gradle.BintrayExtension


plugins {
	kotlin("jvm")
	id("com.github.johnrengelman.shadow") version "5.2.0"
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
	implementation("org.glassfish.jaxb:jaxb-xjc:2.3.0")
	implementation("com.squareup:kotlinpoet:1.14.2")

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
