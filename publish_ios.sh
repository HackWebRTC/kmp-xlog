#!/bin/bash

set -e

pushd kmp-xlog/build/cocoapods/publish/release
pod trunk push kmp_xlog.podspec --allow-warnings
popd
