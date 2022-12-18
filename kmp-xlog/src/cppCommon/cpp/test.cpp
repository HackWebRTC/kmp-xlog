#include "MarsXLog.h"

int main() {
    MarsXLogInitialize(1, "kmp_xlog", 1);
    MarsXLogInfo("tag", "content");
    return 0;
}
