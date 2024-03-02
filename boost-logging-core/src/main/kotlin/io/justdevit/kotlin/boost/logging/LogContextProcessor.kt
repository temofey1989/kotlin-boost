package io.justdevit.kotlin.boost.logging

/**
 * The LogContextProcessor interface defines the contract for applying and restoring a logging context.
 */
interface LogContextProcessor {
    /**
     * Applies the provided logging context to the current context and returns a snapshot of the current context.
     *
     * @param context The logging context to apply.
     * @return A snapshot of the current logging context before applying the new context.
     */
    fun applyContext(context: LogContext): LogContextSnapshot

    /**
     * Restores the logging context to the provided snapshot.
     *
     * @param snapshot The snapshot of the logging context to restore.
     */
    fun restore(snapshot: LogContextSnapshot)
}
