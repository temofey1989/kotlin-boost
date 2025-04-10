rootProject.name = "kotlin-boost"

include(
    "boost-aws-s3",
    "boost-commons",
    "boost-domain",
    "boost-eventbus",
    "boost-kotest",
    "boost-kotest-mockk",
    "boost-kotest-mockserver",
    "boost-kotest-testcontainers",
    "boost-kotest-testcontainers-keycloak",
    "boost-kotest-testcontainers-localstack",
    "boost-kotest-testcontainers-postgres",
    "boost-kotest-testcontainers-rabbitmq",
    "boost-logging-core",
    "boost-logging-slf4j",
    "boost-rest-assured",
    "boost-serialization-json",
)

includeBuild("boost-bom")
