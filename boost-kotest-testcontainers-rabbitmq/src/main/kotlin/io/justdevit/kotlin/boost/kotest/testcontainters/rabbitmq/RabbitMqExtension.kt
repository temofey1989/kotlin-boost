package io.justdevit.kotlin.boost.kotest.testcontainters.rabbitmq

import io.justdevit.kotlin.boost.kotest.ExternalToolExtension
import io.kotest.core.spec.Spec
import org.testcontainers.containers.RabbitMQContainer
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

private var rabbitmqContainer: RabbitMQContainer? = null

private val RABBITMQ_EXTENSION_ACTIVATION_ANNOTATIONS: List<String> =
    listOf(
        "io.micronaut.test.extensions.kotest5.annotation.MicronautTest",
    )

/**
 * The `RabbitMqExtension` class is an implementation of the `ExternalToolExtension` interface that represents an extension for running RabbitMQ containers.
 * It provides functionality to start and stop a RabbitMQ container as needed.
 */
object RabbitMqExtension :
    ExternalToolExtension<RabbitMQContainer, RabbitMQContainer> {
    override fun <T : Spec> instantiate(clazz: KClass<T>): Spec? {
        if (clazz
                .annotations
                .map { it.annotationClass.jvmName }
                .any { it in RABBITMQ_EXTENSION_ACTIVATION_ANNOTATIONS }
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
        if (rabbitmqContainer == null) {
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
