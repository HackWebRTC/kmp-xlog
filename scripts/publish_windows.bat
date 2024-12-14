@echo off

.\gradlew clean publishMingwX64PublicationToMavenCentralRepository --no-configuration-cache

:: login to https://central.sonatype.com/publishing/deployments ,
:: and release windows module manually
