# kmp-xlog

KMP wrapper for [tencent mars xlog](https://github.com/Tencent/mars).

![Maven Central Version](https://img.shields.io/maven-central/v/com.piasy/kmp-xlog) ![Main branch status](https://github.com/HackWebRTC/kmp-xlog/actions/workflows/test_and_run_demo.yml/badge.svg?branch=main)

## Dependency

For Android/JS/Linux/Windows, you only need to add gradle dependency, for iOS/macOS you need add an extra cocoapods dependency.

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

// add iOS/macOS cocoapods dependency
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

// iOS/macOS/Linux/Windows initialize
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

## Env Setup

You need install [RVM](https://rvm.io/) to manage your ruby version, and install gems. You need use homebrew to install the following tools:

```bash
brew install cocoapods xcodegen
# if you have installed them earlier, you need remove them at first,
# or run brew link --overwrite xcodegen cocoapods
```

You may need to restart your system so that Android Studio could use the correct ruby.

If you see "pod install" error when you open the project in Android Studio:

```bash
> Task :example:shared:podInstall FAILED

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':example:shared:podInstall'.
> 'pod install' command failed with code 1.
  Error message:

          Please, check that podfile contains following lines in header:
          source 'https://cdn.cocoapods.org'

          Please, check that each target depended on shared contains following dependencies:
```

Please run `./scripts/build_apple.sh` then open/sync again.

## Example

### Android

Open the project (the repo root dir) in Android studio, and run the example.androidApp target.

### iOS

```bash
cd example/iosApp
pod install
# open iosApp.xcworkspace in Xcode, and run it.
```

### JS

```bash
./gradlew :example:shared:jsBrowserRun
```

### Linux

Install deps: `zlib1g-dev`.

```bash
./scripts/build_xlog_linux.sh
./gradlew runKmp_xlogDebugExecutableLinuxX64
```

### Windows

```bash
.\gradlew runKmp_xlogDebugExecutableMingwX64
```

### macOS

```bash
./scripts/build_apple.sh
./gradlew runKmp_xlogDebugExecutableMacosX64
```

## Development

### Build MarsXLog

```bash
# run on macOS, with Android NDK android-ndk-r23d-canary,
# https://ci.android.com/builds/branches/aosp-ndk-release-r23/grid
./scripts/build_xlog_android.sh
# run on macOS
./scripts/build_xlog_apple.sh
# run on Linux
./scripts/build_xlog_linux.sh
# run on Windows
#.\scripts\build_xlog_windows.bat
# Windows xlog source is compiled by gradle,
# no need to build it manually.
```

### Test

Test shared code on Android unit test:

```bash
./gradlew :kmp-xlog:testDebugUnitTest
```

Then check reports in `kmp-xlog/build/reports/tests/testDebugUnitTest`.

### Publish

Maven central portal credentials and signing configs are set in `~/.gradle/gradle.properties`.

#### Maven publish

```bash
# on Windows: need manual release on website
.\script\publish_windows.bat
# on Linux: need manual release on website
./script/publish_linux.sh
# on macOS: need manual release on website
./script/publish_others.sh
```

#### iOS/macOS cocoapods

```bash
./build_apple.sh
./publish_apple_cocoapods.sh
```
