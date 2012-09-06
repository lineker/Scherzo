//
//  FeedbackViewController.h
//  Request-A-Song
//
//  Created by Lineker Tomazeli on 12-01-05.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class SBJsonParser;
@class SBJsonWriter;

@interface NowPlayingViewController : UITableViewController <NSStreamDelegate, UITableViewDelegate, UITableViewDataSource>
{
    NSInputStream	*inputStream;
	NSOutputStream	*outputStream;
    NSArray *songs;
    NSMutableString* output;
    SBJsonParser *_parser;
    SBJsonWriter *_writer;
    BOOL messageShowed;
    
}
-(IBAction) refresh:(id) sender;

@property (nonatomic, retain) NSInputStream *inputStream;
@property (nonatomic, retain) NSOutputStream *outputStream;
@property (nonatomic, retain) NSMutableString *output;
@property (nonatomic, strong) NSArray *songs;
@property (nonatomic,assign) BOOL messageShowed;

- (void) initNetworkCommunication;
- (void)getPlayingList;
-(void)sendSocketMessage:(NSString*)message;
- (void) messageReceived:(NSString *)message;


@end
