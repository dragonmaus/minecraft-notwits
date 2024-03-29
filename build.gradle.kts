@file:Suppress("GradlePackageVersionRange")

plugins {
	kotlin("jvm")

	id("net.kyori.blossom")
	id("org.jlleitschuh.gradle.ktlint")

	id("fabric-loom")
}

repositories {
	maven("https://maven.fabricmc.net/") { name = "Fabric" }
}

internal val minecraftVersion = project.property("minecraft.version")
internal val fabricVersion = project.property("fabric.version")
internal val fabricLoaderVersion = project.property("fabric.loader.version")
internal val fabricKotlinVersion = project.property("fabric.kotlin.version")

dependencies {
	minecraft("com.mojang:minecraft:$minecraftVersion")
	mappings("net.fabricmc:yarn:${project.property("yarn.mappings.version")}")
	modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")
	modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricVersion")
	modImplementation("net.fabricmc:fabric-language-kotlin:$fabricKotlinVersion")
}

group = project.property("project.group") as String
version = project.property("project.version") as String

description = project.property("project.description") as String

internal val javaVersion = project.property("java.version") as String
java {
	val javaVersion = JavaVersion.toVersion(javaVersion)

	sourceCompatibility = javaVersion
	targetCompatibility = javaVersion
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(javaVersion.toString().toInt()))
		sourceCompatibility = javaVersion
		targetCompatibility = javaVersion
	}
	withSourcesJar()
}

tasks.compileJava {
	options.encoding = "UTF-8"
	options.release.set(javaVersion.toInt())
	sourceCompatibility = javaVersion
	targetCompatibility = javaVersion
}

tasks.compileKotlin {
	kotlinOptions.jvmTarget = javaVersion
}

tasks.jar {
	from("LICENSE") { rename { "$it.${project.name}" } }
}

tasks.processResources {
	inputs.property("version", project.version)
	filesMatching("fabric.mod.json") {
		expand(
			"group" to project.group,
			"name" to project.name,
			"version" to project.version,

			"displayName" to project.displayName,
			"description" to project.description,
			"source" to project.property("project.source_uri"),
			"author" to project.property("project.author"),
			"license" to project.property("project.license"),

			"java" to project.property("java.version"),
			"minecraft" to minecraftVersion,
			"fabric" to fabricVersion,
			"fabric_loader" to fabricLoaderVersion,
			"fabric_kotlin" to fabricKotlinVersion
		)
	}
}

blossom {
	replaceToken("$[name]", project.name)
	replaceToken("$[version]", project.version)

	replaceToken("$[displayName]", project.displayName)
}
