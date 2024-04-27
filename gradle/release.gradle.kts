buildscript {
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
    dependencies {
        classpath("io.github.gradle-nexus:publish-plugin:2.0.0-rc-2")
    }
}

// --- PROPERTIES --------------------------------------------------------- #

fun prop(name: String, defaultValue: String = ""): String {
    val envName = name.replace(".", "_").uppercase()
    return System.getenv(envName) ?: project.findProperty(name)?.toString() ?: defaultValue
}

val projectDescription: String by lazy { prop("project.description", project.description ?: "") }

val scmUrl: String by lazy { prop("scm.url") }

val scmConnection: String by lazy { prop("scm.connection") }

val scmDeveloperConnection: String by lazy { prop("scm.developer.connection") }

val licenseName: String by lazy { prop("license.name") }

val licenseUrl: String by lazy { prop("license.url") }

val developerId: String by lazy { prop("developer.id") }

val developerName: String by lazy { prop("developer.name") }

val developerEmail: String by lazy { prop("developer.email") }

// ------------------------------------------------------------------------ #

apply(plugin = "org.gradle.java-library")
apply(plugin = "org.gradle.maven-publish")
apply(plugin = "org.gradle.signing")

configure<JavaPluginExtension> {
    withSourcesJar()
    withJavadocJar()
}

configure<PublishingExtension> {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            pom {
                name.set("${project.group}:${project.name}")
                description.set(projectDescription)
                url.set(scmUrl)
                licenses {
                    license {
                        name.set(licenseName)
                        url.set(licenseUrl)
                    }
                }
                developers {
                    developer {
                        id.set(developerId)
                        name.set(developerName)
                        email.set(developerEmail)
                    }
                }
                scm {
                    url.set(scmUrl)
                    connection.set(scmConnection)
                    developerConnection.set(scmDeveloperConnection)
                }
            }
            suppressPomMetadataWarningsFor("runtimeElements")
        }
    }
}

configure<SigningExtension> {
    val signingKeyId: String? by project
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)

    val publishing = project.extensions["publishing"] as PublishingExtension
    sign(publishing.publications["maven"])
}
