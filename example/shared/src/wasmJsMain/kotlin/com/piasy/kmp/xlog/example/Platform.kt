package com.piasy.kmp.xlog.example

import kotlinx.browser.document

class WasmJsPlatform : Platform {
  override val name: String = "WasmJS"
}

actual fun getPlatform(): Platform = WasmJsPlatform()

fun main() {
  document.getElementById("text")?.innerHTML = Greeting().greeting()
}
