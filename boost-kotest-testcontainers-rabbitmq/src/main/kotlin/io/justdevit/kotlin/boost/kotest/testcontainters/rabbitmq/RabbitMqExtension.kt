package io.justdevit.kotlin.boost.kotest.testcontainters.rabbitmq

import io.justdevit.kotlin.boost.extension.runIf
import io.justdevit.kotlin.boost.kotest.ANY_EXTENSION_FILTERS
import io.justdevit.kotlin.boost.kotest.AnnotationExtensionFilter
import io.justdevit.kotlin.boost.kotest.ExtensionFilter
import io.justdevit.kotlin.boost.kotest.ExternalToolExtension
import io.kotest.core.spec.Spec
import org.testcontainers.containers.RabbitMQContainer
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.reflect.KClass

private var rabbitmqContainer: RabbitMQContainer? = null
private val LOCK: Lock = ReentrantLock()

/**
 * The `RabbitMqExtension` class is an implementation of the `ExternalToolExtension` interface that represents an extension for running RabbitMQ containers.
 * It provides functionality to start and stop a RabbitMQ container as needed.
 */
class RabbitMqExtension(private val filters: Collection<ExtensionFilter> = ANY_EXTENSION_FILTERS) : ExternalToolExtension<RabbitMQContainer, RabbitMQContainer> {

    constructor(vararg filters: ExtensionFilter) : this(filters.toSet())

    override fun <T : Spec> instantiate(clazz: KClass<T>): Spec? {
        if (filters.any { it.decide(clazz) }) {
            startContainer()
        }
        return null
    }

    override fun mount(configure: RabbitMQContainer.() -> Unit): RabbitMQContainer {
        startContainer()
        return rabbitmqContainer!!
    }

    override suspend fun afterProject() {
        rabbitmqContainer?.stop()
    }

    private fun startContainer() {
        LOCK.runIf({ rabbitmqContainer == null }) {
            rabbitmqContainer =
                RabbitMQContainer("rabbitmq:3-management").apply {
                    start()
                    waitingFor(HostPortWaitStrategy())
                    System.setProperty("RABBITMQ_HOST", host)
                    System.setProperty("RABBITMQ_PORT", amqpPort.toString())
                    System.setProperty("RABBITMQ_USER", adminUsername)
                    System.setProperty("RABBITMQ_PASSWORD", adminPassword)
                }
        }
    }
}

/**
 * Creates a [RabbitMqExtension] with the specified predicates for annotation [A].
 *
 * @param predicates The predicates used to filter the annotations.
 * @return The [RabbitMqExtension] object.
 */
inline fun <reified A : Annotation> RabbitMqExtension(vararg predicates: (A) -> Boolean): RabbitMqExtension {
    val filters = when {
        predicates.isEmpty() -> ANY_EXTENSION_FILTERS
        else -> predicates.map { AnnotationExtensionFilter(A::class, it) }
    }
    return RabbitMqExtension(filters)
}

/**
 * Creates a [RabbitMqExtension] with the specified predicate for annotation [A].
 *
 * @param predicate The predicate used to filter the annotations.
 * @return The [RabbitMqExtension] object.
 */
inline fun <reified A : Annotation> RabbitMqExtension(noinline predicate: (A) -> Boolean) = RabbitMqExtension<A>(*arrayOf(predicate))
