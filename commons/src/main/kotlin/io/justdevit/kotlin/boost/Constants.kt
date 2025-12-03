package io.justdevit.kotlin.boost

import java.time.format.DateTimeFormatter

object Constants {
    val ISO8601_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ")
    val EUROPEAN_DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("d.M.yyyy")
    val EUROPEAN_DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("d.M.yyyy HH:mm:ss")
}
