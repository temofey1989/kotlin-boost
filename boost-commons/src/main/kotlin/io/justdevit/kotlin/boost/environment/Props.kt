package io.justdevit.kotlin.boost.environment

import java.lang.System.getProperty
import java.lang.System.getenv
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.reflect.KClass

/**
 * Retrieves a property value as a string. If the property does not exist, returns a provided default value.
 *
 * @param name The name of the property to retrieve. It will be converted to uppercase,
 * with dots and dashes replaced by underscores, before looking up the property.
 *
 * @param defaultValue The default value to return if the property is not found or is blank.
 * This is optional and defaults to an empty string.
 *
 * @return The property value as a string, or the default value if the property is not found or is blank.
 */
fun property(name: String, defaultValue: String = "") = property(String::class, name) { defaultValue }

/**
 * Retrieves the value of a property or environment variable and converts it to the specified type.
 * If the property or environment variable is not found or is blank, a provided default value is used.
 *
 * @param name The name of the property to retrieve. It will be converted to uppercase,
 * with periods and dashes replaced by underscores, before looking up the environment variable.
 *
 * @param defaultValue The default value to return if the property or environment variable is not found or is blank.
 * This value will be used to determine the type T.
 *
 * @return The value of the property or environment variable converted to the specified type T,
 * or the default value if the property or environment variable is not found or blank.
 */
inline fun <reified T : Any> property(name: String, defaultValue: T? = null): T? = property(T::class, name) { defaultValue }

/**
 * Retrieves a property value as an instance of the specified type T. If the property does not exist or is blank,
 * the provided default value function is used to generate the value.
 *
 * @param name The name of the property to retrieve. It will be converted to uppercase, with periods and dashes
 * replaced by underscores, before looking up the property.
 * @param defaultValueSupplier A function that computes the default value if the property is not found or is blank.
 *
 * @return The property value as an instance of the specified type T, or the default value if the property is not found or is blank.
 */
inline fun <reified T : Any> property(name: String, noinline defaultValueSupplier: () -> T?): T? = property(T::class, name, defaultValueSupplier)

/**
 * Retrieves a property value as an instance of the specified type. If the property does not exist or is blank,
 * the provided default value function is used to generate the value.
 *
 * @param clazz The KClass instance representing the type T of the property.
 * @param name The name of the property to retrieve. It will be transformed and looked up in the environment or system properties.
 * @param defaultValueSupplier A function that computes the default value if the property is not found or is blank.
 * @return The property value as an instance of the specified type T, or the default value if the property is not found or is blank.
 */
@Suppress("UNCHECKED_CAST")
fun <T : Any> property(
    clazz: KClass<T>,
    name: String,
    defaultValueSupplier: () -> T?,
): T? {
    val envName = name
        .replace(".", "_")
        .replace("-", "_")
        .uppercase()
    val value = getenv(envName)?.takeIf { it.isNotBlank() }
        ?: getProperty(name)?.takeIf { it.isNotBlank() }
    return if (value == null) defaultValueSupplier() else value.toTypedValue(clazz) as T
}

/**
 * Retrieves a property value, splits it by the specified separator, and converts each element to the specified type.
 *
 * @param name The name of the property to retrieve and split.
 * @param separator The delimiter that separates the elements in the property string. Defaults to a comma (`,`).
 *
 * @return A list of elements converted to the specified type.
 */
inline fun <reified T : Any> propertyList(name: String, separator: String = ","): List<T> = propertyList(T::class, name, separator)

/**
 * Retrieves a list of property values.
 *
 * @param clazz The class of the type to which each resulting element should be converted.
 * @param name The name of the property to retrieve and split.
 * @param separator The delimiter that separates the elements in the property string. Defaults to a comma (`,`).
 *
 * @return A list of elements converted to the specified type.
 */
@Suppress("UNCHECKED_CAST")
fun <T : Any> propertyList(
    clazz: KClass<T>,
    name: String,
    separator: String = ",",
): List<T> =
    property(name, "")
        ?.split(separator)
        ?.map { it.trim() }
        ?.map { it.toTypedValue(clazz) as T }
        ?: emptyList()

private fun String.toTypedValue(clazz: KClass<*>) =
    when (clazz) {
        String::class -> this
        Int::class -> this.toInt()
        Long::class -> this.toLong()
        BigInteger::class -> this.toBigInteger()
        Float::class -> this.toFloat()
        Double::class -> this.toDouble()
        BigDecimal::class -> this.toBigDecimal()
        Boolean::class -> this.toBoolean()
        else -> throw IllegalArgumentException("Unsupported property type '$clazz' for property value: '$this'.")
    }
