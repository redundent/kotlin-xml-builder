plugins {
	id("org.jlleitschuh.gradle.ktlint")
	conventions.publishing
	conventions.`kotlin-jvm`
}

dependencies {
	compileOnly(kotlin("reflect"))
	implementation("org.apache.commons:commons-lang3:3.5")

	testImplementation("junit:junit:4.13.1")
	testImplementation(kotlin("reflect"))
	testImplementation(kotlin("test-junit"))
}

publishing {
	publications {
		register<MavenPublication>("maven") {
			from(components["java"])
		}
	}
}
