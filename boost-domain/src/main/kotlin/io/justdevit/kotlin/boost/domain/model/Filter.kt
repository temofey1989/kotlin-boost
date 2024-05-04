package io.justdevit.kotlin.boost.domain.model

import io.justdevit.kotlin.boost.domain.model.FilterOperator.EQ
import io.justdevit.kotlin.boost.domain.model.FilterOperator.IN

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

interface FilterAttribute {
    val key: String
}

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
