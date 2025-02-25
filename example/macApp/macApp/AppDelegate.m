//
//  AppDelegate.m
//  macApp
//
//  Created by linker on 2025/2/24.
//

#import "AppDelegate.h"
#import "ViewController.h"

@interface AppDelegate ()


@end

@implementation AppDelegate

- (void)applicationDidFinishLaunching:(NSNotification *)aNotification {
    // Insert code here to initialize your application
    self.mainWindow =
        [[NSWindow alloc] initWithContentRect:NSMakeRect(10, 10, 300, 300)
                                    styleMask:NSWindowStyleMaskTitled | NSWindowStyleMaskClosable | NSWindowStyleMaskMiniaturizable | NSWindowStyleMaskResizable
                                      backing:NSBackingStoreBuffered
                                        defer:NO];
    self.mainWindow.title = @"kmp-webrtc";

    self.viewController = [[ViewController alloc] initWithNibName:nil bundle:nil];
    self.mainWindow.contentView = self.viewController.view;

    [self.mainWindow makeKeyAndOrderFront:nil];
}


- (void)applicationWillTerminate:(NSNotification *)aNotification {
    // Insert code here to tear down your application
}


- (BOOL)applicationSupportsSecureRestorableState:(NSApplication *)app {
    return YES;
}


@end
