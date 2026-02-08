package io.justdevit.kotlin.boost.kotest.testcontainters.rabbitmq

import io.justdevit.kotlin.boost.environment.property
import io.justdevit.kotlin.boost.kotest.testcontainers.ContainerHolder
import org.testcontainers.containers.Network.SHARED
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy
import org.testcontainers.rabbitmq.RabbitMQContainer

/**
 * Represents a container holder for managing the lifecycle of a RabbitMQ container.
 */
object RabbitMqHolder : ContainerHolder<RabbitMQContainer>() {

    /**
     * Represents the default Docker image tag used for initializing and running RabbitMq containers in the testing environment.
     * The value can be overridden by specifying a system property or environment variable named `rabbitmq.image-tag`.
     *
     * If not explicitly defined, this variable defaults to `4-management-alpine`.
     */
    val imageTag = property("rabbitmq.image-tag", "4-management-alpine")

    override fun initializeTool() =
        RabbitMQContainer("rabbitmq:$imageTag").apply {
            withNetwork(SHARED)
            withNetworkAliases("rabbitmq")
            start()
            waitingFor(HostPortWaitStrategy())
            System.setProperty("RABBITMQ_HOST", host)
            System.setProperty("RABBITMQ_PORT", amqpPort.toString())
            System.setProperty("RABBITMQ_USER", adminUsername)
            System.setProperty("RABBITMQ_PASSWORD", adminPassword)
        }
}
