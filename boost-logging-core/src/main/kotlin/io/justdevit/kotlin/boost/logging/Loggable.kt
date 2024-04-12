package io.justdevit.kotlin.boost.logging

/**
 * The `Loggable` interface represents an object that is capable of logging messages using a `Logger` instance.
 *
 * With the `Loggable` interface, classes can define a `log` property of type `Logger` to enable logging capabilities.
 *
 * Example usage:
 * ```
 * class MyClass : Loggable {
 *     override val log: Logger = logger("MyClass")
 * }
 * ```
 *
 * @see Logger
 */
interface Loggable {
    val log: Logger
}

/**
 * The Logging class is a base class that provides logging capabilities using a logger instance.
 *
 * The Logging class implements the Loggable interface, which defines a log property of type Logger, allowing classes to enable logging capabilities.
 *
 * Example usage:
 *
 * ```kotlin
 * class MyClass : Logging {
 *
 *     fun doAction() {
 *         log.info { "Action has started." }
 *         ...
 *     }
 * }
 * ```
 *
 * @param name The name of the logger. If not provided, the name is set to the class name.
 * @see Loggable
 * @see Logger
 */
open class Logging(name: String? = null) : Loggable {
    override val log: Logger = logger(name ?: javaClass.loggerName)
}
