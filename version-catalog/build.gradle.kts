import org.gradle.api.internal.catalog.parser.TomlCatalogFileParser

plugins {
    alias(libs.plugins.version.catalog)
}

version = rootProject.version.toString()

catalog {
    versionCatalog {
        version("kotlin-boost", rootProject.version.toString())
        rootProject.subprojects
            .filter { it.subprojects.isEmpty() }
            .filter { it.name != "version-catalog" }
            .forEach {
                library("boost-${it.name}", "${it.group}:${it.name}:${it.version}")
            }
        @Suppress("UnstableApiUsage")
        TomlCatalogFileParser.parse(File("${project.projectDir.path}/libs.versions.toml").toPath(), this) { null }
    }
}

tasks {
    named("generateCatalogAsToml") {
        doLast {
            val toml = layout
                .buildDirectory
                .file("version-catalog/libs.versions.toml")
                .get()
                .asFile
            toml
                .readLines()
                .map { it.replace(", version = \"\"", "") }
                .also {
                    toml.writeText(it.joinToString(separator = System.lineSeparator()))
                }
        }
    }
    register("clean", DefaultTask::class) {
        group = "build"
        doLast {
            layout.buildDirectory
                .get()
                .asFile
                .deleteRecursively()
        }
    }
}

rootProject.tasks.named("build") {
    dependsOn(tasks.named("generateCatalogAsToml"))
}
rootProject.tasks.named("clean") {
    dependsOn(tasks.named("clean"))
}
