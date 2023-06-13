#!/bin/bash

set -e

# pod trunk register 'xz4215@gmail.com' 'kmp_xlog'
# then open email to verify the register

pushd kmp-xlog/build/cocoapods/publish/release
pod trunk push kmp_xlog.podspec --allow-warnings
popd
