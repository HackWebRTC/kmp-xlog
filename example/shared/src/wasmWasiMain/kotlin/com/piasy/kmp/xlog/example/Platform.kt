package com.piasy.kmp.xlog.example

import com.piasy.kmp.xlog.initializeConsoleLog

class WasmWasiPlatform : Platform {
    override val name: String = "WasmWasi"
}

actual fun getPlatform(): Platform = WasmWasiPlatform()

fun main() {
    initializeConsoleLog(true)

    println(Greeting().greeting())
}
