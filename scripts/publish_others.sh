#!/bin/bash

./gradlew clean publishAndReleaseToMavenCentral --no-configuration-cache

# login to https://central.sonatype.com/publishing/deployments ,
# and check the status
