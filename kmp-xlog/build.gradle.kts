plugins {
  kotlin("multiplatform")
  kotlin("native.cocoapods")
  id("com.android.library")

  id("convention.publication")
}

kotlin {
  android() {
    publishLibraryVariants("release", "debug")
  }

  listOf(iosArm64(), iosSimulatorArm64(), iosX64()).forEach { t ->
    t.compilations.getByName("main") {
      val xlog by cinterops.creating {
        defFile("src/iosMain/cinterop/xlog.def")

        includeDirs("${project.projectDir}/src/iosMain/objc")
      }
    }

    // kmp doesn't support link to xcframework now,
    // but actually we don't need to link it when build static framework
    /*val libPath = hashMapOf(
        "iosArm64" to "os-arm64",
        "iosSimulatorArm64" to "sim-arm64",
        "iosX64" to "sim-x64",
    )
    t.binaries {
        all {
            linkerOpts(
                 "-F${project.projectDir}/src/iosMain/frameworks",
                 "-framework", "mars",
                 "-L${project.projectDir}/src/iosMain/libs/${libPath[t.name]}",
                 "-lxlog",
                 "-lz"
            )
        }
    }*/
  }

  cocoapods {
    summary = "KMP wrapper for tencent mars xlog"
    homepage = "https://github.com/HackWebRTC/kmp-xlog"
    version = Consts.releaseVersion
    ios.deploymentTarget = Consts.iosDeploymentTarget

    license = "{ :type => 'MIT', :file => 'LICENSE'}"
    source = "{ :http => '$homepage/releases/download/v$version/kmp_xlog.xcframework.zip' }"
    authors = "Piasy Xu"

    extraSpecAttributes["libraries"] = "'z'"
    extraSpecAttributes["framework"] = "'SystemConfiguration'"
    framework {
      baseName = "kmp_xlog"
      isStatic = true
      embedBitcode("disable")
    }
  }

  sourceSets {
    val commonMain by getting
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
        implementation("io.mockk:mockk-common:${Consts.mockk}")
      }
    }
    val androidMain by getting
    val androidTest by getting {
      dependencies {
        implementation("io.mockk:mockk:${Consts.mockk}")
      }
    }
    val iosArm64Main by getting
    val iosSimulatorArm64Main by getting
    val iosX64Main by getting
    val iosMain by creating {
      dependsOn(commonMain)
      iosArm64Main.dependsOn(this)
      iosSimulatorArm64Main.dependsOn(this)
      iosX64Main.dependsOn(this)
    }
    val iosArm64Test by getting
    val iosSimulatorArm64Test by getting
    val iosX64Test by getting
    val iosTest by creating {
      dependsOn(commonTest)
      iosArm64Test.dependsOn(this)
      iosSimulatorArm64Test.dependsOn(this)
      iosX64Test.dependsOn(this)
    }
  }
}

android {
  compileSdk = Consts.androidCompileSdk
  ndkVersion = Consts.androidNdk
  namespace = Consts.androidNS

  defaultConfig {
    minSdk = Consts.androidMinSdk
    targetSdk = Consts.androidTargetSdk

    sourceSets["main"].java.srcDir("src/androidMain/java")
    sourceSets["main"].jniLibs.srcDir("src/androidMain/jniLibs")
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
  kotlinOptions {
    jvmTarget = "11"
  }
}

group = Consts.releaseGroup
version = Consts.releaseVersion
