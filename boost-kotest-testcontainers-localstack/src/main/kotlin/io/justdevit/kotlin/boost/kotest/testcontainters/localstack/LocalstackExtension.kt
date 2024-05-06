package io.justdevit.kotlin.boost.kotest.testcontainters.localstack

import io.justdevit.kotlin.boost.extension.runIf
import io.justdevit.kotlin.boost.kotest.ANY_EXTENSION_FILTERS
import io.justdevit.kotlin.boost.kotest.AnnotationExtensionFilter
import io.justdevit.kotlin.boost.kotest.ExtensionFilter
import io.justdevit.kotlin.boost.kotest.ExternalToolExtension
import io.kotest.core.spec.Spec
import org.testcontainers.containers.BindMode.READ_WRITE
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.containers.localstack.LocalStackContainer.Service
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.utility.DockerImageName
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.reflect.KClass

private var localstackContainer: LocalStackContainer? = null
private val LOCK: Lock = ReentrantLock()

/**
 * The `RabbitMqExtension` class is an implementation of the `ExternalToolExtension` interface that represents an extension for running RabbitMQ containers.
 * It provides functionality to start and stop a RabbitMQ container as needed.
 */
class LocalstackExtension(private val services: Collection<Service> = emptyList(), private val filters: Collection<ExtensionFilter> = ANY_EXTENSION_FILTERS) : ExternalToolExtension<LocalStackContainer, LocalStackContainer> {

    constructor(services: Collection<Service> = emptyList(), vararg filters: ExtensionFilter) : this(services, filters.toSet())

    override fun <T : Spec> instantiate(clazz: KClass<T>): Spec? {
        if (filters.any { it.decide(clazz) }) {
            startContainer()
        }
        return null
    }

    override fun mount(configure: LocalStackContainer.() -> Unit): LocalStackContainer {
        startContainer()
        return localstackContainer!!
    }

    override suspend fun afterProject() {
        localstackContainer?.stop()
    }

    private fun startContainer() {
        LOCK.runIf({ localstackContainer == null }) {
            localstackContainer =
                LocalStackContainer(DockerImageName.parse("localstack/localstack:1.4.0")).apply {
                    start()
                    withEnv("DEBUG", "1")
                    withClasspathResourceMapping("/localstack", "/etc/localstack/init/ready.d", READ_WRITE)
                    withServices(*services.toTypedArray())
                    waitingFor(Wait.forLogMessage(".*Local Stack has been initialized\\.\n", 1))

                    services.forEach {
                        System.setProperty("AWS_${it.name}_ENDPOINT", getEndpointOverride(it).toString())
                        System.setProperty("AWS_${it.name}_REGION", region.toString())
                        System.setProperty("AWS_${it.name}_ACCESS_KEY", accessKey)
                        System.setProperty("AWS_${it.name}_SECRET_KEY", secretKey)
                    }
                }
        }
    }
}

/**
 * Creates a [LocalstackExtension] with the specified predicates for annotation [A].
 *
 * @param predicates The predicates used to filter the annotations.
 * @return The [LocalstackExtension] object.
 */
inline fun <reified A : Annotation> LocalstackExtension(services: Collection<Service> = emptyList(), vararg predicates: (A) -> Boolean): LocalstackExtension {
    val filters = when {
        predicates.isEmpty() -> ANY_EXTENSION_FILTERS
        else -> predicates.map { AnnotationExtensionFilter(A::class, it) }
    }
    return LocalstackExtension(services, filters)
}

/**
 * Creates a [LocalstackExtension] with the specified predicate for annotation [A].
 *
 * @param predicate The predicate used to filter the annotations.
 * @return The [LocalstackExtension] object.
 */
inline fun <reified A : Annotation> LocalstackExtension(services: Collection<Service> = emptyList(), noinline predicate: (A) -> Boolean) = LocalstackExtension<A>(services, *arrayOf(predicate))
