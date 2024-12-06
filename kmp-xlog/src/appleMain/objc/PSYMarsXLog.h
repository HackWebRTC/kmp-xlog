#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface PSYMarsXLog : NSObject

+ (void)initialize:(int)level namePrefix:(NSString*)namePrefix logToConsole:(bool)logToConsole;

+ (void)debug:(NSString*)tag content:(NSString*)content;

+ (void)info:(NSString*)tag content:(NSString*)content;

+ (void)error:(NSString*)tag content:(NSString*)content;

@end

NS_ASSUME_NONNULL_END
