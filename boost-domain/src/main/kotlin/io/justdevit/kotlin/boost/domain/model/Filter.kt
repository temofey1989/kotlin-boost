package io.justdevit.kotlin.boost.domain.model

import io.justdevit.kotlin.boost.domain.model.FilterOperator.EQ
import io.justdevit.kotlin.boost.domain.model.FilterOperator.IN

/**
 * Represents a filter used to apply conditions on a specific attribute.
 *
 * @param attribute The attribute to filter on.
 * @param operator The operator to apply for the filter.
 * @param values The list of values to filter against.
 * @param T The type of values for the filter.
 */
data class Filter<T>(
    val attribute: FilterAttribute,
    val operator: FilterOperator,
    val values: List<T>,
) {
    val value: T
        get() = values.first()

    val optimizedOperator: FilterOperator
        get() = when {
            operator == IN && values.size == 1 -> EQ
            operator == EQ && values.size > 1 -> IN
            else -> operator
        }
}

/**
 * Represents a filter attribute used to apply conditions on a specific attribute.
 */
interface FilterAttribute {
    val key: String
}

/**
 * Represents a simple filter attribute used to apply conditions on a specific key.
 *
 * @property key The key of the filter attribute.
 */
data class SimpleFilterAttribute(override val key: String) : FilterAttribute

/**
 * Enum class representing different filter operators used to apply conditions on a specific attribute.
 *
 * The available filter operators are:
 *
 * - [EQ] - Represents the equality operator.
 * - [NE] - Represents the not-equal operator.
 * - [GT] - Represents the greater-than operator.
 * - [GE] - Represents the greater-than-or-equal operator.
 * - [LT] - Represents the less-than operator.
 * - [LE] - Represents the less-than-or-equal operator.
 * - [LI] - Represents the like operator.
 * - [IN] - Represents the in operator.
 * - [NI] - Represents the not-in operator.
 */
enum class FilterOperator {
    EQ,
    NE,
    GT,
    GE,
    LT,
    LE,
    LI,
    IN,
    NI,
}
