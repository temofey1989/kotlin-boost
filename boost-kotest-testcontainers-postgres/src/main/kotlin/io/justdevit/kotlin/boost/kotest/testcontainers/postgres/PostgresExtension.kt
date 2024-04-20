package io.justdevit.kotlin.boost.kotest.testcontainers.postgres

import io.justdevit.kotlin.boost.extension.runIf
import io.justdevit.kotlin.boost.kotest.ANY_EXTENSION_FILTERS
import io.justdevit.kotlin.boost.kotest.AnnotationExtensionFilter
import io.justdevit.kotlin.boost.kotest.ExtensionFilter
import io.justdevit.kotlin.boost.kotest.ExternalToolExtension
import io.kotest.core.spec.Spec
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.PostgreSQLContainer.POSTGRESQL_PORT
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.reflect.KClass

private var postgresContainer: PostgreSQLContainer<*>? = null
private val LOCK: Lock = ReentrantLock()

/**
 * The `PostgresExtension` class is an implementation of the `ExternalToolExtension` interface that represents an extension for running PostgreSQL containers.
 * It provides functionality to start and stop a PostgreSQL container as needed.
 */
class PostgresExtension(private val filters: Collection<ExtensionFilter> = ANY_EXTENSION_FILTERS) : ExternalToolExtension<PostgreSQLContainer<*>, PostgreSQLContainer<*>> {

    constructor(vararg filters: ExtensionFilter) : this(filters.toSet())

    override fun <T : Spec> instantiate(clazz: KClass<T>): Spec? {
        if (filters.any { it.decide(clazz) }) {
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
        LOCK.runIf({ postgresContainer == null }) {
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

/**
 * Creates a [PostgresExtension] with the specified predicates for annotation [A].
 *
 * @param predicates The predicates used to filter the annotations.
 * @return The [PostgresExtension] object.
 */
inline fun <reified A : Annotation> PostgresExtension(vararg predicates: (A) -> Boolean): PostgresExtension {
    val filters = when {
        predicates.isEmpty() -> ANY_EXTENSION_FILTERS
        else -> predicates.map { AnnotationExtensionFilter(A::class, it) }
    }
    return PostgresExtension(filters)
}
