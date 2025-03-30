package io.justdevit.kotlin.boost.kotest.testcontainters.localstack

import io.justdevit.kotlin.boost.environment.property
import io.justdevit.kotlin.boost.kotest.AnnotationExtensionFilter
import io.justdevit.kotlin.boost.kotest.ExtensionFilter
import io.justdevit.kotlin.boost.kotest.ExternalToolExtension
import io.justdevit.kotlin.boost.kotest.testcontainers.ContainerHolder
import org.testcontainers.containers.BindMode.READ_WRITE
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.containers.localstack.LocalStackContainer.Service
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.utility.DockerImageName

const val LOCALSTACK_SERVICES_PROPERTY = "localstack.services"
const val LOCALSTACK_IMAGE_TAG_PROPERTY = "localstack.image-tag"

object LocalstackHolder : ContainerHolder<LocalStackContainer>() {

    val imageTag = property(LOCALSTACK_IMAGE_TAG_PROPERTY, "latest")
    val services = property(LOCALSTACK_SERVICES_PROPERTY, "")
        .split(",")
        .map(String::trim)
        .map { Service.valueOf(it) }

    override fun initializeTool() =
        LocalStackContainer(DockerImageName.parse("localstack/localstack:$imageTag")).apply {
            start()
            withEnv("DEBUG", "1")
            withClasspathResourceMapping("/localstack", "/etc/localstack/init/ready.d", READ_WRITE)
            withServices(*services.toTypedArray())
            waitingFor(Wait.forLogMessage(".*Local Stack has been initialized\\.\n", 1))
            configureServiceVariables()
        }

    private fun LocalStackContainer.configureServiceVariables() {
        services.forEach {
            val serviceName = it.name.uppercase()
            System.setProperty("AWS_${serviceName}_ENDPOINT", getEndpointOverride(it).toString())
            System.setProperty("AWS_${serviceName}_REGION", region.toString())
            System.setProperty("AWS_${serviceName}_ACCESS_KEY", accessKey)
            System.setProperty("AWS_${serviceName}_SECRET_KEY", secretKey)
        }
    }
}

/**
 * The `RabbitMqExtension` class is an implementation of the `ExternalToolExtension` interface that represents an extension for running RabbitMQ containers.
 * It provides functionality to start and stop a RabbitMQ container as needed.
 */
class LocalstackExtension(filters: Collection<ExtensionFilter> = emptyList()) :
    ExternalToolExtension<LocalStackContainer>(
        holder = LocalstackHolder,
        filters = filters,
    ) {

    constructor(vararg filters: ExtensionFilter) : this(filters.toSet())
    constructor() : this(emptyList())
}

/**
 * Creates a [LocalstackExtension] with the specified predicates for annotation [A].
 *
 * @param predicates The predicates used to filter the annotations.
 * @return The [LocalstackExtension] object.
 */
inline fun <reified A : Annotation> LocalstackExtension(services: Collection<Service> = emptyList(), vararg predicates: (A) -> Boolean): LocalstackExtension {
    System.setProperty(LOCALSTACK_SERVICES_PROPERTY, services.joinToString(",") { it.name })
    val filters = when {
        predicates.isEmpty() -> emptyList()
        else -> predicates.map { AnnotationExtensionFilter(A::class, it) }
    }
    return LocalstackExtension(filters)
}

/**
 * Creates a [LocalstackExtension] with the specified predicate for annotation [A].
 *
 * @param predicate The predicate used to filter the annotations.
 * @return The [LocalstackExtension] object.
 */
inline fun <reified A : Annotation> LocalstackExtension(services: Collection<Service> = emptyList(), noinline predicate: (A) -> Boolean) = LocalstackExtension<A>(services, *arrayOf(predicate))
