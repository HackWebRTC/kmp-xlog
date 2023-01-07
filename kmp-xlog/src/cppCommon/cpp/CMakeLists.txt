cmake_minimum_required(VERSION 3.4.1)
project("kmp_xlog")

set(CMAKE_POSITION_INDEPENDENT_CODE ON)
if (MSVC)
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} /std:c++14 /O2")
else ()
    set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++14 -O2 -Wall -g")
    set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Wall -g")
endif ()

set(CWD ${CMAKE_CURRENT_LIST_DIR})

include_directories(${CWD}/../../../../mars)
file(GLOB sources
        MarsXLog.cpp
        )

add_library(${PROJECT_NAME} STATIC ${sources})

if (MSVC)
    # set DebugInformationFormat to ProgramDatabase
    add_definitions(/Zi)
    add_definitions(-D_NO_CRT_STDIO_INLINE)
    add_definitions(-DKMP_XLOG_WINDOWS=1)

    set(CompilerFlags
        CMAKE_CXX_FLAGS
        CMAKE_CXX_FLAGS_DEBUG
        CMAKE_CXX_FLAGS_RELEASE
        CMAKE_CXX_FLAGS_RELWITHDEBINFO
        CMAKE_C_FLAGS
        CMAKE_C_FLAGS_DEBUG
        CMAKE_C_FLAGS_RELEASE
        CMAKE_C_FLAGS_RELWITHDEBINFO)
        
    foreach(CompilerFlag ${CompilerFlags})
        string(REPLACE "/MD" "/MT" ${CompilerFlag} "${${CompilerFlag}}")
    endforeach()

    include_directories(${CWD}/../../../../mars/mars/comm/windows)
elseif ("${CMAKE_SYSTEM_NAME}" STREQUAL "Linux")
    add_definitions(-DKMP_XLOG_LINUX=1)

    add_subdirectory(${CWD}/../../../../mars/mars mars)

    # add_library(${PROJECT_NAME} SHARED ${sources})
    # target_link_options(${PROJECT_NAME} PRIVATE "LINKER:-z,defs")
    # target_link_libraries(${PROJECT_NAME}
    #         xlog
    #         comm
    #         mars-boost
    #         libzstd_static
    #         pthread
    #         z
    #         )

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
endif ()
