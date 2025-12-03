package io.justdevit.kotlin.boost.domain.model

import io.justdevit.kotlin.boost.domain.model.SortDirection.ASC

/**
 * Represents a sorting criteria for data.
 *
 * @param attribute The attribute to sort by.
 * @param direction The sorting direction. Default is [ASC].
 */
data class Sort(val attribute: SortAttribute, val direction: SortDirection = ASC)

/**
 * This interface represents a sorting attribute for data.
 */
interface SortAttribute {
    val value: String
}

/**
 * Represents a simple sort attribute for data.
 *
 * @param value The value of the sort attribute.
 */
data class SimpleSortAttribute(override val value: String) : SortAttribute

/**
 * Enum class representing the sort direction.
 */
enum class SortDirection {
    /**
     * Ascending.
     */
    ASC,

    /**
     * Descending.
     */
    DESC,
}
