package io.justdevit.kotlin.boost.domain.model

import io.justdevit.kotlin.boost.domain.model.SortDirection.ASC

data class Sort(val attribute: SortAttribute, val direction: SortDirection = ASC)

interface SortAttribute {
    val value: String
}

enum class SortDirection {
    ASC,
    DESC,
}
