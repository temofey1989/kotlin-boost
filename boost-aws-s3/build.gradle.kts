dependencies {
    api(project(":boost-commons"))

    with(rootProject) {
        api(platform(libs.aws.bom))
        api(libs.aws.s3)
        api(libs.aws.s3.transfer)
        api(libs.aws.crt)
        compileOnly(libs.kotlin.coroutines.core)
    }

    testImplementation(project(":boost-kotest-testcontainers-localstack"))
}
