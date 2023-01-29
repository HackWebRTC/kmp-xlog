#!/usr/bin/env sh

set -e

py=$(python -c 'import sys; print(".".join(map(str, sys.version_info[0:1])))')
if [[ "$py" != "2" ]]; then
  echo "Please use py2 env"
  exit
fi

# build mars xlog at first, with py2 env
pushd mars/mars
rm -rf cmake_build/Android
printf '3\n' | env NDK_ROOT=/Users/piasy/tools/android-sdk/ndk/21.3.6528147 python build_android.py
popd

cp mars/mars/libraries/mars_xlog_sdk/libs/armeabi-v7a/libc++_shared.so \
  kmp-xlog/src/androidMain/jniLibs/armeabi-v7a/
cp mars/mars/libraries/mars_xlog_sdk/libs/arm64-v8a/libc++_shared.so \
  kmp-xlog/src/androidMain/jniLibs/arm64-v8a/
cp mars/mars/libraries/mars_xlog_sdk/libs/x86/libc++_shared.so \
  kmp-xlog/src/androidMain/jniLibs/x86/
cp mars/mars/libraries/mars_xlog_sdk/libs/x86_64/libc++_shared.so \
  kmp-xlog/src/androidMain/jniLibs/x86_64/

cp mars/mars/libraries/mars_xlog_sdk/libs/armeabi-v7a/libmarsxlog.so \
  kmp-xlog/src/androidMain/jniLibs/armeabi-v7a/
cp mars/mars/libraries/mars_xlog_sdk/libs/arm64-v8a/libmarsxlog.so \
  kmp-xlog/src/androidMain/jniLibs/arm64-v8a/
cp mars/mars/libraries/mars_xlog_sdk/libs/x86/libmarsxlog.so \
  kmp-xlog/src/androidMain/jniLibs/x86/
cp mars/mars/libraries/mars_xlog_sdk/libs/x86_64/libmarsxlog.so \
  kmp-xlog/src/androidMain/jniLibs/x86_64/

cp mars/mars/libraries/mars_xlog_sdk/obj/local/armeabi-v7a/libc++_shared.so \
  symbols/android/armeabi-v7a/
cp mars/mars/libraries/mars_xlog_sdk/obj/local/arm64-v8a/libc++_shared.so \
  symbols/android/arm64-v8a/
cp mars/mars/libraries/mars_xlog_sdk/obj/local/x86/libc++_shared.so \
  symbols/android/x86/
cp mars/mars/libraries/mars_xlog_sdk/obj/local/x86_64/libc++_shared.so \
  symbols/android/x86_64/

cp mars/mars/libraries/mars_xlog_sdk/obj/local/armeabi-v7a/libmarsxlog.so \
  symbols/android/armeabi-v7a/
cp mars/mars/libraries/mars_xlog_sdk/obj/local/arm64-v8a/libmarsxlog.so \
  symbols/android/arm64-v8a/
cp mars/mars/libraries/mars_xlog_sdk/obj/local/x86/libmarsxlog.so \
  symbols/android/x86/
cp mars/mars/libraries/mars_xlog_sdk/obj/local/x86_64/libmarsxlog.so \
  symbols/android/x86_64/

cp mars/mars/libraries/mars_xlog_sdk/src/main/java/com/tencent/mars/xlog/Log.java \
  mars/mars/libraries/mars_xlog_sdk/src/main/java/com/tencent/mars/xlog/Xlog.java \
  kmp-xlog/src/androidMain/java/com/tencent/mars/xlog/
