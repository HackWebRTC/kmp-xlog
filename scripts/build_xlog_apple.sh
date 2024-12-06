#!/bin/bash

set -e

py=$(python -c 'import sys; print(".".join(map(str, sys.version_info[0:1])))')
if [[ "$py" != "2" ]]; then
  echo "Please use py2 env"
  exit
fi

# build mars xlog at first, with py2 env
pushd mars/mars

rm -rf cmake_build/iOS cmake_build/OSX cmake_build/mars.xcframework

printf '2\n' | python build_ios.py
printf '3\n' | python build_osx.py

xcodebuild -create-xcframework -framework cmake_build/iOS/Darwin.out/os/mars.framework \
  -framework cmake_build/iOS/Darwin.out/sim/mars.framework \
  -framework cmake_build/OSX/Darwin.out/mars.framework \
  -output cmake_build/mars.xcframework

popd

rm -rf kmp-xlog/src/appleMain/frameworks/mars.xcframework
cp -r mars/mars/cmake_build/mars.xcframework kmp-xlog/src/appleMain/frameworks/
