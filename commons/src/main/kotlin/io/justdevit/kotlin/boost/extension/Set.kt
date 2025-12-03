package io.justdevit.kotlin.boost.extension

/**
 * Maps the elements of the set to a new set based on the provided mapper function.
 *
 * @param mapper The function used to map each element of the set.
 * @return The resulting set after applying the mapper function to each element of the set,
 *          or an empty set if the input set is null.
 * @param T The type of the elements in the input set.
 * @param U The type of the elements in the resulting set.
 */
fun <T, U> Set<T>?.mapOrEmpty(mapper: (T) -> U): Set<U> = this?.map { mapper(it) }?.toSet() ?: emptySet()
