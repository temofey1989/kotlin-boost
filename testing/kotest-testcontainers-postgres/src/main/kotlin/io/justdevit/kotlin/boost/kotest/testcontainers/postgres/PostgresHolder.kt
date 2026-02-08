package io.justdevit.kotlin.boost.kotest.testcontainers.postgres

import io.justdevit.kotlin.boost.environment.property
import io.justdevit.kotlin.boost.kotest.testcontainers.ContainerHolder
import org.testcontainers.containers.Network.SHARED
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy
import org.testcontainers.postgresql.PostgreSQLContainer
import org.testcontainers.postgresql.PostgreSQLContainer.POSTGRESQL_PORT

/**
 * An object responsible for managing the lifecycle of a PostgreSQL test container.
 */
object PostgresHolder : ContainerHolder<PostgreSQLContainer>() {

    /**
     * Represents the default Docker image tag used for initializing and running Postgres containers in the testing environment.
     * The value can be overridden by specifying a system property or environment variable named `postgres.image-tag`.
     *
     * If not explicitly defined, this variable defaults to `latest`.
     */
    val imageTag: String = property("postgres.image-tag", "latest")

    override fun initializeTool() =
        PostgreSQLContainer("postgres:$imageTag").apply {
            withNetwork(SHARED)
            withNetworkAliases("postgres")
            start()
            waitingFor(HostPortWaitStrategy())
            System.setProperty("POSTGRES_URL", jdbcUrl)
            System.setProperty("POSTGRES_HOST", host)
            System.setProperty("POSTGRES_PORT", getMappedPort(POSTGRESQL_PORT).toString())
            System.setProperty("POSTGRES_DATABASE", databaseName)
            System.setProperty("POSTGRES_USERNAME", username)
            System.setProperty("POSTGRES_PASSWORD", password)
        }
}
