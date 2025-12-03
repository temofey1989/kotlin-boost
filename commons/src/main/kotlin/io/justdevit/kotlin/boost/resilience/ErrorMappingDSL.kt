@file:Suppress("UNCHECKED_CAST")

package io.justdevit.kotlin.boost.resilience

import io.justdevit.kotlin.boost.resilience.ErrorMappingRejection.Companion.INSTANCE
import kotlinx.coroutines.delay
import kotlin.math.pow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.ZERO

/**
 * Executes the given action while providing the ability to map and handle errors
 * using the specified configuration for error handling.
 *
 * @param T The type of the result produced by the action.
 * @param action A suspending action that is executed.
 * @param configurer A function used to configure error mappings and handlers using
 *                   an instance of [ErrorMappingExecutorBuilder].
 * @return The result of the action if executed successfully, or the result of the
 *         applicable error handler if an error occurs.
 */
suspend fun <T> withErrorMapping(action: suspend () -> T, configurer: ErrorMappingExecutorBuilder<T>.() -> Unit): T =
    ErrorMappingExecutorBuilder(action)
        .apply(configurer)
        .build()
        .execute()

/**
 * Represents a condition to evaluate whether a specific exception satisfies certain criteria.
 * This functional interface is primarily used to filter exceptions in the context of error handling logic.
 *
 * @param E The type of [Throwable] for which the condition is evaluated.
 */
fun interface ErrorMappingCondition<E : Throwable> {
    operator fun invoke(exception: E): Boolean
}

/**
 * Marker for declarations within the Error Mapping DSL to restrict their scope
 * and clarify their usage, serving as a boundary for DSL context-specific operations.
 */
@DslMarker
internal annotation class ErrorMappingDslMarker

/**
 * The component to execute the provided action and apply error handling logic based on specified error handlers.
 *
 * @param T The type of the result produced by the action.
 * @property action A suspending lambda representing the main operation to be executed.
 * @property errorHandlers A list of [ErrorMappingHandler] instances that define how to handle specific errors.
 */
internal class ErrorMappingExecutor<T>(private val action: suspend () -> T, private val errorHandlers: List<ErrorMappingHandler<T, *, *>>) {
    /**
     * Executes the main action while applying error handling logic based on a list of error handlers.
     * If the action succeeds, its result is returned. If an error occurs, the error is processed by
     * the first applicable error handler. If no handler supports the error, the original error is rethrown.
     *
     * @return The result of the executed action if successful, or the result of the corresponding
     *         error handler if an error occurs that is supported by one of the handlers.
     */
    suspend fun execute(): T =
        try {
            action()
        } catch (throwable: Throwable) {
            try {
                errorHandlers.firstOrNull { it.supports(throwable) }?.execute(throwable) ?: throw throwable
            } catch (_: NoSuchElementException) {
                throw throwable
            }
        }
}

/**
 * A DSL builder for configuring an error mapping executor. This provides a flexible way to define
 * error handling logic for different types of exceptions thrown during the execution of a suspending action.
 *
 * @param T The type of the result produced by the action being executed.
 * @property action The suspending operation to be executed by the error mapping executor.
 */
@ErrorMappingDslMarker
class ErrorMappingExecutorBuilder<T>(private val action: suspend () -> T) {
    private val errorHandlers = mutableListOf<ErrorMappingHandler<T, *, *>>()
    private val executor =
        ErrorMappingExecutor(
            action = action,
            errorHandlers = errorHandlers,
        )

    /**
     * Configures an error mapping handler for the specified condition targeting [Throwable].
     * The handler defines how errors matching the condition should be processed during execution.
     *
     * @param condition The condition used to match exceptions of type [Throwable].
     * @param configurer A lambda for configuring the error mapping handling logic.
     */
    fun on(condition: ErrorMappingCondition<Throwable>, configurer: ErrorMappingHandlerBuilder<T, Throwable, Throwable>.() -> Unit) {
        errorHandlers +=
            ErrorMappingHandlerBuilder<T, Throwable, Throwable>(
                exceptionClass = Throwable::class.java,
                action = executor::execute,
                condition = condition,
            ).apply(configurer).build()
    }

