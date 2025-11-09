import org.gradle.api.Project

private fun Project.subproject(name: String) = project(":modules:${rootProject.name.lowercase()}-${name.lowercase()}")

val Project.projectAPI get() = subproject("api")
val Project.projectPlugin get() = subproject("plugin")