package com.piasy.kmp.xlog.example

import com.piasy.kmp.xlog.Logging

class Greeting {
  private val platform: Platform = getPlatform()

  fun greeting(): String {
    Logging.info("XXPXX", "greeting from ${platform.name}")
    Logging.error("XXPXX", "flush")

    return "Hello, ${platform.name}!"
  }
}
