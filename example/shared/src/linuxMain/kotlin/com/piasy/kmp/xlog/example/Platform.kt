package com.piasy.kmp.xlog.example

import com.piasy.kmp.xlog.Logging
import com.piasy.kmp.xlog.initializeMarsXLog

class LinuxPlatform : Platform {
  override val name: String = "Linux"
}

actual fun getPlatform(): Platform = LinuxPlatform()

fun main() {
  initializeMarsXLog(Logging.LEVEL_DEBUG, "test", true)
  println(Greeting().greeting())
}
