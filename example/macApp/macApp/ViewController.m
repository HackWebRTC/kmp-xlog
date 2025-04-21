//
//  ViewController.m
//  macApp
//
//  Created by linker on 2025/2/24.
//

#import "ViewController.h"

@import kmp_xlog;

@interface ViewController ()

@property (nonatomic, strong) NSTextField *label;

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];

    self.label = [[NSTextField alloc] initWithFrame:NSMakeRect(50, 50, 200, 30)];
    self.label.stringValue = [NSString stringWithFormat:@"Hello, macOS %@!",  [self getSystemVersion]];
    self.label.editable = NO;
    self.label.bezeled = NO;
    self.label.drawsBackground = NO;
    self.label.textColor = [NSColor redColor];
    self.label.font = [NSFont systemFontOfSize:14];

    [self.view addSubview:self.label];

    [Kmp_xlogLoggingKt initializeMarsXLogLevel:1 namePrefix:@"test"];
    [[Kmp_xlogLogging logging] infoTag:@"XXPXX" content:[NSString stringWithFormat:@"greeting from %@", [self getSystemVersion]]];
    [[Kmp_xlogLogging logging] errorTag:@"XXPXX" content:@"flush"];
}

- (NSString*)getSystemVersion {
    NSProcessInfo *processInfo = [NSProcessInfo processInfo];
    NSString *osVersion = processInfo.operatingSystemVersionString;
    return osVersion;
}

- (void)setRepresentedObject:(id)representedObject {
    [super setRepresentedObject:representedObject];

    // Update the view, if already loaded.
}


@end
