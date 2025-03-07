buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.application) apply false

    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kmp) apply false

    alias(libs.plugins.vanniktech.mavenPublish) apply false

    alias(libs.plugins.versions)
    alias(libs.plugins.versionUpdate)
}

versionCatalogUpdate {
    sortByKey = false
    keep {
        keepUnusedVersions = true
    }
}
