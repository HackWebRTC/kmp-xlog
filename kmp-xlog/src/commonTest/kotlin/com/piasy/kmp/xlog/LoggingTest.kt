package com.piasy.kmp.xlog

import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import kotlin.test.Test

class LoggingTest {

  private fun mockLoggingImpl(): LoggingImpl {
    val impl = mockk<LoggingImpl>()
    every { impl.debug(any(), any()) } returns Unit
    every { impl.info(any(), any()) } returns Unit
    every { impl.error(any(), any()) } returns Unit
    Logging.init(impl)

    return impl
  }

  @Test
  fun testLog() {
    val impl = mockLoggingImpl()

    val tag = "xxx"
    val content = "test log"
    Logging.debug(tag, content)
    Logging.info(tag, content)
    Logging.error(tag, content)

    verifySequence {
      impl.debug(tag, content)
      impl.info(tag, content)
      impl.error(tag, content)
    }
  }

  @Test
  fun testSplit() {
    val impl = mockLoggingImpl()

    val tag = "xxx"
    val c1 = "a".repeat(Logging.LINE_LENGTH - 1)
    val c2 = "b"
    val c3 = "c"
    val c4 = "d".repeat(100)

    Logging.info(tag, c1)
    Logging.info(tag, c1 + c2)
    Logging.info(tag, c1 + c2 + c3)
    Logging.info(tag, c1 + c2 + c3 + c4)

    verifySequence {
      impl.info(tag, c1)

      impl.info(tag, c1 + c2)

      impl.info(tag, c1 + c2)
      impl.info(tag, c3)

      impl.info(tag, c1 + c2)
      impl.info(tag, c3 + c4)
    }
  }
}
