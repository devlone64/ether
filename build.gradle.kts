@file:Suppress("SpellCheckingInspection")

plugins {
    idea
    id("java")
    id("java-library")
    id("com.github.johnrengelman.shadow")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(Dependency.Java.Version))
    }
}

allprojects {
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://libraries.minecraft.net/")
        maven("https://repo.codemc.io/repository/maven-snapshots/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "com.github.johnrengelman.shadow")

    dependencies {
        compileOnly("org.jetbrains:annotations:${Dependency.Annotations.Version}")
        compileOnly("org.spigotmc:spigot-api:${Dependency.Spigot.Version}-R0.1-SNAPSHOT")

        /* ------------ Lombok ------------ */
        compileOnly("org.projectlombok:lombok:${Dependency.Lombok.Version}")
        annotationProcessor("org.projectlombok:lombok:${Dependency.Lombok.Version}")
        /* ------------ Lombok ------------ */
    }

    tasks {
        test {
            useJUnitPlatform()
        }
    }
}