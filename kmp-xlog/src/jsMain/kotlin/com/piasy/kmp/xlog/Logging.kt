package com.piasy.kmp.xlog

/**
 * Created by Piasy{github.com/Piasy} on 2022/12/15.
 */
fun initializeConsoleLog() {
  Logging.init(JsLogging)
}

private object JsLogging : LoggingImpl {
  override fun debug(tag: String, content: String) {
    console.log(tag, content)
  }

  override fun info(tag: String, content: String) {
    console.info(tag, content)
  }

  override fun error(tag: String, content: String) {
    console.error(tag, content)
  }
}
