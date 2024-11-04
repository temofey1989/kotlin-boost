val awsSdkVersion: String by project
val awsCrtVersion: String by project
val kotlinCoroutinesVersion: String by project

dependencies {
    api(project(":boost-commons"))

    api(platform("software.amazon.awssdk:bom:$awsSdkVersion"))
    api("software.amazon.awssdk:s3")
    api("software.amazon.awssdk:s3-transfer-manager")
    api("software.amazon.awssdk.crt:aws-crt:$awsCrtVersion")
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")

    testImplementation(project(":boost-kotest-testcontainers-localstack"))
}
