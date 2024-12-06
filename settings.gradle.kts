pluginManagement {
  repositories {
    google()
    gradlePluginPortal()
    mavenCentral()
  }
}

dependencyResolutionManagement {
  repositories {
    google()
    mavenCentral()
    mavenLocal()
  }
}

rootProject.name = "kmp-xlog"
include(":kmp-xlog")

include(":example:shared")
if (System.getProperty("os.name") == "Mac OS X") {
  include(":example:androidApp")
}
