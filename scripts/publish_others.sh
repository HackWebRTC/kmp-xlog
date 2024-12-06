#!/bin/bash

./gradlew publishAllPublicationsToSonatypeRepository closeAndReleaseRepository

# login to https://s01.oss.sonatype.org/ ,
# find repository in the 'Staging repositories' section,
# close it and release it
