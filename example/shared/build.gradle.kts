plugins {
    alias(libs.plugins.kmp)

    alias(libs.plugins.android.library)
    // alias will fail, see https://github.com/gradle/gradle/issues/20084
    id("org.jetbrains.kotlin.native.cocoapods")
}

kotlin {
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()
    macosX64 {
        binaries {
            executable("kmp_xlog") {
                entryPoint = "com.piasy.kmp.xlog.example.main"
                linkerOpts = mutableListOf(
                    "-F${rootProject.projectDir}/kmp-xlog/build/cocoapods/publish/release/kmp_xlog.xcframework/macos-arm64_x86_64",
                    "-framework", "kmp_xlog", "-lz",
                )
            }
        }
    }

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = Consts.releaseVersion
        ios.deploymentTarget = libs.versions.iosDeploymentTarget.get()
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
    }

    js(IR) {
        browser {
        }
        binaries.executable()
    }

    listOf(linuxX64(), mingwX64()).forEach {
        it.binaries {
            executable("kmp_xlog") {
                entryPoint = "com.piasy.kmp.xlog.example.main"
            }
        }
    }

    applyDefaultHierarchyTemplate()
    sourceSets {
        commonMain {
            dependencies {
                //implementation("${Consts.releaseGroup}:${Consts.releaseName}:${Consts.releaseVersion}")
                implementation(project(":kmp-xlog"))
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
    namespace = "${Consts.androidNS}.android"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.jvm.get().toInt())
        targetCompatibility = JavaVersion.toVersion(libs.versions.jvm.get().toInt())
    }

    kotlin {
        jvmToolchain(libs.versions.jvm.get().toInt())
    }
}
