@file:Suppress("GradlePackageVersionRange")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")

    id("org.jlleitschuh.gradle.ktlint")

    id("fabric-loom")
}

repositories {
    maven("https://maven.fabricmc.net/") { name = "Fabric" }
}

internal val minecraftVersion: String by project
internal val yarnMappings: String by project
internal val loaderVersion: String by project
internal val fabricVersion: String by project
internal val fabricKotlinVersion: String by project

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings("net.fabricmc:yarn:$yarnMappings")
    modImplementation("net.fabricmc:fabric-loader:$loaderVersion")
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricVersion")
    modImplementation("net.fabricmc:fabric-language-kotlin:$fabricKotlinVersion")
}

internal val projectGroup: String by project
internal val projectVersion: String by project

group = projectGroup
version = projectVersion

internal val javaVersion: String by project

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

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(javaVersion.toInt())
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = javaVersion
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    jar {
        from("LICENSE") { rename { "$it.${project.name}" } }
    }

    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand(
                mutableMapOf(
                    "version" to project.version,
                    "javaVersion" to javaVersion,
                    "minecraftVersion" to minecraftVersion,
                    "loaderVersion" to loaderVersion,
                    "fabricKotlinVersion" to fabricKotlinVersion,
                ),
            )
        }
    }
}
