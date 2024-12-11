#!/bin/bash

set -e

# Run command below:
# pod trunk register 'xz4215@gmail.com' 'kmp_xlog'
# then open email to verify the register.
# Then create GitHub release with zip asset located in:
# kmp-xlog/build/cocoapods/publish/release/kmp_xlog.xcframework.zip

pushd kmp-xlog/build/cocoapods/publish/release
pod trunk push kmp_xlog.podspec --allow-warnings
popd
