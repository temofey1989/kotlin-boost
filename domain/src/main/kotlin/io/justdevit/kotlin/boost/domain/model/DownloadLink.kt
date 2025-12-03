package io.justdevit.kotlin.boost.domain.model

import java.net.URI
import java.time.Instant

/**
 * Represents a download link.
 *
 * @property link The URI of the download link.
 * @property expiration The expiration time of the download link (optional).
 */
data class DownloadLink(val link: URI, val expiration: Instant? = null)
