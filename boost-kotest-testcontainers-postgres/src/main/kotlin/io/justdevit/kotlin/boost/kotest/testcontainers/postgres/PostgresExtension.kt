package io.justdevit.kotlin.boost.kotest.testcontainers.postgres

import io.justdevit.kotlin.boost.kotest.ExternalToolExtension
import io.kotest.core.spec.Spec
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.PostgreSQLContainer.POSTGRESQL_PORT
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

private var postgresContainer: PostgreSQLContainer<*>? = null

private val POSTGRES_EXTENSION_ACTIVATION_ANNOTATIONS: List<String> =
    listOf(
        "io.micronaut.test.extensions.kotest5.annotation.MicronautTest",
    )

/**
 * The `PostgresExtension` class is an implementation of the `ExternalToolExtension` interface that represents an extension for running PostgreSQL containers.
 * It provides functionality to start and stop a PostgreSQL container as needed.
 */
object PostgresExtension : ExternalToolExtension<PostgreSQLContainer<*>, PostgreSQLContainer<*>> {
    override fun <T : Spec> instantiate(clazz: KClass<T>): Spec? {
        if (clazz
                .annotations
                .map { it.annotationClass.jvmName }
                .any { it in POSTGRES_EXTENSION_ACTIVATION_ANNOTATIONS }
        ) {
            startContainer()
        }
        return null
    }

    override fun mount(configure: PostgreSQLContainer<*>.() -> Unit): PostgreSQLContainer<*> {
        startContainer()
        return postgresContainer!!
    }

    override suspend fun afterProject() {
        postgresContainer?.stop()
    }

    private fun startContainer() {
        if (postgresContainer == null) {
            postgresContainer =
                PostgreSQLContainer("postgres:latest").apply {
                    start()
                    waitingFor(HostPortWaitStrategy())
                    System.setProperty("POSTGRES_URL", jdbcUrl)
                    System.setProperty("POSTGRES_HOST", host)
                    System.setProperty("POSTGRES_PORT", getMappedPort(POSTGRESQL_PORT).toString())
                    System.setProperty("POSTGRES_NAME", databaseName)
                    System.setProperty("POSTGRES_USERNAME", username)
                    System.setProperty("POSTGRES_PASSWORD", password)
                }
        }
    }
}
