package io.justdevit.kotlin.boost.logging.slf4j

import io.justdevit.kotlin.boost.logging.Logger
import io.justdevit.kotlin.boost.logging.LoggerFactory
import io.justdevit.kotlin.boost.logging.loggerName

/**
 * The `Slf4jLoggerFactory` class is an implementation of the `LoggerFactory` interface that creates instances of the `Slf4jLogger` class.
 */
class Slf4jLoggerFactory : LoggerFactory {
    override fun getLogger(name: String): Logger =
        Slf4jLogger(
            org
                .slf4j
                .LoggerFactory
                .getLogger(name),
        )

    override fun getLogger(clazz: Class<*>): Logger = getLogger(clazz.loggerName)

    override fun getLogger(function: () -> Unit): Logger = getLogger(function.loggerName)
}
