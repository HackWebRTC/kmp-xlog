package com.piasy.kmp.xlog

/**
 * Created by Piasy{github.com/Piasy} on 2022/11/16.
 */
fun initializeMarsXLog(
    level: Int,
    namePrefix: String,
    debugLog: Boolean
) {
    xlog.PSYMarsXLog.initialize(level, namePrefix, debugLog)

    AppleLogging.debugLog = debugLog
    Logging.init(AppleLogging)
}

private object AppleLogging : LoggingImpl {
    var debugLog: Boolean = false

    override fun debug() = debugLog

    override fun debug(tag: String, content: String) {
        xlog.PSYMarsXLog.debug(tag, content)
    }

    override fun info(tag: String, content: String) {
        xlog.PSYMarsXLog.info(tag, content)
    }

    override fun error(tag: String, content: String) {
        xlog.PSYMarsXLog.error(tag, content)
    }
}
