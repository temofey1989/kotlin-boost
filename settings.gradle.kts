rootProject.name = "kotlin-boost"

include(
    "boost-domain",
    "boost-kotest",
    "boost-kotest-mockserver",
    "boost-kotest-testcontainers",
    "boost-kotest-testcontainers-keycloak",
    "boost-kotest-testcontainers-localstack",
    "boost-kotest-testcontainers-postgres",
    "boost-kotest-testcontainers-rabbitmq",
    "boost-logging-core",
    "boost-logging-slf4j",
    "boost-serialization-json",
    "boost-utils",
)

includeBuild("boost-bom")
