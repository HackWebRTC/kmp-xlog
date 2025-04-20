package com.piasy.kmp.xlog.example

import com.piasy.kmp.xlog.Logging
import com.piasy.kmp.xlog.initializeMarsXLog

class CppPlatform : Platform {
  override val name: String = "CPP"
}

actual fun getPlatform(): Platform = CppPlatform()

fun main() {
  initializeMarsXLog(Logging.LEVEL_DEBUG, "test")
  println(Greeting().greeting())
}
