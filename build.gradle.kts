buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.library").version(Consts.agp).apply(false)
    id("com.android.application").version(Consts.agp).apply(false)

    kotlin("android").version(Consts.kotlin).apply(false)
    kotlin("multiplatform").version(Consts.kotlin).apply(false)

    id("io.codearte.nexus-staging").version("0.30.0")
}

nexusStaging {
    packageGroup = Consts.releaseGroup
    username = getPropString(project, "ossrhUsername")
    password = getPropString(project, "ossrhPassword")
}
