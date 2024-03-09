package io.justdevit.kotlin.boost.logging

/**
 * The `LoggerFactory` interface provides methods for creating instances of the `Logger` interface.
 */
interface LoggerFactory {
    /**
     * Retrieves an instance of the Logger interface for the specified name.
     *
     * @param name The name of the logger.
     * @return An instance of the Logger interface for the specified name.
     */
    fun getLogger(name: String): Logger

    /**
     * Retrieves a Logger instance for the specified class.
     *
     * @param clazz The class for which the Logger is obtained.
     * @return An instance of the Logger interface for the specified class.
     */
    fun getLogger(clazz: Class<*>): Logger

    /**
     * Retrieves an instance of the Logger interface using the provided function.
     *
     * @param function The function used to find/create the logger.
     * @return An instance of the Logger interface.
     */
    fun getLogger(function: () -> Unit): Logger
}
