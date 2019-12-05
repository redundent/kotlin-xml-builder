import com.jfrog.bintray.gradle.BintrayExtension

plugins {
	kotlin("jvm")
	id("com.jfrog.bintray")
	`maven-publish`
}

val kotlinVersion: String by rootProject.extra

tasks {
	val jar by getting(Jar::class)

	register<Jar>("sourceJar") {
		from(sourceSets["main"].allSource)
		destinationDir = jar.destinationDir
		classifier = "sources"
	}
}

dependencies {
	compileOnly(kotlin("stdlib", kotlinVersion))
	compile(kotlin("reflect", kotlinVersion))
	compile("org.apache.commons:commons-lang3:3.5")

	testCompile("junit:junit:4.12")
	testCompile(kotlin("test-junit", kotlinVersion))
}

artifacts {
	add("archives", tasks["sourceJar"])
}

publishing {
	publications {
		register<MavenPublication>("maven") {
			from(components["java"])

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
