package io.justdevit.kotlin.boost.logging.slf4j

import org.slf4j.bridge.SLF4JBridgeHandler

/**
 * Install the JUL (Java Util Logging) to SLF4J (Simple Logging Facade for Java) bridge.
 *
 * This method removes any existing handlers from the root logger using `SLF4JBridgeHandler.removeHandlersForRootLogger()`
 * and installs the SLF4JBridgeHandler using `SLF4JBridgeHandler.install()`.
 *
 * The purpose of installing the bridge is to redirect all JUL log messages to SLF4J, allowing you to use SLF4J for
 * logging in your application, even if third-party libraries or frameworks use JUL.
 *
 * Please make sure to call this method before any JUL logging is done in your application.
 * Usage:
 * ```
 * fun main() {
 *     installJulToSlf4j()
 *     ...
 * }
 * ```
 */
fun installJulToSlf4j() {
    SLF4JBridgeHandler.removeHandlersForRootLogger()
    SLF4JBridgeHandler.install()
}
