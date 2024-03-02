package io.justdevit.kotlin.boost.logging

/**
 * Retrieves the logger for the current class. The logger is an instance of the Logger interface,
 * which provides methods for logging different levels of messages.
 *
 * @receiver The current class instance.
 * @return The logger for the current class.
 */
val Class<*>.log: Logger
    get() = logger(this.name)

/**
 * Retrieves the logger name for a function.
 *
 * @receiver The function for which to retrieve the logger name.
 * @return The logger name for the function.
 */
val (() -> Unit).loggerName: String
    get() = javaClass.loggerName

/**
 * Returns the logger name for a given class.
 * The logger name is derived from the class name by removing any additional suffixes or dollar signs.
 *
 * @return the logger name
 */
val Class<*>.loggerName: String
    get() =
        when {
            name.contains("Kt$") -> name.substringBefore("Kt$")
            name.contains("$") -> name.substringBefore("$")
            else -> name
        }
