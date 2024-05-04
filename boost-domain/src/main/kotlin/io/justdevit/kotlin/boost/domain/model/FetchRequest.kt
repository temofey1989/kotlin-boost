package io.justdevit.kotlin.boost.domain.model

const val DEFAULT_PAGE = 0
const val DEFAULT_PAGE_SIZE = 50
val MAX_PAGE_SIZE: Int by lazy {
    System.getenv("MAX_PAGE_SIZE")?.toInt() ?: 200
}

sealed interface FetchRequest

data class PageRequest(
    val pageNumber: Int = DEFAULT_PAGE,
    val pageSize: Int = DEFAULT_PAGE_SIZE,
    val sort: Sort? = null,
    val filters: List<Filter<*>> = emptyList(),
) : FetchRequest

data class CursorRequest(
    val cursor: Cursor,
    val pageSize: Int = DEFAULT_PAGE,
    val filters: List<Filter<*>> = emptyList(),
) : FetchRequest
