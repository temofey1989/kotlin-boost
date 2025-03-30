package io.justdevit.kotlin.boost.kotest.testcontainters.rabbitmq

import io.justdevit.kotlin.boost.environment.property
import io.justdevit.kotlin.boost.kotest.testcontainers.ContainerHolder
import org.testcontainers.containers.RabbitMQContainer
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy

/**
 * Represents a container holder for managing the lifecycle of a RabbitMQ container.
 */
object RabbitMqHolder : ContainerHolder<RabbitMQContainer>() {

    val imageTag = property("rabbitmq.image-tag", "3-management")

    override fun initializeTool() =
        RabbitMQContainer("rabbitmq:$imageTag").apply {
            start()
            waitingFor(HostPortWaitStrategy())
            System.setProperty("RABBITMQ_HOST", host)
            System.setProperty("RABBITMQ_PORT", amqpPort.toString())
            System.setProperty("RABBITMQ_USER", adminUsername)
            System.setProperty("RABBITMQ_PASSWORD", adminPassword)
        }
}
