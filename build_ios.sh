#!/bin/bash

set -e

pushd kmp-xlog/src/iosMain
rm -rf build xlog.xcodeproj

xcodegen

xcodebuild clean
xcodebuild -quiet -arch arm64
mv build/libxlog.a libs/os-arm64/libxlog.a

xcodebuild clean
xcodebuild -quiet -arch x86_64 -sdk iphonesimulator
mv build/libxlog.a libs/sim-x64/libxlog.a

xcodebuild clean
xcodebuild -quiet -arch arm64 -sdk iphonesimulator
mv build/libxlog.a libs/sim-arm64/libxlog.a

rm -rf build

popd

./gradlew clean :kmp-xlog:podPublishReleaseXCFramework

pushd kmp-xlog

libtool -static -no_warning_for_no_symbols \
  -o build/kmp_xlog-os-arm64 \
  src/iosMain/frameworks/mars.xcframework/ios-arm64/mars.framework/mars \
  src/iosMain/libs/os-arm64/libxlog.a \
  build/cocoapods/publish/release/kmp_xlog.xcframework/ios-arm64/kmp_xlog.framework/kmp_xlog

lipo -create src/iosMain/libs/sim-arm64/libxlog.a \
  src/iosMain/libs/sim-x64/libxlog.a \
  -output build/libxlog-sim-arm64-x64.a

libtool -static -no_warning_for_no_symbols \
  -o build/kmp_xlog-sim-arm64-x64 \
  src/iosMain/frameworks/mars.xcframework/ios-arm64_x86_64-simulator/mars.framework/mars \
  build/libxlog-sim-arm64-x64.a \
  build/cocoapods/publish/release/kmp_xlog.xcframework/ios-arm64_x86_64-simulator/kmp_xlog.framework/kmp_xlog

cp build/kmp_xlog-os-arm64 \
  build/cocoapods/publish/release/kmp_xlog.xcframework/ios-arm64/kmp_xlog.framework/kmp_xlog

cp build/kmp_xlog-sim-arm64-x64 \
  build/cocoapods/publish/release/kmp_xlog.xcframework/ios-arm64_x86_64-simulator/kmp_xlog.framework/kmp_xlog

popd

cp LICENSE kmp-xlog/build/cocoapods/publish/release/
