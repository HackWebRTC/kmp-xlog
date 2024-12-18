import com.vanniktech.maven.publish.SonatypeHost

plugins {
  alias(libs.plugins.kmp)
  alias(libs.plugins.vanniktech.mavenPublish)

  if (System.getProperty("os.name") == "Mac OS X") {
    alias(libs.plugins.android.library)
    // alias will fail, see https://github.com/gradle/gradle/issues/20084
    id("org.jetbrains.kotlin.native.cocoapods")
  }
}

version = Consts.releaseVersion
group = Consts.releaseGroup

val hostOs = System.getProperty("os.name")
val isMacOS = hostOs == "Mac OS X"
val isWindows = hostOs.startsWith("Windows")
val isLinux = hostOs == "Linux"

kotlin {
  if (isMacOS) {
    androidTarget {
      publishLibraryVariants("release")
    }

    listOf(iosArm64(), iosSimulatorArm64(), iosX64(), macosArm64(), macosX64()).forEach {
      it.compilations.getByName("main").cinterops {
        val xlog by creating {
          defFile("src/appleMain/cinterop/xlog.def")
          includeDirs {
            allHeaders("${project.projectDir}/src/appleMain/objc")
          }
        }
      }
    }

    cocoapods {
      summary = "KMP wrapper for tencent mars xlog"
      homepage = "https://github.com/HackWebRTC/kmp-xlog"
      version = Consts.releaseVersion
      ios.deploymentTarget = libs.versions.iosDeploymentTarget.get()

      license = "{ :type => 'MIT', :file => 'LICENSE'}"
      source = "{ :http => '$homepage/releases/download/v$version/kmp_xlog.xcframework.zip' }"
      authors = "Piasy Xu"

      extraSpecAttributes["libraries"] = "'z'"
      extraSpecAttributes["framework"] = "'SystemConfiguration'"
      framework {
        baseName = "kmp_xlog"
        isStatic = true
      }
    }

    js(IR) {
      browser {
      }
      binaries.executable()
    }

    // to include them in the KotlinMultiplatform module when publishing
    linuxX64 {}
    mingwX64 {}
  }

  if (isLinux) {
    linuxX64 {
      compilations.getByName("main") {
        cinterops {
          val xlog by creating {
            defFile("src/cppCommon/cinterop/xlog.def")
            includeDirs {
              allHeaders("${project.projectDir}/src/cppCommon/cpp")
            }
          }
        }
      }

      compilerOptions {
        freeCompilerArgs.addAll(listOf(
          "-include-binary",
          "${project.projectDir}/src/linuxMain/libs/x64/libkmp_xlog.a"
        ))
      }
    }
  }

  if (isWindows) {
    mingwX64 {
      compilations.getByName("main") {
        cinterops {
          val zlib by creating {
            defFile("src/cppCommon/cinterop/zlib.def")
            extraOpts(
              "-Xsource-compiler-option", "-xc",
              "-Xsource-compiler-option", "-I${rootProject.projectDir}/mars/mars/zstd/lib",
              "-Xsource-compiler-option", "-I${rootProject.projectDir}/mars/mars/zstd/lib/common",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/windows/zlib/adler32.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/windows/zlib/compress.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/windows/zlib/crc32.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/windows/zlib/deflate.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/windows/zlib/gzclose.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/windows/zlib/gzlib.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/windows/zlib/gzread.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/windows/zlib/gzwrite.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/windows/zlib/infback.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/windows/zlib/inffast.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/windows/zlib/inflate.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/windows/zlib/inftrees.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/windows/zlib/trees.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/windows/zlib/uncompr.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/windows/zlib/zutil.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/common/debug.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/common/entropy_common.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/common/fse_decompress.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/common/error_private.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/common/pool.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/common/threading.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/common/xxhash.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/common/zstd_common.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/compress/fse_compress.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/compress/hist.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/compress/huf_compress.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/compress/zstd_compress_literals.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/compress/zstd_compress_sequences.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/compress/zstd_compress.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/compress/zstd_double_fast.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/compress/zstd_fast.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/compress/zstd_lazy.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/compress/zstd_ldm.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/compress/zstd_opt.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/compress/zstdmt_compress.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/decompress/huf_decompress.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/decompress/zstd_ddict.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/decompress/zstd_decompress_block.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/decompress/zstd_decompress.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/deprecated/zbuff_common.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/dictBuilder/cover.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/dictBuilder/divsufsort.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/dictBuilder/fastcover.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/zstd/lib/dictBuilder/zdict.c",
            )
          }
          val xlog by creating {
            defFile("src/cppCommon/cinterop/xlog.def")
            includeDirs {
              allHeaders("${project.projectDir}/src/cppCommon/cpp")
            }
            extraOpts(
              "-Xsource-compiler-option", "-I${rootProject.projectDir}/mars",
              "-Xsource-compiler-option", "-I${rootProject.projectDir}/mars/mars",
              "-Xsource-compiler-option", "-I${rootProject.projectDir}/mars/mars/comm",
              "-Xsource-compiler-option", "-I${rootProject.projectDir}/mars/mars/comm/xlogger",
              "-Xsource-compiler-option", "-I${rootProject.projectDir}/mars/mars/log",
              "-Xsource-compiler-option", "-I${rootProject.projectDir}/mars/mars/zstd/lib",
              "-Xsource-compiler-option", "-I${rootProject.projectDir}/mars/mars/zstd/lib/common",
              "-Xsource-compiler-option", "-DBOOST_NO_EXCEPTIONS",
              "-Xsource-compiler-option", "-DBOOST_USE_WINDOWS_H",
              "-Xsource-compiler-option", "-DXLOG_NO_CRYPT",
              "-Xsource-compiler-option", "-DKMP_XLOG_WINDOWS=1",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/boost/libs/filesystem/src/codecvt_error_category.cpp",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/boost/libs/filesystem/src/operations.cpp",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/boost/libs/filesystem/src/path.cpp",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/boost/libs/filesystem/src/path_traits.cpp",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/boost/libs/filesystem/src/utf8_codecvt_facet.cpp",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/boost/libs/filesystem/src/windows_file_codecvt.cpp",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/boost/libs/iostreams/src/mapped_file.cpp",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/boost/libs/system/src/error_code.cpp",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/boost/libs/thread/src/win32/thread.cpp",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/assert/__assert.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/windows/win32/platform_comm.cpp",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/windows/xlogger_threadinfo.cpp",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/xlogger/loginfo_extract.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/xlogger/xloggerbase.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/xlogger/xlogger.cc",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/autobuffer.cc",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/mmap_util.cc",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/ptrbuffer.cc",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/strutil.cc",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/tickcount.cc",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/comm/time_utils.c",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/log/crypt/log_crypt.cc",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/log/src/appender.cc",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/log/src/formater.cc",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/log/src/log_base_buffer.cc",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/log/src/log_zlib_buffer.cc",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/log/src/log_zstd_buffer.cc",
              "-Xcompile-source", "${rootProject.projectDir}/mars/mars/log/src/xlogger_interface.cc",
              "-Xcompile-source", "src/cppCommon/cpp/MarsXLog.cpp",
            )
          }
        }
      }
    }
  }

  applyDefaultHierarchyTemplate()
  sourceSets {
    all {
      languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
    }

    if (isMacOS) {
      androidUnitTest {
        dependencies {
          implementation(libs.kotlin.test)
          implementation(libs.kotlin.test.junit)
          implementation(libs.mockk)
        }
      }

      jsMain {
        dependencies {
          implementation(libs.kotlin.stdlib.js)
        }
      }
    }

    if (isLinux || isWindows) {
      val cppCommon by creating {
        dependsOn(commonMain.get())
      }

      if (isLinux) {
        linuxX64Main {
          dependsOn(cppCommon)
        }
      }
      if (isWindows) {
        mingwX64Main {
          dependsOn(cppCommon)
        }
      }
    }
  }
}

