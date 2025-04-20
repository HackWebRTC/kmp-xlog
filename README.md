# kmp-xlog

KMP wrapper for [tencent mars xlog](https://github.com/Tencent/mars).

![Maven Central Version](https://img.shields.io/maven-central/v/com.piasy/kmp-xlog) ![Main branch status](https://github.com/HackWebRTC/kmp-xlog/actions/workflows/ci.yaml/badge.svg?branch=main)

## Supported platforms

|      Platform      | 🛠Builds🛠 + 🔬Tests🔬 |
| :----------------: | :------------------: |
|      `JVM` 17      |          🚀          |
| `JS`     (Chrome)  |          🚀          |
|     `Android`      |          🚀          |
|       `iOS`        |          🚀          |
|      `macOS`       |          🚀          |
|   `Windows X64`    |          🚀          |
|    `Linux X64`     |          🚀          |

## Dependency

For Android/JS/Linux/Windows, you only need to add gradle dependency, for iOS/macOS you need to add an extra cocoapods dependency.

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
)

// iOS/macOS/Linux/Windows initialize
fun initializeMarsXLog(
  level: Int,
  namePrefix: String,
)

// JS initialize
fun initializeConsoleLog(debugLog: Boolean)

// logging
object Logging {
  // if debug log is enabled (level is LEVEL_DEBUG)
  fun debug(): Boolean

  // compute log content lazily, only when debug log is enabled
  fun debug(tag: String, block: () -> String)
  
  fun debug(tag: String, content: String)

  fun info(tag: String, content: String)

  fun error(tag: String, content: String)
}
```

## Env Setup

You will need cocoapods. You can use [RVM](https://rvm.io/) to manage your ruby version, and install gems.

You need to use homebrew to install the following tools:

```bash
brew install xcodegen
# if you have installed them earlier, you need to remove them at first,
# or run brew link --overwrite xcodegen
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

This is because `iosApp/Podfile` depends on the podspec in `../../kmp-xlog/build/cocoapods/publish/release`, so please run `./scripts/build_apple.sh` and open/sync again.

## Example

### Android

Open the project (the repo root dir) in Android studio, and run the example.androidApp target.

### iOS

```bash
cd example/iosApp
xcodegen
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

Test in command line:

```bash
./scripts/build_apple.sh
./gradlew runKmp_xlogDebugExecutableMacosX64
```

Test in GUI:

```bash
cd example/macApp
xcodegen
pod install
# open macApp.xcworkspace in Xcode, and run it.
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
.\scripts\publish_windows.bat
# on Linux: need manual release on website
./scripts/publish_linux.sh
# on macOS: need manual release on website
./scripts/publish_others.sh
```

#### iOS/macOS cocoapods

```bash
./scripts/build_apple.sh
./scripts/publish_apple_cocoapods.sh
```
