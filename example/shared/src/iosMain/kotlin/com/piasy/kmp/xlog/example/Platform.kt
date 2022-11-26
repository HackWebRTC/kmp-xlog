package com.piasy.kmp.xlog.example

import com.piasy.kmp.xlog.Logging
import com.piasy.kmp.xlog.initializeMarsXLog
import platform.UIKit.UIDevice

class IOSPlatform : Platform {
  override val name: String =
    UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

fun initialize() {
  initializeMarsXLog(Logging.LEVEL_DEBUG, "test", true)
}
