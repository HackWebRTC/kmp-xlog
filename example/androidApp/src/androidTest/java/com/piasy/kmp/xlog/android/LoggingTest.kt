package com.piasy.kmp.xlog.android

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.piasy.kmp.xlog.Logging
import com.piasy.kmp.xlog.initializeMarsXLog
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class LoggingTest {
  @Test
  fun testLog() {
    // Context of the app under test.
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    initializeMarsXLog(
      appContext,
      "${appContext.getExternalFilesDir(null)?.absolutePath}/log",
      Logging.LEVEL_DEBUG,
      "test",
      true
    )

    Logging.debug("xxx", "test debug log")
    Logging.info("xxx", "test info log")
    Logging.error("xxx", "test error log")
  }
}
