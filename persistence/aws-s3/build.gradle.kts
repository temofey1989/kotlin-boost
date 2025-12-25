dependencies {
    api(platform(libs.aws.bom))
    api(libs.aws.s3)
    api(libs.aws.s3.transfer)
    api(libs.aws.crt)
    compileOnly(libs.kotlin.coroutines.core)

    testImplementation(projects.testing.kotestTestcontainersLocalstack)
}
