package io.justdevit.kotlin.boost.kotest.testcontainers.postgres

import io.justdevit.kotlin.boost.extension.runIf
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
class PostgresExtension<A : Annotation>(private val activationAnnotations: Collection<KClass<A>> = emptySet()) : ExternalToolExtension<PostgreSQLContainer<*>, PostgreSQLContainer<*>> {

    companion object {
        val INSTANCE: PostgresExtension<*> by lazy { PostgresExtension<Annotation>() }
    }

    constructor(vararg activationAnnotations: KClass<A>) : this(activationAnnotations.toSet())

    override fun <T : Spec> instantiate(clazz: KClass<T>): Spec? {
        if (clazz
                .annotations
                .any { it.annotationClass in activationAnnotations }
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
