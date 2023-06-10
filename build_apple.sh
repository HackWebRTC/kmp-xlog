#!/bin/bash

set -e

pushd kmp-xlog/src/iosMain

rm -rf build libs kmp_xlog.xcodeproj
mkdir -p libs/os-arm64 libs/sim-arm64 libs/sim-x64

xcodegen

xcodebuild clean
xcodebuild -quiet -arch arm64
mv build/libkmp_xlog.a libs/os-arm64/libkmp_xlog.a

xcodebuild clean
xcodebuild -quiet -arch x86_64 -sdk iphonesimulator
mv build/libkmp_xlog.a libs/sim-x64/libkmp_xlog.a

xcodebuild clean
xcodebuild -quiet -arch arm64 -sdk iphonesimulator
mv build/libkmp_xlog.a libs/sim-arm64/libkmp_xlog.a

rm -rf build

popd

pushd kmp-xlog/src/macosMain

rm -rf build libs kmp_xlog.xcodeproj
mkdir -p libs/arm64 libs/x64

xcodegen

xcodebuild clean
xcodebuild -quiet -arch arm64
mv build/libkmp_xlog.a libs/arm64/libkmp_xlog.a

xcodebuild clean
xcodebuild -quiet -arch x86_64
mv build/libkmp_xlog.a libs/x64/libkmp_xlog.a

rm -rf build

popd

./gradlew clean :kmp-xlog:podPublishReleaseXCFramework

pushd kmp-xlog

libtool -static -no_warning_for_no_symbols \
  -o build/kmp_xlog-os-arm64 \
  src/objcCommon/frameworks/mars.xcframework/ios-arm64/mars.framework/mars \
  src/iosMain/libs/os-arm64/libkmp_xlog.a \
  build/cocoapods/publish/release/kmp_xlog.xcframework/ios-arm64/kmp_xlog.framework/kmp_xlog

cp build/kmp_xlog-os-arm64 \
  build/cocoapods/publish/release/kmp_xlog.xcframework/ios-arm64/kmp_xlog.framework/kmp_xlog

lipo -create src/iosMain/libs/sim-arm64/libkmp_xlog.a \
  src/iosMain/libs/sim-x64/libkmp_xlog.a \
  -output build/libkmp_xlog-sim-arm64-x64.a

libtool -static -no_warning_for_no_symbols \
  -o build/kmp_xlog-sim-arm64-x64 \
  src/objcCommon/frameworks/mars.xcframework/ios-arm64_x86_64-simulator/mars.framework/mars \
  build/libkmp_xlog-sim-arm64-x64.a \
  build/cocoapods/publish/release/kmp_xlog.xcframework/ios-arm64_x86_64-simulator/kmp_xlog.framework/kmp_xlog

cp build/kmp_xlog-sim-arm64-x64 \
  build/cocoapods/publish/release/kmp_xlog.xcframework/ios-arm64_x86_64-simulator/kmp_xlog.framework/kmp_xlog

lipo -create src/macosMain/libs/arm64/libkmp_xlog.a \
  src/macosMain/libs/x64/libkmp_xlog.a \
  -output build/libkmp_xlog-macos-arm64-x64.a

libtool -static -no_warning_for_no_symbols \
  -o build/kmp_xlog-macos-arm64-x64 \
  src/objcCommon/frameworks/mars.xcframework/macos-arm64_x86_64/mars.framework/mars \
  build/libkmp_xlog-macos-arm64-x64.a \
  build/cocoapods/publish/release/kmp_xlog.xcframework/macos-arm64_x86_64/kmp_xlog.framework/kmp_xlog

cp build/kmp_xlog-macos-arm64-x64 \
  build/cocoapods/publish/release/kmp_xlog.xcframework/macos-arm64_x86_64/kmp_xlog.framework/kmp_xlog

popd

cp LICENSE kmp-xlog/build/cocoapods/publish/release/
