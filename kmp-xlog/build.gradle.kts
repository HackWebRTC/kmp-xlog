@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kmp)
    alias(libs.plugins.vanniktech.mavenPublish)

    alias(libs.plugins.android.library)
    // alias will fail, see https://github.com/gradle/gradle/issues/20084
    id("org.jetbrains.kotlin.native.cocoapods")
}

version = Consts.releaseVersion
group = Consts.releaseGroup

kotlin {
    jvm()
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
        osx.deploymentTarget = "11.0"

        license = "{ :type => 'MIT', :file => 'LICENSE'}"
        source = "{ :http => '$homepage/releases/download/v$version/kmp_xlog.xcframework.zip' }"
        authors = "Piasy Xu"

        extraSpecAttributes["libraries"] = "'z'"
        extraSpecAttributes["framework"] = "'SystemConfiguration'"
        framework {
            export(project(":kmp-xlog-api"))
            baseName = "kmp_xlog"
            isStatic = true
        }
    }

    js {
        browser()
        nodejs()
    }
    wasmJs {
        browser()
        nodejs()
        d8()
    }
    wasmWasi {
        nodejs()
    }

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
            freeCompilerArgs.addAll(
                listOf(
                    "-include-binary",
                    "${project.projectDir}/src/linuxMain/libs/x64/libkmp_xlog.a"
                )
            )
        }
    }

    mingwX64 {
        compilations.getByName("main") {
            cinterops {
                val marsRoot = "${rootProject.projectDir}/mars/mars"
                val zlib by creating {
                    defFile("src/cppCommon/cinterop/zlib.def")
                    extraOpts(
                        "-Xsource-compiler-option", "-xc",
                        "-Xsource-compiler-option", "-I${marsRoot}/zstd/lib",
                        "-Xsource-compiler-option", "-I${marsRoot}/zstd/lib/common",
                        "-Xcompile-source", "${marsRoot}/comm/windows/zlib/adler32.c",
                        "-Xcompile-source", "${marsRoot}/comm/windows/zlib/compress.c",
                        "-Xcompile-source", "${marsRoot}/comm/windows/zlib/crc32.c",
                        "-Xcompile-source", "${marsRoot}/comm/windows/zlib/deflate.c",
                        "-Xcompile-source", "${marsRoot}/comm/windows/zlib/gzclose.c",
                        "-Xcompile-source", "${marsRoot}/comm/windows/zlib/gzlib.c",
                        "-Xcompile-source", "${marsRoot}/comm/windows/zlib/gzread.c",
                        "-Xcompile-source", "${marsRoot}/comm/windows/zlib/gzwrite.c",
                        "-Xcompile-source", "${marsRoot}/comm/windows/zlib/infback.c",
                        "-Xcompile-source", "${marsRoot}/comm/windows/zlib/inffast.c",
                        "-Xcompile-source", "${marsRoot}/comm/windows/zlib/inflate.c",
                        "-Xcompile-source", "${marsRoot}/comm/windows/zlib/inftrees.c",
                        "-Xcompile-source", "${marsRoot}/comm/windows/zlib/trees.c",
                        "-Xcompile-source", "${marsRoot}/comm/windows/zlib/uncompr.c",
                        "-Xcompile-source", "${marsRoot}/comm/windows/zlib/zutil.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/common/debug.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/common/entropy_common.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/common/fse_decompress.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/common/error_private.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/common/pool.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/common/threading.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/common/xxhash.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/common/zstd_common.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/compress/fse_compress.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/compress/hist.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/compress/huf_compress.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/compress/zstd_compress_literals.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/compress/zstd_compress_sequences.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/compress/zstd_compress.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/compress/zstd_double_fast.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/compress/zstd_fast.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/compress/zstd_lazy.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/compress/zstd_ldm.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/compress/zstd_opt.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/compress/zstdmt_compress.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/decompress/huf_decompress.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/decompress/zstd_ddict.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/decompress/zstd_decompress_block.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/decompress/zstd_decompress.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/deprecated/zbuff_common.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/dictBuilder/cover.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/dictBuilder/divsufsort.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/dictBuilder/fastcover.c",
                        "-Xcompile-source", "${marsRoot}/zstd/lib/dictBuilder/zdict.c",
                    )
                }
                val xlog by creating {
                    defFile("src/cppCommon/cinterop/xlog.def")
                    includeDirs {
                        allHeaders("${project.projectDir}/src/cppCommon/cpp")
                    }
                    extraOpts(
                        "-Xsource-compiler-option", "-I${rootProject.projectDir}/mars",
                        "-Xsource-compiler-option", "-I${marsRoot}",
                        "-Xsource-compiler-option", "-I${marsRoot}/comm",
                        "-Xsource-compiler-option", "-I${marsRoot}/comm/xlogger",
                        "-Xsource-compiler-option", "-I${marsRoot}/log",
                        "-Xsource-compiler-option", "-I${marsRoot}/zstd/lib",
                        "-Xsource-compiler-option", "-I${marsRoot}/zstd/lib/common",
                        "-Xsource-compiler-option", "-DBOOST_NO_EXCEPTIONS",
                        "-Xsource-compiler-option", "-DBOOST_USE_WINDOWS_H",
                        "-Xsource-compiler-option", "-DXLOG_NO_CRYPT",
                        "-Xsource-compiler-option", "-DKMP_XLOG_WINDOWS=1",
                        "-Xcompile-source", "${marsRoot}/boost/libs/filesystem/src/codecvt_error_category.cpp",
                        "-Xcompile-source", "${marsRoot}/boost/libs/filesystem/src/operations.cpp",
                        "-Xcompile-source", "${marsRoot}/boost/libs/filesystem/src/path.cpp",
                        "-Xcompile-source", "${marsRoot}/boost/libs/filesystem/src/path_traits.cpp",
                        "-Xcompile-source", "${marsRoot}/boost/libs/filesystem/src/utf8_codecvt_facet.cpp",
                        "-Xcompile-source", "${marsRoot}/boost/libs/filesystem/src/windows_file_codecvt.cpp",
                        "-Xcompile-source", "${marsRoot}/boost/libs/iostreams/src/mapped_file.cpp",
                        "-Xcompile-source", "${marsRoot}/boost/libs/system/src/error_code.cpp",
                        "-Xcompile-source", "${marsRoot}/boost/libs/thread/src/win32/thread.cpp",
                        "-Xcompile-source", "${marsRoot}/comm/assert/__assert.c",
                        "-Xcompile-source", "${marsRoot}/comm/windows/win32/platform_comm.cpp",
                        "-Xcompile-source", "${marsRoot}/comm/windows/xlogger_threadinfo.cpp",
                        "-Xcompile-source", "${marsRoot}/comm/xlogger/loginfo_extract.c",
                        "-Xcompile-source", "${marsRoot}/comm/xlogger/xloggerbase.c",
                        "-Xcompile-source", "${marsRoot}/comm/xlogger/xlogger.cc",
                        "-Xcompile-source", "${marsRoot}/comm/autobuffer.cc",
                        "-Xcompile-source", "${marsRoot}/comm/mmap_util.cc",
                        "-Xcompile-source", "${marsRoot}/comm/ptrbuffer.cc",
                        "-Xcompile-source", "${marsRoot}/comm/strutil.cc",
                        "-Xcompile-source", "${marsRoot}/comm/tickcount.cc",
                        "-Xcompile-source", "${marsRoot}/comm/time_utils.c",
                        "-Xcompile-source", "${marsRoot}/log/crypt/log_crypt.cc",
                        "-Xcompile-source", "${marsRoot}/log/src/appender.cc",
                        "-Xcompile-source", "${marsRoot}/log/src/formater.cc",
                        "-Xcompile-source", "${marsRoot}/log/src/log_base_buffer.cc",
                        "-Xcompile-source", "${marsRoot}/log/src/log_zlib_buffer.cc",
                        "-Xcompile-source", "${marsRoot}/log/src/log_zstd_buffer.cc",
                        "-Xcompile-source", "${marsRoot}/log/src/xlogger_interface.cc",
                        "-Xcompile-source", "src/cppCommon/cpp/MarsXLog.cpp",
                    )
                }
            }
        }
    }

    applyDefaultHierarchyTemplate()
    sourceSets {
        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }

        commonMain {
            dependencies {
                api(project(":kmp-xlog-api"))
            }
        }

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

        val cppCommon by creating {
            dependsOn(commonMain.get())
        }

        linuxX64Main {
            dependsOn(cppCommon)
        }
        mingwX64Main {
            dependsOn(cppCommon)
        }
    }
}

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

mavenPublishing {
    publishToMavenCentral()

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
