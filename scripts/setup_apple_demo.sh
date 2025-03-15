#!/bin/bash

set -e

./gradlew :example:shared:podspec :example:shared:generateDummyFramework

pushd example/iosApp
xcodegen
pod install
popd

pushd example/macApp
xcodegen
pod install
popd
