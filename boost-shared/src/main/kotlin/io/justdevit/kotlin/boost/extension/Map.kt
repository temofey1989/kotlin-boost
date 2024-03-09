package io.justdevit.kotlin.boost.extension

import kotlin.collections.mapKeys as kotlinMapKeys
import kotlin.collections.mapValues as kotlinMapValues

/**
 * Executes the provided [block] if the map contains the specified [key].
 *
 * @param key the key to check in the map
 * @param block the block of code to be executed if the key is found in the map
 * @receiver the map on which the method is called
 *
 * @see containsKey
 * @see let
 */
inline fun <T, U> Map<T, U>.forKey(key: T, block: (U) -> Unit) {
    if (containsKey(key)) {
        this[key]?.let { block(it) }
    }
}

/**
 * Transforms the keys of the map using the specified transform function and returns a new map
 * with the transformed keys.
 *
 * @param transform the transform function to apply to each key.
 * @return a new map with the transformed keys.
 */
fun <K, V, R> Map<out K, R>.mapKeys(transform: (K) -> V): Map<V, R> = this.kotlinMapKeys { transform(it.key) }

/**
 * Returns a new map with the same keys as this map, but with the values transformed
 * by the given [transform] function.
 *
 * @param transform the function to transform the values
 * @return the new map with transformed values
 *
 * @param K the type of keys in the original map
 * @param V the type of values in the original map
 * @param R the type of values in the resulting map
 *
 * @since 1.0
 */
fun <K, V, R> Map<out K, V>.mapValues(transform: (V) -> R): Map<K, R> = this.kotlinMapValues { transform(it.value) }

/**
 * Returns the value to which the specified key is mapped, or an empty collection if this map contains no mapping
 * for the key.
 *
 * @param key The key whose associated value is to be returned.
 * @return The value to which the specified key is mapped, or an empty collection if this map contains no mapping
 * for the key.
 */
fun <T, U> Map<T, Collection<U>>.getOrEmpty(key: T) = getOrElse(key) { emptyList() }

/**
 * Returns the value obtained by applying the specified mapper function to the value
 * associated with the given key in this map.
 *
 * @param key The key used to retrieve the value from the map.
 * @param mapper The function to apply to the value.
 * @return The value obtained by applying the mapper function to the value associated
 * with the given key.
 *
 * @throws [NoSuchElementException] if the specified key is not present in the map.
 */
fun <T : Any, U : Any, V : Any> Map<T, U>.forKeyMap(key: T, mapper: (U) -> V): V = mapper(getValue(key))

/**
 * Retrieves the value associated with the given [key] from the map and maps it using the provided [mapper] function.
 * Returns the result of the mapping operation, or null if the key is not present in the map.
 *
 * @param key The key to retrieve the value for.
 * @param mapper The function used to map the retrieved value.
 * @return The mapped value if the key is present in the map, null otherwise.
 */
fun <T : Any, U : Any, V : Any> Map<T, U>.forKeyMapOrNull(key: T, mapper: (U) -> V): V? = this[key]?.let(mapper)
