import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kmp)
    alias(libs.plugins.vanniktech.mavenPublish)
}

version = Consts.releaseVersion
group = Consts.releaseGroup

kotlin {
    jvm()

    iosArm64()
    iosSimulatorArm64()
    iosX64()
    macosArm64()
    macosX64()

    @OptIn(ExperimentalWasmDsl::class)
    listOf(js(IR), wasmJs()).forEach {
        it.browser {
        }
        it.binaries.executable()
    }

    linuxX64()
    mingwX64()

    applyDefaultHierarchyTemplate()
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlinx.datetime)
            }
        }
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    coordinates(group.toString(), "kmp-xlog-api", version.toString())

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
