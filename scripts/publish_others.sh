#!/bin/bash

./gradlew clean \
    publishKotlinMultiplatformPublicationToMavenCentralRepository \
    publishAndroidReleasePublicationToMavenCentralRepository \
    publishIosArm64PublicationToMavenCentralRepository \
    publishIosSimulatorArm64PublicationToMavenCentralRepository \
    publishIosX64PublicationToMavenCentralRepository \
    publishMacosArm64PublicationToMavenCentralRepository \
    publishMacosX64PublicationToMavenCentralRepository \
    publishJsPublicationToMavenCentralRepository \
    publishJvmPublicationToMavenCentralRepository \
    --no-configuration-cache

# login to https://central.sonatype.com/publishing/deployments ,
# and release them manually
