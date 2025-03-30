package io.justdevit.kotlin.boost.kotest.testcontainers.postgres

import io.justdevit.kotlin.boost.environment.property
import io.justdevit.kotlin.boost.kotest.testcontainers.ContainerHolder
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy

/**
 * An object responsible for managing the lifecycle of a PostgreSQL test container.
 */
object PostgresHolder : ContainerHolder<PostgreSQLContainer<*>>() {

    /**
     * Represents the Docker image tag used for the PostgreSQL container.
     *
     * The value is retrieved from a property with the name `POSTGRES_IMAGE_TAG`. If the property is not found,
     * the default value `latest` is used. This allows for flexibility in specifying the image version
     * for the PostgreSQL container during the runtime of tests or applications interacting with the container.
     */
    val imageTag: String = property("postgres.image-tag", "latest")

    override fun initializeTool() =
        PostgreSQLContainer("postgres:$imageTag").apply {
            start()
            waitingFor(HostPortWaitStrategy())
            System.setProperty("POSTGRES_URL", jdbcUrl)
            System.setProperty("POSTGRES_HOST", host)
            System.setProperty("POSTGRES_PORT", getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT).toString())
            System.setProperty("POSTGRES_NAME", databaseName)
            System.setProperty("POSTGRES_USERNAME", username)
            System.setProperty("POSTGRES_PASSWORD", password)
        }
}
