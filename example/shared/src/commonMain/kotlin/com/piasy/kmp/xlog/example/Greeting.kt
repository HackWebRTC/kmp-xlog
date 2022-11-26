package com.piasy.kmp.xlog.example

import com.piasy.kmp.xlog.Logging

class Greeting {
  private val platform: Platform = getPlatform()

  fun greeting(): String {
    Logging.info("XXPXX", "greeting from ${platform.name}")

    return "Hello, ${platform.name}!"
  }
}
