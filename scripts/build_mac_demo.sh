#!/bin/bash

set -e

./scripts/setup_apple_demo.sh

pushd example/macApp
xcodebuild -workspace macApp.xcworkspace \
    -scheme macApp \
    -sdk macosx \
    -configuration Debug ARCHS="x86_64" ONLY_ACTIVE_ARCH=NO
popd