if (isMacOS) {
  android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    ndkVersion = libs.versions.ndk.get()
    namespace = Consts.androidNS

    defaultConfig {
      minSdk = libs.versions.minSdk.get().toInt()

      sourceSets.getByName("main") {
        java.srcDir("src/androidMain/java")
        jniLibs.srcDir("src/androidMain/jniLibs")
      }
    }

    compileOptions {
      sourceCompatibility = JavaVersion.toVersion(libs.versions.jvm.get().toInt())
      targetCompatibility = JavaVersion.toVersion(libs.versions.jvm.get().toInt())
    }

    kotlin {
      jvmToolchain(libs.versions.jvm.get().toInt())
    }
  }
}

mavenPublishing {
  publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

  signAllPublications()

  coordinates(group.toString(), Consts.releaseName, version.toString())

  pom {
    name = "kmp-xlog"
    description = "KMP wrapper for tencent mars xlog."
    inceptionYear = "2022"
    url = "https://github.com/HackWebRTC/kmp-xlog"
    licenses {
      license {
        name = "MIT"
        url = "https://opensource.org/licenses/MIT"
        distribution = "https://opensource.org/licenses/MIT"
      }
    }
    developers {
      developer {
        id = "Piasy"
        name = "Piasy Xu"
        url = "xz4215@gmail.com"
      }
    }
    scm {
      url = "https://github.com/HackWebRTC/kmp-xlog"
      connection = "scm:git:git://github.com/HackWebRTC/kmp-xlog.git"
      developerConnection = "scm:git:git://github.com/HackWebRTC/kmp-xlog.git"
    }
  }
}
