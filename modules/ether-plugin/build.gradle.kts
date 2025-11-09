dependencies {
    implementation(projectAPI)
}

extra.apply {
    set("pluginName", rootProject.name)
}

tasks.processResources {
    filesMatching("*.yml") {
        expand(project.properties)
        expand(extra.properties)
    }
}