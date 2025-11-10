import java.util.Locale

plugins {
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
            artifactId = project.name.lowercase(Locale.getDefault())
            from(project.components["java"])

            pom {
                name.set(project.name.lowercase(Locale.getDefault()))
                description.set("SpigotMC extension library written in Java")
                url.set("https://github.com/devlone64/${rootProject.name}")

                licenses {
                    license {
                        name.set("GNU General Public License version 3")
                        url.set("https://opensource.org/licenses/GPL-3.0")
                    }
                }

                developers {
                    developer {
                        id.set("lone64")
                        name.set("Lone64")
                        url.set("https://github.com/devlone64")
                        roles.addAll("developer")
                        timezone.set("Asia/Seoul")
                    }
                }

                scm {
                    url.set("https://github.com/devlone64/${rootProject.name}")
                    connection.set("scm:git:git://github.com/devlone64/${rootProject.name}.git")
                    developerConnection.set("scm:git:ssh://github.com:devlone64/${rootProject.name}.git")
                }
            }
        }

        create<MavenPublication>("api") {
            createPublication(projectAPI)
        }
    }
}