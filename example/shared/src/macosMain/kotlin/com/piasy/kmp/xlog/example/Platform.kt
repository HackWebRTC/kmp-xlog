package com.piasy.kmp.xlog.example

import com.piasy.kmp.xlog.Logging
import com.piasy.kmp.xlog.initializeMarsXLog
import platform.Foundation.NSProcessInfo

class MACOSPlatform : Platform {
  override val name: String = "macOS ${NSProcessInfo.processInfo.operatingSystemVersionString}"
}

actual fun getPlatform(): Platform = MACOSPlatform()

fun main() {
  initializeMarsXLog(Logging.LEVEL_DEBUG, "test", true)
  println(Greeting().greeting())
}
