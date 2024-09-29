rootProject.name = "kotlin-xml-builder-project"

pluginManagement {
	repositories {
		mavenCentral()
		gradlePluginPortal()
	}
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)

	repositories {
		mavenCentral()

		// Declare the Node.js & Yarn download repositories
		ivy("https://nodejs.org/dist/") {
			name = "Node Distributions at $url"
			patternLayout { artifact("v[revision]/[artifact](-v[revision]-[classifier]).[ext]") }
			metadataSources { artifact() }
			content { includeModule("org.nodejs", "node") }
		}

		ivy("https://github.com/yarnpkg/yarn/releases/download") {
			name = "Yarn Distributions at $url"
			patternLayout { artifact("v[revision]/[artifact](-v[revision]).[ext]") }
			metadataSources { artifact() }
			content { includeModule("com.yarnpkg", "yarn") }
		}

		//region workaround for https://youtrack.jetbrains.com/issue/KT-51379
		// Remove when Kotlin is updated to 2+
		ivy("https://download.jetbrains.com/kotlin/native/builds") {
			name = "Kotlin Native"
			patternLayout {
				listOf(
					"macos-x86_64",
					"macos-aarch64",
					"osx-x86_64",
					"osx-aarch64",
					"linux-x86_64",
					"windows-x86_64",
				).forEach { os ->
					listOf("dev", "releases").forEach { stage ->
						artifact("$stage/[revision]/$os/[artifact]-[revision].[ext]")
					}
				}
			}
			metadataSources { artifact() }
			content {
				includeModuleByRegex(".*", ".*kotlin-native-prebuilt.*")
			}
		}
		//endregion
	}
}

include(":kotlin-xml-dsl-generator")
include(":kotlin-xml-builder")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