    /**
     * Configures an error mapping handler for the specified condition targeting [E].
     * The handler defines how errors matching the condition should be processed during execution.
     *
     * @param E The type of exception to handle.
     * @param U The type of the mapped result produced from the handled exception.
     * @param exceptionClass The class of the exception type to handle.
     * @param condition A condition to evaluate if the exception matches the criteria for handling. Defaults to always `true`.
     * @param configurer A lambda for configuring the error mapping handling logic specific to the provided exception type.
     * @param exceptionMapper A mapper function to convert the exception into the desired mapped result type.
     */
    fun <E : Throwable, U> on(
        exceptionClass: Class<E>,
        condition: ErrorMappingCondition<E> = ErrorMappingCondition { true },
        configurer: ErrorMappingHandlerBuilder<T, E, U>.() -> Unit,
        exceptionMapper: (E) -> U = { it as U },
    ) {
        errorHandlers +=
            ErrorMappingHandlerBuilder(
                exceptionClass = exceptionClass,
                action = executor::execute,
                condition = condition,
                exceptionMapper = exceptionMapper,
            ).apply(configurer).build()
    }

    /**
     * Configures an error mapping handler for the specified condition targeting [E].
     * The handler defines how errors matching the condition should be processed during execution.
     *
     * @param E The type of exception to handle.
     * @param exceptionClass The class of the exception type to handle.
     * @param configurer A lambda for configuring the error mapping handling logic specific to the provided exception type.
     */
    fun <E : Throwable> on(exceptionClass: Class<E>, configurer: ErrorMappingHandlerBuilder<T, E, E>.() -> Unit) {
        errorHandlers +=
            ErrorMappingHandlerBuilder<T, E, E>(
                exceptionClass = exceptionClass,
                action = executor::execute,
                condition = { true },
            ).apply(configurer).build()
    }

    /**
     * Configures an error mapping handler for the specified condition targeting [E].
     * The handler defines how errors matching the condition should be processed during execution.
     *
     * @param E The type of exception to handle.
     * @param configurer A lambda for configuring the error mapping handling logic specific to the provided exception type.
     */
    inline fun <reified E : Throwable> on(noinline configurer: ErrorMappingHandlerBuilder<T, E, E>.() -> Unit) {
        on(E::class.java, configurer)
    }

    internal fun build() = executor
}

/**
 * A handler that maps and processes exceptions based on specific conditions and actions.
 *
 * @param T The type of the result produced after processing the exception.
 * @param E The type of exception this handler is designed to process. Must be a subclass of [Throwable].
 * @param U The type of the mapped result produced by converting the exception.
 * @property exceptionClass The class of the target exception type being handled.
 * @property condition A condition that determines if the handler supports the exception.
 * @property actions A queue of actions to execute for the exception. Each action processes a mapped result of type [U].
 * @property exceptionMapper A function that maps the exception of type [E] into the result type [U].
 */
internal class ErrorMappingHandler<T, E : Throwable, U>(
    private val exceptionClass: Class<E>,
    private val condition: ErrorMappingCondition<E>,
    private val actions: ArrayDeque<ErrorMappingAction<T, U>> = ArrayDeque(),
    private val exceptionMapper: (E) -> U = { it as U },
) {
    /**
     * Determines whether the given exception is supported by this handler.
     *
     * @param throwable The exception to be checked for support by this handler.
     * @return `true` if the handler supports the given exception, otherwise `false`.
     */
    fun supports(throwable: Throwable) =
        actions.isNotEmpty() &&
            exceptionClass.isAssignableFrom(throwable.javaClass) &&
            condition(exceptionClass.cast(throwable))

    /**
     * Executes the first available action in the queue by processing the provided exception.
     *
     * @param throwable The exception to be mapped and processed.
     * @return The result of the executed action after processing the mapped exception.
     * @throws NoSuchElementException If there are no more actions left to execute.
     * @throws Throwable The original exception if an error mapping rejection occurs.
     */
    @Suppress("UNCHECKED_CAST")
    suspend fun execute(throwable: Throwable): T =
        try {
            actions.removeFirstOrNull()?.invoke(exceptionMapper(throwable as E))
                ?: throw NoSuchElementException("No more actions.")
        } catch (_: ErrorMappingRejection) {
            throw throwable
        }
}

/**
 * Builder providing DSL for creating an instance of [ErrorMappingHandler].
 *
 * @param T The type of the result produced after executing the defined actions in the handler.
 * @param E The type of exception handled by the handler. Must be a subclass of [Throwable].
 * @param U The type of the mapped result produced by transforming the exception.
 * @property exceptionClass The class of the exception being handled by the handler.
 * @property action The main action to be invoked when exception handling begins.
 * @property condition A condition to evaluate if the exception should be processed by this handler.
 * @property exceptionMapper A mapping function that converts the exception of type [E] into result type [U].
 */
