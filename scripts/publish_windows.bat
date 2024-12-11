@echo off

.\gradlew clean publishMingwX64PublicationToMavenCentralRepository

:: login to https://central.sonatype.com/publishing/deployments ,
:: and release windows module manually
