@echo off

cd kmp-xlog\src\cppCommon\cpp

mkdir build\xlog build\kmp_xlog

cd build\xlog
cmake ..\..\..\..\..\..\mars\mars -A x64 -G "Visual Studio 16 2019" -T v142
cmake --build . --target install --config Release

cd ..\kmp_xlog
cmake ..\.. -A x64 -G "Visual Studio 16 2019" -T v142
cmake --build . --target kmp_xlog --config Release

cd ..
lib /OUT:kmp_xlog.lib xlog\Windows.out\comm.lib xlog\Windows.out\mars-boost.lib xlog\Windows.out\xlog.lib kmp_xlog\Release\kmp_xlog.lib

copy kmp_xlog.lib ..\..\..\mingwMain\libs\x86\
copy xlog\comm\Release\comm.pdb ..\..\..\..\..\symbols\windows\x86\
copy xlog\boost\Release\mars-boost.pdb ..\..\..\..\..\symbols\windows\x86\
copy xlog\xlog\Release\xlog.pdb ..\..\..\..\..\symbols\windows\x86\
copy kmp_xlog\Release\kmp_xlog.pdb ..\..\..\..\..\symbols\windows\x86\

cd ..\..\..\..\..
