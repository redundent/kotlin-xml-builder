plugins {
	id("com.bmuschko.nexus") version "2.3.1" apply false
	id("io.github.gradle-nexus.publish-plugin") version "2.0.0-rc-1"
	//id("org.jlleitschuh.gradle.ktlint") version "12.1.1"
}

nexusPublishing {
	repositories {
		sonatype()
	}
}
