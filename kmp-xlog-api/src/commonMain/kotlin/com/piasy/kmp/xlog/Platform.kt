package com.piasy.kmp.xlog

/**
 * Created by Piasy{github.com/Piasy} on 2025-02-28.
 */
expect fun platform(): Int

object Platform {
    const val JVM = 1
    const val IOS = 2
    const val MAC = 3
    const val WINDOWS = 4
    const val LINUX = 5
    const val JS = 6
    const val WASM_JS = 7
    const val WASM_WASI = 8

    private val platform = platform()

    val isJvm get() = platform == JVM
    val isIOS get() = platform == IOS
    val isMAC get() = platform == MAC
    val isWindows get() = platform == WINDOWS
    val isLinux get() = platform == LINUX
    val isJs get() = platform == JS
}
