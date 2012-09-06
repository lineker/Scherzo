//
//  LibraryViewController.h
//  Request-A-Song
//
//  Created by Lineker Tomazeli on 12-01-05.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Song.h"

@class SBJsonParser;
@class SBJsonWriter;

@interface LibraryViewController : UITableViewController <NSStreamDelegate, UITableViewDelegate, UITableViewDataSource,UIAlertViewDelegate>
{
    NSInputStream	*inputStream;
	NSOutputStream	*outputStream;
    NSArray *songs;
    NSMutableString* output;
    BOOL messageShowed;
    
    SBJsonParser *_parser;
    SBJsonWriter *_writer;
    Song *selectedSong;
    
}
-(IBAction) refresh:(id) sender;

@property (nonatomic,assign) BOOL messageShowed;
@property (nonatomic, retain) NSMutableString *output;

@property (nonatomic, retain) NSInputStream *inputStream;
@property (nonatomic, retain) NSOutputStream *outputStream;

@property (nonatomic, strong) NSArray *songs;

- (void) initNetworkCommunication;
- (void)getPlaylist;
-(void)sendSocketMessage:(NSString*)message;
- (void) messageReceived:(NSString *)message;

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex;
- (void)makeSongRequest;



@end
