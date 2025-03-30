package io.justdevit.kotlin.boost.kotest

import io.kotest.core.extensions.Extension

const val HIGHEST_PRECEDENCE = Int.MIN_VALUE
const val DEFAULT_PRECEDENCE = 0
const val LOWEST_PRECEDENCE = Int.MAX_VALUE

/**
 * Represents an extension with a predefined precedence level to determine its priority in execution or ordering.
 *
 * Extensions implementing this interface can define custom precedence levels, which are compared using the `precedence` property.
 *
 * By default, the value of `precedence` is set to [DEFAULT_PRECEDENCE]. Extensions with lower precedence values are given higher priority.
 */
interface PrioritizedExtension {
    /**
     * Defines the precedence level for a prioritized extension.
     */
    val precedence: Int
        get() = DEFAULT_PRECEDENCE
}

fun <T : Extension> List<T>.sortedByPrecedence() = sortedBy { if (it is PrioritizedExtension) it.precedence else DEFAULT_PRECEDENCE }
