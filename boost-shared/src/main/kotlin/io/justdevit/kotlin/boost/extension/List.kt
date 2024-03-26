package io.justdevit.kotlin.boost.extension

/**
 * Calculates the conjunction of all elements in the list.
 *
 * The conjunction operation returns `true` if and only if all elements in the list are `true`.
 *
 * @receiver The list of boolean values.
 * @return `true` if all elements in the list are `true`, `false` otherwise.
 */
fun List<Boolean>.conjunction() = all { it }

/**
 * Returns `true` if at least one element in the list is `true`, otherwise returns `false`.
 * Calculates the disjunction of all elements in the list.
 *
 * The disjunction operation returns `true` if at least one element in the list is `true`.
 *
 * @receiver The list of boolean values.
 * @return `true` if any element is `true`, `false` otherwise.
 */
fun List<Boolean>.disjunction() = any { it }

/**
 * Copies the receiver object to a new list for the specified number of times with an optional effect applied to each copy.
 *
 * @param times The number of times to copy the object to the list. Defaults to 1 if not provided.
 * @param effect The effect to apply to each copied object. Defaults to the identity function if not provided.
 * @return A new list containing the copied objects.
 */
fun <T : Any?> T.copyToList(times: Int = 1, effect: (T) -> T = { it }): List<T> =
    buildList {
        repeat(times) {
            add(effect(this@copyToList))
        }
    }

/**
 * Checks if the list has a single element.
 *
 * @return `true` if the list has exactly one element, `false` otherwise.
 */
fun <E> List<E>.hasSingleElement(): Boolean = size == 1

/**
 * Maps each element of the given [List] using the provided [mapper] function,
 * or returns an empty [List] if the input [List] is null.
 *
 * @param mapper The function used to map each element of the list.
 * @return A new [List] containing the mapped elements, or an empty [List] if the input [List] is null.
 */
fun <T, U> List<T>?.mapOrEmpty(mapper: (T) -> U): List<U> = this?.map { mapper(it) } ?: emptyList()
