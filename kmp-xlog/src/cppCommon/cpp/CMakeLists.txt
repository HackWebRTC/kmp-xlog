cmake_minimum_required(VERSION 3.4.1)
project("kmp_xlog")

if (MSVC)
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} /std:c++14 /O2")
else ()
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++14 -O2 -fPIC -Wall -g")
    set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -fPIC -Wall -g")
endif ()

set(CWD ${CMAKE_CURRENT_LIST_DIR})
include_directories(${CWD}/../../../../mars)

add_subdirectory(${CWD}/../../../../mars/mars mars)

file(GLOB sources
        MarsXLog.cpp
        )

if (MSVC)
    add_definitions(-DKMP_XLOG_WINDOWS=1)
elseif ("${CMAKE_SYSTEM_NAME}" STREQUAL "Linux")
    add_definitions(-DKMP_XLOG_LINUX=1)
endif ()

add_library(${PROJECT_NAME} STATIC ${sources})

add_executable(test test.cpp)
target_link_libraries(test
        ${PROJECT_NAME}
        xlog
        comm
        mars-boost
        libzstd_static
        pthread
        z
        )
