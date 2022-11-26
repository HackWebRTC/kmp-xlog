package com.piasy.kmp.xlog.example

import android.content.Context
import com.piasy.kmp.xlog.Logging
import com.piasy.kmp.xlog.initializeMarsXLog

class AndroidPlatform : Platform {
  override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

fun initialize(context: Context) {
  initializeMarsXLog(context, "logs", Logging.LEVEL_DEBUG, "test", true)
}
