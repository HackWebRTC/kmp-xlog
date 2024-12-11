#!/bin/bash

./gradlew clean publishLinuxX64PublicationToMavenCentralRepository

# login to https://central.sonatype.com/publishing/deployments ,
# and release linux module manually
