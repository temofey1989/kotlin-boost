package io.justdevit.kotlin.boost.logging

/**
 * The LogContextProcessor interface defines the contract for applying and restoring a logging context.
 */
interface LogContextProcessor {
    /**
     * Retrieves the current attributes of the logging context.
     *
     * @return A map containing the current attributes as key-value pairs, where the keys are strings
     * representing attribute names and the values are nullable strings representing attribute values.
     */
    fun currentAttributes(): Map<String, String?>

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
