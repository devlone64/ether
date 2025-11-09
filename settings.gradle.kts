pluginManagement {
    plugins {
        id("com.github.johnrengelman.shadow").version("7.1.2")
    }

    repositories {
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
}

val prefix = rootProject.name.lowercase()
include("$prefix-publish")

include("modules:$prefix-api")
include("modules:$prefix-plugin")