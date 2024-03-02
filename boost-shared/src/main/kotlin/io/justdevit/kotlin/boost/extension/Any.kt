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
