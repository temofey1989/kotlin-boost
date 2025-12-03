package io.justdevit.kotlin.boost.kotest.testcontainters.localstack

import io.justdevit.kotlin.boost.environment.property
import io.justdevit.kotlin.boost.kotest.AnnotationExtensionFilter
import io.justdevit.kotlin.boost.kotest.ExtensionFilter
import io.justdevit.kotlin.boost.kotest.ExternalToolExtension
import io.justdevit.kotlin.boost.kotest.testcontainers.ContainerHolder
import org.testcontainers.containers.BindMode.READ_WRITE
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.localstack.LocalStackContainer
import org.testcontainers.utility.DockerImageName

const val LOCALSTACK_SERVICES_PROPERTY = "localstack.services"
const val LOCALSTACK_IMAGE_TAG_PROPERTY = "localstack.image-tag"

object LocalStackHolder : ContainerHolder<LocalStackContainer>() {

    val imageTag = property(LOCALSTACK_IMAGE_TAG_PROPERTY, "latest")
    val services = property(LOCALSTACK_SERVICES_PROPERTY, "")
        .split(",")
        .map(String::trim)

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
            val serviceName = it.uppercase()
            System.setProperty("AWS_${serviceName}_ENDPOINT", endpoint.toString())
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
class LocalStackExtension(filters: Collection<ExtensionFilter> = emptyList()) :
    ExternalToolExtension<LocalStackContainer>(
        holder = LocalStackHolder,
        filters = filters,
    ) {

    constructor(vararg filters: ExtensionFilter) : this(filters.toSet())
    constructor() : this(emptyList())
}

/**
 * Creates a [LocalStackExtension] with the specified predicates for annotation [A].
 *
 * @param predicates The predicates used to filter the annotations.
 * @return The [LocalStackExtension] object.
 */
inline fun <reified A : Annotation> LocalStackExtension(services: Collection<String> = emptyList(), vararg predicates: (A) -> Boolean): LocalStackExtension {
    System.setProperty(LOCALSTACK_SERVICES_PROPERTY, services.joinToString(","))
    val filters = when {
        predicates.isEmpty() -> emptyList()
        else -> predicates.map { AnnotationExtensionFilter(A::class, it) }
    }
    return LocalStackExtension(filters)
}

/**
 * Creates a [LocalStackExtension] with the specified predicate for annotation [A].
 *
 * @param predicate The predicate used to filter the annotations.
 * @return The [LocalStackExtension] object.
 */
inline fun <reified A : Annotation> LocalStackExtension(services: Collection<String> = emptyList(), noinline predicate: (A) -> Boolean) = LocalStackExtension<A>(services, *arrayOf(predicate))
