#!/bin/bash

set -e

./scripts/setup_apple_demo.sh

pushd example/iosApp
xcodebuild -workspace iosApp.xcworkspace \
    -scheme iosApp \
    -sdk iphonesimulator \
    -configuration Debug
popd
