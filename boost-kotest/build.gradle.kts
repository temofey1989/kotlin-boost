val kotestVersion: String by project
val restAssureVersion: String by project

dependencies {
    api("io.kotest:kotest-runner-junit5:$kotestVersion")
    api("io.kotest:kotest-assertions-core:$kotestVersion")
    api("io.kotest:kotest-property:$kotestVersion")
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    api("io.rest-assured:kotlin-extensions:$restAssureVersion")
}
