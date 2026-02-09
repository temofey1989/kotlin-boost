package io.justdevit.kotlin.boost.kotest.testcontainters.toxiproxy

import eu.rekawek.toxiproxy.Proxy
import eu.rekawek.toxiproxy.model.ToxicList

/**
 * The Proxic class represents a wrapper around a Toxiproxy proxy instance.
 * It provides essential information and operations for interacting with the proxy.
 *
 * @param proxy The underlying Toxiproxy proxy instance.
 * @param onDeleted The callback invoked when the proxy is deleted.
 *
 * @property listenHost The listen host of the mapped proxy.
 * @property listenPort The listen port of the mapped proxy.
 */
class Proxic internal constructor(
    val listenHost: String,
    val listenPort: Int,
    private val proxy: Proxy,
    private var onDeleted: (Proxic) -> Unit = {},
) {

    /**
     * The unique identifier (name) of the proxy instance.
     */
    val alias: String = proxy.name

    /**
     * The full listen address of the proxy in the format "host:port".
     */
    val listen = "$listenHost:$listenPort"

    /**
     * The upstream endpoint that the proxy forwards traffic to.
     */
    val upstream: String = proxy.upstream

    /**
     * The list of toxic behaviors associated with the proxy.
     */
    val toxics: ToxicList = proxy.toxics()

    /**
     * Enables the proxy instance associated with this object.
     */
    fun enable() = proxy.enable()

    /**
     * Disables the proxy instance associated with this object.
     */
    fun disable() = proxy.disable()

    /**
     * Deletes the proxy instance associated with this object.
     */
    fun delete() {
        proxy.delete()
        onDeleted(this)
    }
}
