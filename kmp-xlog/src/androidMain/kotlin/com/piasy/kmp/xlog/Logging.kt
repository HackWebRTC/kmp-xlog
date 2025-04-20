package com.piasy.kmp.xlog

import android.content.Context
import com.tencent.mars.xlog.Log
import com.tencent.mars.xlog.Xlog

/**
 * Created by Piasy{github.com/Piasy} on 2022/11/16.
 */
private var sInitialized = false

fun initializeMarsXLog(
    context: Context,
    logDir: String,
    level: Int,
    namePrefix: String,
) {
    if (sInitialized) {
        return
    }

    // this is necessary, or may cash for SIGBUS
    val cachePath = context.filesDir.toString() + "/xlog"
    Xlog.open(true, level, Xlog.AppednerModeAsync, cachePath, logDir, namePrefix, "")
    Log.setLogImp(Xlog())
    AndroidLogging.debugLog = level == Logging.LEVEL_DEBUG
    Log.setConsoleLogOpen(AndroidLogging.debugLog)

    Logging.init(AndroidLogging)

    sInitialized = true
}

private object AndroidLogging : LoggingImpl {
    var debugLog: Boolean = false

    override fun debug() = debugLog

    override fun debug(tag: String, content: String) {
        Log.d(tag, "${Thread.currentThread().name} # $content")
    }

    override fun info(tag: String, content: String) {
        Log.i(tag, "${Thread.currentThread().name} # $content")
    }

    override fun error(tag: String, content: String) {
        Log.e(tag, "${Thread.currentThread().name} # $content")
        Log.appenderFlush()
    }
}
