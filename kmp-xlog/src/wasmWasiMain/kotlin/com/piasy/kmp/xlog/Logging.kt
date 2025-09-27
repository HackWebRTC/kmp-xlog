package com.piasy.kmp.xlog

/**
 * Created by Piasy{github.com/Piasy} on 2022/12/15.
 */
fun initializeConsoleLog(debugLog: Boolean) {
    WasmWasiLogging.debugLog = debugLog
    Logging.init(WasmWasiLogging)
}

private object WasmWasiLogging : LoggingImpl {
    var debugLog: Boolean = false

    override fun debug() = debugLog

    override fun debug(tag: String, content: String) {
        println("$tag $content")
    }

    override fun info(tag: String, content: String) {
        println("$tag $content")
    }

    override fun error(tag: String, content: String) {
        println("$tag $content")
    }
}
