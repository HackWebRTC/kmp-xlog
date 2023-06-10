#import "PSYMarsXLog.h"

#import <sys/xattr.h>

#import <mars/xlog/xloggerbase.h>
#import <mars/xlog/xlogger.h>
#import <mars/xlog/appender.h>

static bool gInitialized = false;

@implementation PSYMarsXLog

+ (void)initialize:(int)level namePrefix:(NSString*)namePrefix logToConsole:(bool)logToConsole {
    if (gInitialized) {
        return;
    }

    NSString* logPath = [[NSSearchPathForDirectoriesInDomains(
        NSDocumentDirectory, NSUserDomainMask, YES) objectAtIndex:0]
        stringByAppendingString:@"/log"];

    // set do not backup for log path
    const char* attrName = "com.apple.MobileBackup";
    u_int8_t attrValue = 1;
    setxattr([logPath UTF8String], attrName, &attrValue, sizeof(attrValue), 0, 0);

    // init xlog
    xlogger_SetLevel((TLogLevel) level);
    mars::xlog::appender_set_console_log(logToConsole);

    mars::xlog::XLogConfig config;
    config.mode_ = mars::xlog::kAppenderAsync;
    config.logdir_ = [logPath UTF8String];
    config.nameprefix_ = [namePrefix UTF8String];
    mars::xlog::appender_open(config);

    gInitialized = true;
}

+ (void)debug:(NSString*)tag content:(NSString*)content {
    xlogger2(kLevelDebug, [tag UTF8String], "", "", 0, "%s # %s",
             [PSYMarsXLog threadName], [content UTF8String]);
}

+ (void)info:(NSString*)tag content:(NSString*)content {
    xlogger2(kLevelInfo, [tag UTF8String], "", "", 0, "%s # %s",
             [PSYMarsXLog threadName], [content UTF8String]);
}

+ (void)error:(NSString*)tag content:(NSString*)content {
    xlogger2(kLevelError, [tag UTF8String], "", "", 0, "%s # %s",
             [PSYMarsXLog threadName], [content UTF8String]);
    mars::xlog::appender_flush();
}

+ (const char*)threadName {
    NSString* name = [NSThread currentThread].name;
    if (![name isEqualToString:@""]) {
        return [name UTF8String];
    }
    if ([NSThread isMainThread]) {
        return "main";
    }
    return dispatch_queue_get_label(DISPATCH_CURRENT_QUEUE_LABEL);
}

@end
