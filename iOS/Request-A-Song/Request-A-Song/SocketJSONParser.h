//
//  JSONParser.h
//  Request-A-Song
//
//  Created by Lineker Tomazeli on 12-02-09.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@class SBJsonParser;
@class SBJsonWriter;

@interface SocketJSONParser : NSObject <NSStreamDelegate>
{
    SBJsonParser *_parser;
    SBJsonWriter *_writer;
    
    NSInputStream	*inputStream;
	NSOutputStream	*outputStream;
    BOOL messageSent;
}
@property (nonatomic, assign) BOOL messageSent;
@property (nonatomic, retain) NSInputStream *inputStream;
@property (nonatomic, retain) NSOutputStream *outputStream;

- (void) initNetworkCommunication;
-(void)sendSocketMessage:(NSString*)message;
- (void) receivedSocketmessage:(NSString *)message;

- (NSString *)formatText:(id)sender;



- (BOOL)sendFeedback:(NSString*)message;

- (void)getPlayingList;

- (void)getPlayList;

- (void)requestSong:(NSNumber *)songId atTime:(NSString *)time;

@end
