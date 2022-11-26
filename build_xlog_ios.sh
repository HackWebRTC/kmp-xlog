#!/bin/bash

set -e

py=$(python -c 'import sys; print(".".join(map(str, sys.version_info[0:1])))')
if [[ "$py" != "2" ]]; then
  echo "Please use py2 env"
  exit
fi

# build mars xlog at first, with py2 env
pushd mars/mars
printf '2\n' | python build_ios.py
popd

rm -rf kmp-xlog/src/iosMain/frameworks/mars.xcframework
cp -r mars/mars/cmake_build/iOS/Darwin.out/mars.xcframework kmp-xlog/src/iosMain/frameworks/
