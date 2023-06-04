rootProject.name = settings.extra["project.name"] as String

pluginManagement {
	repositories {
		maven("https://maven.fabricmc.net/") { name = "Fabric" }
		mavenCentral()
		gradlePluginPortal()
	}

	plugins {
		kotlin("jvm") version settings.extra["kotlin.version"] as String

		id("net.kyori.blossom") version settings.extra["blossom.version"] as String
		id("org.jlleitschuh.gradle.ktlint") version settings.extra["ktlint.version"] as String

		id("fabric-loom") version settings.extra["loom.version"] as String
	}
}
