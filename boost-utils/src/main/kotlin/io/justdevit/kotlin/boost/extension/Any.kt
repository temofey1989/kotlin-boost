package io.justdevit.kotlin.boost.extension

/**
 * Executes the given [action] if the object is not null.
 *
 * @param action the function to be executed if the object is not null
 *
 * @param T the type of the object
 * @receiver the object to be checked for null
 */
inline fun <T> T?.ifExists(action: (T) -> Unit) {
    if (this != null) {
        action(this)
    }
}

/**
 * Wraps the object to a singleton list.
 *
 * @return a new list containing only the object itself as a single element.
 */
fun <T> T.toSingletonList(): List<T> = listOf(this)

/**
 * Applies the given [block] lambda to the receiver object if it is not null.
 *
 * @param block The lambda function to be applied on the receiver object.
 * @return The receiver object after applying the [block] function, or null if the receiver object is null.
 */
fun <T> T?.applyNotNull(block: T.() -> Unit): T? = this?.apply(block)

/**
 * Returns the non-null value if it is not null, or throws the specified exception.
 *
 * @param block The exception block to be executed if the value is null.
 * @return The non-null value, if it is not null.
 * @throws Exception the specified exception returned by the block if the value is null.
 */
fun <T> T?.orThrow(block: () -> Exception): T = this ?: throw block()

/**
 * Adds a key-value pair to the given context map.
 *
 * @param value The value associated with the key.
 */
context(MutableMap<T, U>)
infix fun <T : Any, U> T.to(value: U) {
    this@MutableMap[this] = value
}

typealias MapBuilder<T, U> = MutableMap<T, U>.() -> Unit
