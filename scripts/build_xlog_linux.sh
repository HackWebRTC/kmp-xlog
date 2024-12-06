#!/bin/bash

set -e

pushd kmp-xlog/src/cppCommon/cpp

rm -rf build
cmake -Bbuild -DCMAKE_BUILD_TYPE=Release
cmake --build build --config Release --target test

mkdir -p build/tmp/comm \
  build/tmp/xlog \
  build/tmp/boost \
  build/tmp/zstd \
  build/tmp/kmp_xlog

cp build/mars/comm/libcomm.a build/tmp/comm/
pushd build/tmp/comm/
ar -x libcomm.a
popd

cp build/mars/xlog/libxlog.a build/tmp/xlog/
pushd build/tmp/xlog/
ar -x libxlog.a
popd

cp build/mars/boost/libmars-boost.a build/tmp/boost/
pushd build/tmp/boost/
ar -x libmars-boost.a
popd

cp build/mars/zstd/libzstd.a build/tmp/zstd/
pushd build/tmp/zstd/
ar -x libzstd.a
popd

cp build/libkmp_xlog.a build/tmp/kmp_xlog/
pushd build/tmp/kmp_xlog/
ar -x libkmp_xlog.a
popd

pushd build/tmp
ar -qc libkmp_xlog.a comm/*.o xlog/*.o boost/*.o zstd/*.o kmp_xlog/*.o
popd

popd

cp kmp-xlog/src/cppCommon/cpp/build/tmp/libkmp_xlog.a \
  kmp-xlog/src/linuxMain/libs/x64/
