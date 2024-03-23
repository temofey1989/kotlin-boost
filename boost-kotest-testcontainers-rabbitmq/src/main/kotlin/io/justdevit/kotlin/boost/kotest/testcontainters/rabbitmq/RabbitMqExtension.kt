package io.justdevit.kotlin.boost.kotest.testcontainters.rabbitmq

import io.justdevit.kotlin.boost.extension.runIf
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
class RabbitMqExtension<A : Annotation>(private val activationAnnotations: Collection<KClass<A>> = emptySet()) : ExternalToolExtension<RabbitMQContainer, RabbitMQContainer> {

    companion object {
        val INSTANCE: RabbitMqExtension<*> by lazy { RabbitMqExtension<Annotation>() }
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
