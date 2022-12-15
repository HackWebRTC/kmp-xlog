# kmp-xlog

KMP wrapper for [tencent mars xlog](https://github.com/Tencent/mars). [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.piasy/kmp-xlog/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.piasy/kmp-xlog)

## Dependency

```kotlin
// add common source set dependency
kotlin {
  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation("com.piasy:kmp-xlog:$version")
      }
    }
  }
}

// add iOS cocoapods dependency
pod 'kmp_xlog', '~> $version'
```

## Usage

```kotlin
object Logging {
  const val LEVEL_DEBUG = 1
  const val LEVEL_INFO = 2
  const val LEVEL_ERROR = 4
}

// Android initialize
fun initializeMarsXLog(
  context: Context,
  logDir: String,
  level: Int,
  namePrefix: String,
  logToConsole: Boolean
)

// iOS initialize
fun initializeMarsXLog(
  level: Int,
  namePrefix: String,
  logToConsole: Boolean
)

// JS initialize
fun initializeConsoleLog()

// logging
object Logging {
  fun debug(tag: String, content: String)

  fun info(tag: String, content: String)

  fun error(tag: String, content: String)
}
```
