package io.justdevit.kotlin.boost.kotest

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * A holder class responsible for managing the lifecycle of an external tool or resource.
 *
 * This class ensures thread-safe initialization and access to a tool of type [T].
 * It uses lazy initialization to create the tool when it is first accessed.
 * Additionally, it provides mechanisms for reinitializing and tearing down the tool.
 *
 * @param T The type of the tool or resource managed by this holder.
 */
abstract class ExternalToolHolder<T> {

    private val lock = ReentrantLock()
    protected var materialized: T? = null

    /**
     * Provides access to a lazily initialized tool or resource managed by this holder.
     *
     * Calling this property triggers the initialization process if the tool is not already initialized.
     * A thread-safe mechanism ensures only a single initialization occurs even in concurrent scenarios.
     *
     * @throws IllegalStateException If the tool or resource is not successfully initialized.
     */
    val tool: T
        get() {
            initialize()
            return lock.withLock { materialized } ?: throw IllegalStateException("No extension target found!")
        }

    /**
     * Performs thread-safe initialization of the `materialized` resource.
     */
    fun initialize() {
        if (materialized != null) {
            return
        }
        lock.withLock {
            if (materialized == null) {
                materialized = initializeTool()
            }
        }
    }

    /**
     * Initializes and returns a materialized object of type [T] managed by the extension.
     *
     * @return An instance of the materialized object of type [T].
     */
    protected abstract fun initializeTool(): T

    /**
     * Re-initialize the lifecycle of the extension.
     */
    fun reinitialize() {
        lock.withLock {
            tearDownTool()
            materialized = null
            initialize()
        }
    }

    /**
     * Cleans up and releases resources associated with the `materialized` tool or resource.
     */
    protected open fun tearDownTool() {
    }
}
