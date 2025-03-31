dependencies {
    api(project(":boost-commons"))
    api(rootProject.libs.ksuid)
    compileOnly(rootProject.libs.kotlin.coroutines.core)
    compileOnly(rootProject.libs.kotlin.coroutines.slf4j)
}
