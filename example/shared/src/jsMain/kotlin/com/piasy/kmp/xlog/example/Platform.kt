package com.piasy.kmp.xlog.example

import com.piasy.kmp.xlog.initializeConsoleLog
import kotlinx.browser.document

class JsPlatform : Platform {
  override val name: String = "JS"
}

actual fun getPlatform(): Platform = JsPlatform()

fun main() {
  initializeConsoleLog()

  document.getElementById("text")?.innerHTML = Greeting().greeting()
}
