rootProject.name = "kotlin-boost"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    ":commons",
    ":domain",
    ":integration:event-bus",
    ":observability:logging-core",
    ":observability:logging-slf4j",
    ":persistence:aws-s3",
    ":serialization:serialization-json",
    ":testing:kotest",
    ":testing:kotest-mockk",
    ":testing:kotest-mockserver",
    ":testing:kotest-testcontainers",
    ":testing:kotest-testcontainers-keycloak",
    ":testing:kotest-testcontainers-localstack",
    ":testing:kotest-testcontainers-postgres",
    ":testing:kotest-testcontainers-rabbitmq",
    ":testing:rest-assured",
    "version-catalog",
)
