package com.piasy.kmp.xlog.example

interface Platform {
  val name: String
}

expect fun getPlatform(): Platform
