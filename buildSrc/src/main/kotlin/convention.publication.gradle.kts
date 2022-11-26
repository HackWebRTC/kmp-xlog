// credit: https://dev.to/kotlin/how-to-build-and-publish-a-kotlin-multiplatform-library-going-public-4a8k
import java.util.Properties

plugins {
  `maven-publish`
  signing
}

// Stub secrets to let the project sync and build without the publication values set up
ext["signing.keyId"] = null
ext["signing.password"] = null
ext["signing.secretKeyRingFile"] = null
ext["ossrhUsername"] = null
ext["ossrhPassword"] = null

// Grabbing secrets from local.properties file or from environment variables, which could be used on CI
val secretPropsFile = project.rootProject.file("local.properties")
if (secretPropsFile.exists()) {
  secretPropsFile.reader().use {
    Properties().apply {
      load(it)
    }
  }.onEach { (name, value) ->
    ext[name.toString()] = value
  }
} else {
  ext["signing.keyId"] = System.getenv("SIGNING_KEY_ID")
  ext["signing.password"] = System.getenv("SIGNING_PASSWORD")
  ext["signing.secretKeyRingFile"] = System.getenv("SIGNING_SECRET_KEY_RING_FILE")
  ext["ossrhUsername"] = System.getenv("OSSRH_USERNAME")
  ext["ossrhPassword"] = System.getenv("OSSRH_PASSWORD")
}

ext["artifact.name"] = null
ext["artifact.desc"] = null
ext["artifact.url"] = null
val propsFile = project.rootProject.file("gradle.properties")
if (propsFile.exists()) {
  propsFile.reader().use {
    Properties().apply {
      load(it)
    }
  }.onEach { (name, value) ->
    ext[name.toString()] = value
  }
}

val javadocJar by tasks.registering(Jar::class) {
  archiveClassifier.set("javadoc")
}

fun getExtraString(name: String) = ext[name]?.toString()

publishing {
  // Configure maven central repository
  repositories {
    maven {
      name = "sonatype"
      setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
      credentials {
        username = getExtraString("ossrhUsername")
        password = getExtraString("ossrhPassword")
      }
    }
  }

  // Configure all publications
  publications.withType<MavenPublication> {
    // Stub javadoc.jar artifact
    artifact(javadocJar.get())

    // Provide artifacts information requited by Maven Central
    pom {
      name.set(getExtraString("artifact.name"))
      description.set(getExtraString("artifact.desc"))
      url.set(getExtraString("artifact.url"))

      licenses {
        license {
          name.set("MIT")
          url.set("https://opensource.org/licenses/MIT")
        }
      }
      developers {
        developer {
          id.set("Piasy")
          name.set("Piasy Xu")
          email.set("xz4215@gmail.com")
        }
      }
      scm {
        url.set(getExtraString("artifact.url"))
      }
    }
  }
}

// Signing artifacts. Signing.* extra properties values will be used
signing {
  sign(publishing.publications)
}
