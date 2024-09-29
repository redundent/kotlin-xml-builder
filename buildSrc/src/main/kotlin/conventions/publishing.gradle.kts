package conventions

plugins {
	id("conventions.base")
	`maven-publish`
	signing
}

val stubJavadocJar by tasks.registering(Jar::class) {
	archiveClassifier = "javadoc"
}

publishing {
	publications.withType<MavenPublication> {
		artifact(stubJavadocJar)

		pom {
			name = "Kotlin XML Builder"
			description = "A lightweight type safe builder to build xml documents in Kotlin"
			url = "https://github.com/redundent/kotlin-xml-builder"

			licenses {
				license {
					name = "Apache 2.0"
					url = "https://www.apache.org/licenses/LICENSE-2.0"
				}
			}
			developers {
				developer {
					id = "redundent"
					name = "Jason Blackwell"
				}
			}
			scm {
				url = "https://github.com/redundent/kotlin-xml-builder"
			}
		}
	}

	repositories {
		// Publish to a project-local Maven directory, for verification.
		// To test, run:
		// ./gradlew publishAllPublicationsToMavenDevRepository
		// and check $rootDir/build/maven-dev
		maven(rootProject.layout.buildDirectory.dir("maven-dev")) {
			name = "MavenDev"
		}
	}
}

signing {
	if (project.hasProperty("signing.gnupg.keyName")) {
		useGpgCmd()
		sign(publishing.publications)
	}
}

//region Fix Gradle warning about signing tasks using publishing task outputs without explicit dependencies
// https://youtrack.jetbrains.com/issue/KT-46466
val signingTasks = tasks.withType<Sign>()

tasks.withType<AbstractPublishToMaven>().configureEach {
	mustRunAfter(signingTasks)
}
//endregion

//region Maven Publish limiter
// Maven Central can't handle parallel uploads, which can break releases in ugly ways. So, limit parallel publications with a build service.
abstract class MavenPublishLimiter : BuildService<BuildServiceParameters.None>

val mavenPublishLimiter =
	gradle.sharedServices.registerIfAbsent("mavenPublishLimiter", MavenPublishLimiter::class) {
		maxParallelUsages = 1
	}

tasks.withType<PublishToMavenRepository>().configureEach {
	usesService(mavenPublishLimiter)
}
//endregion
