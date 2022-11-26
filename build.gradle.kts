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
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
