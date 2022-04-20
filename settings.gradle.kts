internal val projectName: String by settings
rootProject.name = projectName

pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") { name = "Fabric" }
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        val kotlinVersion: String by settings
        kotlin("jvm") version kotlinVersion

        val ktlintVersion: String by settings
        id("org.jlleitschuh.gradle.ktlint") version ktlintVersion

        val loomVersion: String by settings
        id("fabric-loom") version loomVersion
    }
}
