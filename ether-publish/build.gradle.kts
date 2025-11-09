plugins {
    signing
    id("maven-publish")
}

publishing {
    repositories {
        maven {
            url = uri("https://repo.repsy.io/mvn/lone64/everything")
            credentials {
                username = properties["username"] as String
                password = properties["password"] as String
            }
        }
    }

    publications {
        fun MavenPublication.createPublication(project: Project) {
            groupId = "$group"
            artifactId = rootProject.name.toLowerCase()
            from(components["java"])
        }

        create<MavenPublication>("api") {
            createPublication(projectAPI)
        }
    }
}

signing {
    isRequired = true
    sign(publishing.publications["api"])
}