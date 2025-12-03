package io.justdevit.kotlin.boost.logging.slf4j

import io.justdevit.kotlin.boost.logging.LogRecordBuilder
import io.justdevit.kotlin.boost.logging.LogRecordBuilderFunction
import io.justdevit.kotlin.boost.logging.Logger
import net.logstash.logback.marker.Markers
import org.slf4j.Marker

/**
 * The Slf4jLogger class is an implementation of the Logger interface that uses the SLF4J logging framework.
 *
 * @param logger The underlying SLF4J Logger.
 */
internal class Slf4jLogger(private val logger: org.slf4j.Logger) : Logger {
    override fun trace(builder: LogRecordBuilderFunction) {
        if (logger.isTraceEnabled) doLog(builder, logger::trace)
    }

    override fun trace(throwable: Throwable, builder: LogRecordBuilderFunction) {
        if (logger.isTraceEnabled) doLog(throwable, builder, logger::trace)
    }

    override fun debug(builder: LogRecordBuilderFunction) {
        if (logger.isDebugEnabled) doLog(builder, logger::debug)
    }

    override fun debug(throwable: Throwable, builder: LogRecordBuilderFunction) {
        if (logger.isDebugEnabled) doLog(throwable, builder, logger::debug)
    }

    override fun info(builder: LogRecordBuilderFunction) {
        if (logger.isInfoEnabled) doLog(builder, logger::info)
    }

    override fun info(throwable: Throwable, builder: LogRecordBuilderFunction) {
        if (logger.isInfoEnabled) doLog(throwable, builder, logger::info)
    }

    override fun warn(builder: LogRecordBuilderFunction) {
        if (logger.isWarnEnabled) doLog(builder, logger::warn)
    }

    override fun warn(throwable: Throwable, builder: LogRecordBuilderFunction) {
        if (logger.isWarnEnabled) doLog(throwable, builder, logger::warn)
    }

    override fun error(builder: LogRecordBuilderFunction) {
        if (logger.isErrorEnabled) doLog(builder, logger::error)
    }

    override fun error(throwable: Throwable, builder: LogRecordBuilderFunction) {
        if (logger.isErrorEnabled) doLog(throwable, builder, logger::error)
    }

    private fun doLog(builder: LogRecordBuilderFunction, logFun: (Marker?, String) -> Unit) {
        val record = builder.toLogRecord()
        val marker = Markers.appendEntries(record.attributes)
        logFun(marker, record.message)
    }

    private fun doLog(
        throwable: Throwable,
        builder: LogRecordBuilderFunction,
        logFun: (Marker?, String, Throwable) -> Unit,
    ) {
        val record = builder.toLogRecord(throwable)
        val marker = Markers.appendEntries(record.attributes)
        logFun(marker, record.message, throwable)
    }

    private fun LogRecordBuilderFunction.toLogRecord(throwable: Throwable? = null) = LogRecordBuilder(this, throwable).build()
}
