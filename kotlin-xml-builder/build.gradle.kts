plugins {
	conventions.`kotlin-jvm`
	conventions.publishing
	id("org.jlleitschuh.gradle.ktlint")
}

dependencies {
	compileOnly(kotlin("reflect"))

	testImplementation(kotlin("test"))
}

publishing {
	publications {
		register<MavenPublication>("maven") {
			from(components["java"])
		}
	}
}
