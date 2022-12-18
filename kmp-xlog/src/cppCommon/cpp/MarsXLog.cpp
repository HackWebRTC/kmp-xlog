#include "MarsXLog.h"

#if defined(KMP_XLOG_WINDOWS)
#include <winsock2.h>
#include <libloaderapi.h>
#include <direct.h>
#elif defined(KMP_XLOG_LINUX)
#include <limits.h>
#include <sys/stat.h>
#include <unistd.h>
#endif

#include <exception>

#include <mars/comm/xlogger/xloggerbase.h>
#include <mars/comm/xlogger/xlogger.h>
#include <mars/log/appender.h>

namespace mars_boost {
void throw_exception( std::exception const & e ) {}
}

void MarsXLogInitialize(int level, const char* name_prefix, int log_to_console) {
#if defined(KMP_XLOG_WINDOWS)
    wchar_t module_buf[1024];
    GetModuleFileName(nullptr, module_buf, 1023);
    std::wstring module_str(module_buf);
    std::string app_path = std::string(module_str.begin(), module_str.end());
#elif defined(KMP_XLOG_LINUX)
    char result[PATH_MAX];
    ssize_t count = readlink("/proc/self/exe", result, PATH_MAX);
    std::string app_path = std::string(result, (count > 0) ? count : 0);
#else
#error "Unknown target"
#endif

    // Finds the last character equal to one of characters
    // in the given character sequence.
    std::string app_dir;
    std::size_t found = app_path.find_last_of("/\\");
    if (found != std::string::npos) {
        app_dir = app_path.substr(0, found);
    } else {
        app_dir = ".";
    }

    char abs_log_path[1024];
#if defined(KMP_XLOG_WINDOWS)
    snprintf(abs_log_path, sizeof(abs_log_path), "%s\\log", app_dir.c_str());
    _mkdir(abs_log_path);
#elif defined(KMP_XLOG_LINUX)
    snprintf(abs_log_path, sizeof(abs_log_path), "%s/log", app_dir.c_str());
    mkdir(abs_log_path, S_IRWXU | S_IRWXG | S_IROTH | S_IXOTH);
#else
#error "Unknown target"
#endif

    xlogger_SetLevel((TLogLevel) level);
    mars::xlog::appender_set_console_log(log_to_console);
    mars::xlog::XLogConfig config;
    config.mode_ = mars::xlog::kAppenderAsync;
    config.logdir_ = abs_log_path;
    config.nameprefix_ = name_prefix;
    mars::xlog::appender_open(config);
}

void MarsXLogDebug(const char* tag, const char* content) {
    xlogger2(kLevelDebug, tag, "", "", 0, "%s", content);
}

void MarsXLogInfo(const char* tag, const char* content) {
    xlogger2(kLevelInfo, tag, "", "", 0, "%s", content);
}

void MarsXLogError(const char* tag, const char* content) {
    xlogger2(kLevelError, tag, "", "", 0, "%s", content);
    mars::xlog::appender_flush();
}
