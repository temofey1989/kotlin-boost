rootProject.name = "kotlin-boost"

include(
    "boost-aws-s3",
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
    "boost-model",
    "boost-serialization-json",
    "boost-utils",
)

includeBuild("boost-bom")
