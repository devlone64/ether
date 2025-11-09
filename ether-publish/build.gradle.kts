plugins {
    id("maven-publish")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "$group"
            artifactId = rootProject.name.toLowerCase()
            from(components["java"])
        }
    }

    repositories {
        maven {
            url = uri("https://repo.repsy.io/mvn/lone64/everything")
            credentials {
                username = properties["username"] as String
                password = properties["password"] as String
            }
        }
    }
}