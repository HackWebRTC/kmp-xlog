package com.piasy.kmp.xlog

/**
 * Created by Piasy{github.com/Piasy} on 2022/12/16.
 */
fun initializeMarsXLog(
    level: Int,
    namePrefix: String,
    debugLog: Boolean
) {
    xlog.MarsXLogInitialize(level, namePrefix, if (debugLog) 1 else 0)

    CppLogging.debugLog = debugLog
    Logging.init(CppLogging)
}

private object CppLogging : LoggingImpl {
    var debugLog: Boolean = false

    override fun debug() = debugLog

    override fun debug(tag: String, content: String) {
        xlog.MarsXLogDebug(tag, content)
    }

    override fun info(tag: String, content: String) {
        xlog.MarsXLogInfo(tag, content)
    }

    override fun error(tag: String, content: String) {
        xlog.MarsXLogError(tag, content)
    }
}
