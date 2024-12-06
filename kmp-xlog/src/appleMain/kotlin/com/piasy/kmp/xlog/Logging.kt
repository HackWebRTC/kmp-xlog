package com.piasy.kmp.xlog

/**
 * Created by Piasy{github.com/Piasy} on 2022/11/16.
 */
fun initializeMarsXLog(
  level: Int,
  namePrefix: String,
  logToConsole: Boolean
) {
  xlog.PSYMarsXLog.initialize(level, namePrefix, logToConsole)

  Logging.init(IOSLogging)
}

private object IOSLogging : LoggingImpl {
  override fun debug(tag: String, content: String) {
    xlog.PSYMarsXLog.debug(tag, content)
  }

  override fun info(tag: String, content: String) {
    xlog.PSYMarsXLog.info(tag, content)
  }

  override fun error(tag: String, content: String) {
    xlog.PSYMarsXLog.error(tag, content)
  }
}
