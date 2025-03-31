dependencies {
    api(project(":boost-commons"))
    api(platform(rootProject.libs.aws.bom))
    api(rootProject.libs.aws.s3)
    api(rootProject.libs.aws.s3.transfer)
    api(rootProject.libs.aws.crt)
    compileOnly(rootProject.libs.kotlin.coroutines.core)

    testImplementation(project(":boost-kotest-testcontainers-localstack"))
}
