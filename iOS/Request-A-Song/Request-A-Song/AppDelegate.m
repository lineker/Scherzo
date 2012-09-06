//
//  AppDelegate.m
//  Request-A-Song
//
//  Created by Lineker Tomazeli on 12-01-05.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "AppDelegate.h"

#import "Song.h"
#import "NowPlayingViewController.h"
#import "LibraryViewController.h"

@implementation AppDelegate{
    NSMutableArray *songs;
}

@synthesize window = _window;

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    /*songs = [NSMutableArray arrayWithCapacity:5];
    Song *Song1 = [[Song alloc] init];
    Song *Song2 = [[Song alloc] init];
    Song *Song3 = [[Song alloc] init];
    Song *Song4 = [[Song alloc] init];
    Song *Song5 = [[Song alloc] init];
    
    Song1.SongName = @"Graduation";
    Song2.SongName = @"Dark and Twisted Fantasy";
    Song3.SongName = @"Torches";
    Song4.SongName = @"Nothing But The Beat";
    Song5.SongName = @"Angles";
    
    Song1.artist = @"Kanye West";
    Song2.artist = @"Kanye West";
    Song3.artist = @"Foster The People";
    Song4.artist = @"David Guetta";
    Song5.artist = @"The Strokes";
    
    Song1.songId = 1;
    Song2.songId = 2;
    Song3.songId = 3;
    Song4.songId = 4;
    Song5.songId = 5;
    
    [songs addObject:Song1];
    [songs addObject:Song2];
    [songs addObject:Song3];
    [songs addObject:Song4];
    [songs addObject:Song5];*/
    
    // Register the preference defaults early.
    NSDictionary *appDefaults = [NSDictionary dictionaryWithObjectsAndKeys:@"localhost", @"ServerAddress",@"2011", @"Port", nil];
    [[NSUserDefaults standardUserDefaults] registerDefaults:appDefaults];
    
    
    UITabBarController *tabBarController = 
    (UITabBarController *)self.window.rootViewController;
	
    UINavigationController *navigationController = 
    [[tabBarController viewControllers] objectAtIndex:0];
	
    NowPlayingViewController *nowPlayingViewController = 
    [[navigationController viewControllers] objectAtIndex:0];
	//nowPlayingViewController.songs = songs;
    //[nowPlayingViewController getPlayingList];
    
    UINavigationController *libNavController = 
    [[tabBarController viewControllers] objectAtIndex:1];
	
    LibraryViewController *libraryViewController = 
    [[libNavController viewControllers] objectAtIndex:0];
	//[libraryViewController getPlaylist];
    
    NSLog(@"%d",[[tabBarController viewControllers] count]);
    
    // Override point for customization after application launch.
    return YES;
}
							
- (void)applicationWillResignActive:(UIApplication *)application
{
    /*
     Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
     Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
     */
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    /*
     Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
     If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
     */
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    /*
     Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
     */
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    /*
     Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
     */
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    /*
     Called when the application is about to terminate.
     Save data if appropriate.
     See also applicationDidEnterBackground:.
     */
}

@end
