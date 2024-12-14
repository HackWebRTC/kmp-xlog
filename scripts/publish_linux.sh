#!/bin/bash

./gradlew clean \
    publishLinuxX64PublicationToMavenCentralRepository \
    --no-configuration-cache

# login to https://central.sonatype.com/publishing/deployments ,
# and release linux module manually
