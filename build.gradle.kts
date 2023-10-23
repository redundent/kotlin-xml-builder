plugins {
	kotlin("jvm") version "1.6.20" apply false
	id("com.bmuschko.nexus") version "2.3.1" apply false
	id("io.github.gradle-nexus.publish-plugin") version "2.0.0-rc-1"
	id("org.jlleitschuh.gradle.ktlint") version "11.6.1"
}

extra["kotlinVersion"] = "1.6.20"

allprojects {
	group = "org.redundent"
	version = "1.9.1"

	repositories {
		mavenCentral()
	}
}

nexusPublishing {
	repositories {
		sonatype()
	}
}

subprojects {
	afterEvaluate {
		configure<PublishingExtension> {
			publications.withType<MavenPublication> {
				artifact(
					tasks.register("${name}JavadocJar", Jar::class) {
						archiveClassifier.set("javadoc")
						archiveAppendix.set(this@withType.name)
					}
				)

				pom {
					name.set("Kotlin XML Builder")
					description.set("A lightweight type safe builder to build xml documents in Kotlin")
					url.set("https://github.com/redundent/kotlin-xml-builder")

					licenses {
						license {
							name.set("Apache 2.0")
							url.set("https://www.apache.org/licenses/LICENSE-2.0")
						}
					}
					developers {
						developer {
							id.set("redundent")
							name.set("Jason Blackwell")
						}
					}
					scm {
						url.set("https://github.com/redundent/kotlin-xml-builder")
					}
				}
			}
		}

		configure<SigningExtension> {
			if (project.hasProperty("signing.gnupg.keyName")) {
				useGpgCmd()
				sign(extensions.getByType(PublishingExtension::class).publications)
			}
		}
	}
}
