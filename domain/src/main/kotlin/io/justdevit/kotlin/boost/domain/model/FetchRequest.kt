package io.justdevit.kotlin.boost.domain.model

const val DEFAULT_PAGE = 1
const val DEFAULT_PAGE_SIZE = 50
val MAX_PAGE_SIZE: Int by lazy {
    System.getenv("MAX_PAGE_SIZE")?.toInt() ?: 200
}

/**
 * Represents a fetch request.
 */
sealed interface FetchRequest

/**
 * Represents a request for paginated data.
 *
 * @property pageNumber The page number to retrieve. Default is [DEFAULT_PAGE].
 * @property pageSize The number of items per page. Default is [DEFAULT_PAGE_SIZE].
 * @property sort The sorting criteria for the requested data.
 * @property filters The filters to apply to the requested data.
 */
data class PageRequest(
    val pageNumber: Int = DEFAULT_PAGE,
    val pageSize: Int = DEFAULT_PAGE_SIZE,
    val sort: Sort? = null,
    val filters: List<Filter<*>> = emptyList(),
) : FetchRequest

/**
 * Represents a request for fetching data with pagination using a cursor-based approach.
 *
 * @property cursor The cursor that determines the starting point for fetching data.
 * @property pageSize The number of items to fetch per page. Default is [DEFAULT_PAGE].
 * @property filters The list of filters to apply to the data.
 */
data class CursorRequest(
    val cursor: Cursor,
    val pageSize: Int = DEFAULT_PAGE,
    val filters: List<Filter<*>> = emptyList(),
) : FetchRequest
