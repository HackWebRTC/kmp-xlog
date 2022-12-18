#pragma once

#if __cplusplus
extern "C" {
#endif

void MarsXLogInitialize(int level, const char* name_prefix, int log_to_console);
void MarsXLogDebug(const char* tag, const char* content);
void MarsXLogInfo(const char* tag, const char* content);
void MarsXLogError(const char* tag, const char* content);

#if __cplusplus
}
#endif