@ErrorMappingDslMarker
class ErrorMappingHandlerBuilder<T, E : Throwable, U>(
    private val exceptionClass: Class<E>,
    private val action: suspend () -> T,
    private val condition: ErrorMappingCondition<E> = ErrorMappingCondition { true },
    private val exceptionMapper: (E) -> U = { it as U },
) {
    private val actions = ArrayDeque<ErrorMappingAction<T, U>>()

    /**
     * Adds a retry mechanism to handle errors by executing the action a specified number of times.
     * The retry intervals can include delays and can be adjusted using a multiplier factor.
     *
     * @param times The number of retry attempts. Defaults to `1`.
     * @param delay The delay duration between each retry attempt. Defaults to [Duration.ZERO].
     * @param multiplier The factor by which the delay time is multiplied after each retry attempt. Defaults to `1.0`.
     */
    fun retry(
        times: Int = 1,
        delay: Duration = ZERO,
        multiplier: Double = 1.0,
    ) {
        repeat(times) { index ->
            actions +=
                ErrorMappingAction {
                    if (delay > ZERO) {
                        val waitTime = delay * multiplier.pow(index)
                        if (waitTime > ZERO) {
                            delay(waitTime)
                        }
                    }
                    action()
                }
        }
    }

    /**
     * Adds a fallback value to the error mapping actions, which will be returned when a specific condition is met.
     *
     * @param value The value to be returned if the associated error condition is satisfied.
     */
    fun result(value: T) {
        actions += ErrorMappingAction { value }
    }

    /**
     * Adds an error mapping action to the builder. The provided supplier function is executed
     * when the action is invoked, allowing for custom error handling or transformation logic.
     *
     * @param supplier A suspend function to be executed within the context of the [ErrorMappingAction], which takes a parameter of type [U] and returns a value of type [T].
     */
    fun execute(supplier: suspend ErrorMappingAction<T, U>.(U) -> T) {
        actions += ErrorMappingAction { supplier(it) }
    }

    /**
     * Adds an error mapping action to throw the specified throwable when triggered.
     *
     * @param throwable The exception to be thrown as part of the error mapping action.
     */
    fun exception(throwable: Throwable) {
        actions += ErrorMappingAction { throw throwable }
    }

    /**
     * Adds an error mapping action to the builder that throws the exception provided by the given suspend supplier when triggered.
     *
     * @param supplier A suspend function that takes a parameter of type [U] and returns an instance of [Throwable] to be thrown as part of the error mapping action.
     */
    fun exception(supplier: suspend (U) -> Throwable) {
        actions += ErrorMappingAction { throw supplier(it) }
    }

    /**
     * Builds an instance of [ErrorMappingHandler] using the configured parameters.
     *
     * @return An instance of [ErrorMappingHandler] initialized with the specified exception class, condition, actions, and exception mapper.
     */
    internal fun build() = ErrorMappingHandler(exceptionClass, condition, actions, exceptionMapper)
}

/**
 * Represents an action capable of performing error mapping by processing an input of type [U]
 * and returning a result of type [T]. The action is defined as a suspend function that can
 * optionally reject mapping by throwing a specific internal exception.
 *
 * @param T The type of the result produced by the action.
 * @param U The type of the input passed to the action.
 * @property action A suspend function executed within the context of the action, which processes an input of type [U]
 * and produces a result of type [T].
 */
class ErrorMappingAction<T, U>(private val action: suspend ErrorMappingAction<T, U>.(U) -> T) {
    /**
     * Executes the action by processing the provided input value of type [U]
     * and returning a result of type [T]. This function is a suspending operation.
     *
     * @param value The input value to be processed by the action.
     * @return The result of type [T] produced by the action after processing the input.
     */
    suspend fun invoke(value: U): T = action(value)

    /**
     * Rejects the current error mapping operation by throwing an [ErrorMappingRejection] exception.
     *
     * This method is used within an error mapping block to indicate that the current
     * error mapping process should not proceed and the rejection should propagate.
     *
     * @return This method does not return as it always throws an exception.
     */
    fun reject(): Nothing = throw ErrorMappingRejection.INSTANCE
}

/**
 * A singleton exception used to signify the rejection of an error mapping operation.
 *
 * @constructor Private constructor to enforce singleton usage via [INSTANCE].
 */
class ErrorMappingRejection private constructor() : RuntimeException() {
    companion object {
        /**
         * A singleton instance of [ErrorMappingRejection], used to signify the rejection of an error mapping operation.
         */
        val INSTANCE = ErrorMappingRejection()
    }
}
