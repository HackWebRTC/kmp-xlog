package com.piasy.kmp.xlog

/**
 * Created by Piasy{github.com/Piasy} on 2022/12/15.
 */
fun initializeConsoleLog(debugLog: Boolean) {
    WebLogging.debugLog = debugLog
    Logging.init(WebLogging)
}

external interface Console {
    fun info(vararg data: String)
    fun error(vararg data: String)
    fun debug(vararg data: String)
}

external val console: Console

private object WebLogging : LoggingImpl {
    var debugLog: Boolean = false

    override fun debug() = debugLog

    override fun debug(tag: String, content: String) {
        console.debug(tag, content)
    }

    override fun info(tag: String, content: String) {
        console.info(tag, content)
    }

    override fun error(tag: String, content: String) {
        console.error(tag, content)
    }
}
