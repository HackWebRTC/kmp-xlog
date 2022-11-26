package com.piasy.kmp.xlog

import kotlin.math.min

/**
 * Created by Piasy{github.com/Piasy} on 2022/11/16.
 */
object Logging {
  const val LEVEL_DEBUG = 1
  const val LEVEL_INFO = 2
  const val LEVEL_ERROR = 4

  internal const val LINE_LENGTH = 4000
  private var impl: LoggingImpl? = null

  internal fun init(impl: LoggingImpl) {
    this.impl = impl
  }

  fun debug(tag: String, content: String) {
    log(tag, content) { t, c ->
      impl?.debug(t, c)
    }
  }

  fun info(tag: String, content: String) {
    log(tag, content) { t, c ->
      impl?.info(t, c)
    }
  }

  fun error(tag: String, content: String) {
    log(tag, content) { t, c ->
      impl?.error(t, c)
    }
  }

  private fun log(
    tag: String,
    content: String,
    logFunc: (
      tag: String,
      content: String
    ) -> Unit
  ) {
    // MarsXLog limit single log length to 4096
    if (content.length <= LINE_LENGTH) {
      logFunc(tag, content)
    } else {
      var write = 0
      while (write < content.length) {
        val endIndex = min(content.length, write + LINE_LENGTH)
        logFunc(tag, content.substring(write, endIndex))
        write = endIndex
      }
    }
  }
}

internal interface LoggingImpl {
  fun debug(tag: String, content: String)

  fun info(tag: String, content: String)

  fun error(tag: String, content: String)
}
