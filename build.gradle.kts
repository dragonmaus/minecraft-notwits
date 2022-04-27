@file:Suppress("GradlePackageVersionRange")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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

internal val projectGroup = project.property("project.group")
internal val projectVersion = project.property("project.version")

group = projectGroup as String
version = projectVersion as String

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

internal val projectId = project.property("project.id")
internal val projectName = project.property("project.name")

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
        from("LICENSE") { rename { "$it.$projectId" } }
    }

    processResources {
        inputs.property("version", projectVersion)
        filesMatching("fabric.mod.json") {
            expand(
                "group" to projectGroup,
                "id" to projectId,
                "version" to projectVersion,

                "name" to projectName,
                "description" to project.property("project.description"),
                "source" to project.property("project.source_uri"),
                "author" to project.property("project.author"),
                "license" to project.property("project.license"),

                "java" to project.property("java.version"),
                "minecraft" to minecraftVersion,
                "fabric" to fabricVersion,
                "fabric_loader" to fabricLoaderVersion,
                "fabric_kotlin" to fabricKotlinVersion,
            )
        }
    }
}

blossom {
    replaceToken("$[id]", projectId)
    replaceToken("$[version]", projectVersion)

    replaceToken("$[name]", projectName)
}
