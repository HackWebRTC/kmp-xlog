package com.piasy.kmp.xlog.example

import com.piasy.kmp.xlog.Logging
import com.piasy.kmp.xlog.initializeConsoleLog

class WasmJsPlatform : Platform {
    override val name: String = "WasmJS"
}

actual fun getPlatform(): Platform = WasmJsPlatform()

fun main() {
    initializeConsoleLog(true)

    Logging.info("XXPXX", Greeting().greeting())
}
