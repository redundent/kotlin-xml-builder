plugins {
//	id("org.jlleitschuh.gradle.ktlint")
	conventions.publishing
	conventions.`kotlin-jvm`
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
