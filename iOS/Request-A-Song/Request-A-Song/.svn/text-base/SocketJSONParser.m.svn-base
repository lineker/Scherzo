//
//  JSONParser.m
//  Request-A-Song
//
//  Created by Lineker Tomazeli on 12-02-09.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "SocketJSONParser.h"
#import "SBJson.h"   


@implementation SocketJSONParser
@synthesize inputStream, outputStream;
@synthesize messageSent;

- (id)init
{
    self = [super init];
    if (self) {
        _parser = [[SBJsonParser alloc] init];
        _writer = [[SBJsonWriter alloc] init];
        _writer.humanReadable = YES;
        _writer.sortKeys = YES;
    }    
    return self;
}

- (void)dealloc
{
    _parser = NULL;
    _writer = NULL;
}



- (void) initNetworkCommunication {
	messageSent = YES;
	CFReadStreamRef readStream;
	CFWriteStreamRef writeStream;
    
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    NSString *serverAddress = [defaults stringForKey:@"ServerAddress"];
    int serverPort = [defaults integerForKey:@"Port"];
    NSLog(@"%@ : %d",serverAddress,serverPort);
    
	CFStreamCreatePairWithSocketToHost(NULL, (__bridge CFStringRef)serverAddress, serverPort, &readStream, &writeStream);
	
	inputStream = (__bridge NSInputStream *)readStream;
	outputStream = (__bridge NSOutputStream *)writeStream;
	[inputStream setDelegate:self];
	[outputStream setDelegate:self];
	[inputStream scheduleInRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
	[outputStream scheduleInRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
	[inputStream open];
	[outputStream open];
	
}

- (void)stream:(NSStream *)theStream handleEvent:(NSStreamEvent)streamEvent {
    
	NSLog(@"stream event %i", streamEvent);
	
    if (theStream == inputStream) {
         NSLog(@"is NULL event");
    }
    else
    {
         NSLog(@"is NULL event");
    }
    
    if(theStream == NULL)
        NSLog(@"is NULL event");
    
	switch (streamEvent) {
			
		case NSStreamEventOpenCompleted:
			NSLog(@"Stream opened");
			break;
		case NSStreamEventHasBytesAvailable:
            
			if (theStream == inputStream) {
				
				uint8_t buffer[1024];
				int len;
				
				while ([inputStream hasBytesAvailable]) {
					len = [inputStream read:buffer maxLength:sizeof(buffer)];
					if (len > 0) {
						
						NSString *output = [[NSString alloc] initWithBytes:buffer length:len encoding:NSASCIIStringEncoding];
						
						if (nil != output) {
                            
							NSLog(@"server said: %@", output);
							[self receivedSocketmessage:output];
							
						}
					}
				}
			}
			break;
            
			
		case NSStreamEventErrorOccurred:
			messageSent = NO;
			NSLog(@"Can not connect to the host!");
			break;
			
		case NSStreamEventEndEncountered:
            
            [theStream close];
            [theStream removeFromRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
            //[theStream release];
            theStream = nil;
			
			break;
        case NSStreamStatusWriting:
            NSLog(@"writing to socket!");
            break;
        case NSStreamStatusReading:
             NSLog(@"reading to socket!");
		default:
        {
			NSLog(@"Unknown event");
            
            
        }
            
	}
    
}

- (void) receivedSocketmessage:(NSString *)message {
    NSLog(@"received : %@",message);
}

- (NSString *)formatText:(id)sender {
    NSString *json_message_received = @"{\"RequestType\":\"FEEDBACK\",\"Source\":\"TEST_CLIENT\",\"FeedbackMessage\":\"Feedback test from client 0\"}\n";
    id object = [_parser objectWithString:json_message_received];
    NSLog(@"from JSON to object : %@",[object objectForKey:@"RequestType"]);
    if (object) {
         NSString *json_message_tosend = [_writer stringWithObject:object];
        NSLog(@"OBJECT to JSON %@",json_message_tosend);
    } else {
        NSLog(@"%@",[NSString stringWithFormat:@"An error occurred: %@", _parser.error]) ;
    }
    
    return @"";
}

- (BOOL)sendFeedback:(NSString*)message
{
    NSMutableDictionary *requestDictionary =[NSMutableDictionary dictionary];
    
    [requestDictionary setObject: @"FEEDBACK"  forKey: @"RequestType"];
    [requestDictionary setObject: [NSString stringWithFormat:@"iOS %@",[[UIDevice currentDevice] systemVersion]] forKey: @"Source"];
    [requestDictionary setObject:message  forKey: @"FeedbackMessage"];
    
    NSString *json_message_tosend = [_writer stringWithObject:requestDictionary];
    NSLog(@"here : %@",json_message_tosend);
    requestDictionary = NULL;
    
    [self sendSocketMessage:json_message_tosend];
    
    return messageSent;
}



- (void)getPlayingList
{
    NSMutableDictionary *requestDictionary =[NSMutableDictionary dictionary];

    [requestDictionary setObject: @"PLAYING"  forKey: @"RequestType"];
    [requestDictionary setObject: [NSString stringWithFormat:@"iOS %@",[[UIDevice currentDevice] systemVersion]] forKey: @"Source"];
    
    NSString *json_message_tosend = [_writer stringWithObject:requestDictionary];
    
    requestDictionary = NULL;
    
    [self sendSocketMessage:json_message_tosend];
    [inputStream close];
	[outputStream close];
}

- (void)getPlayList
{
    NSMutableDictionary *requestDictionary =[NSMutableDictionary dictionary];
    
    [requestDictionary setObject: @"PLAYING"  forKey: @"RequestType"];
    [requestDictionary setObject: [NSString stringWithFormat:@"iOS %@",[[UIDevice currentDevice] systemVersion]] forKey: @"Source"];
    
    NSString *json_message_tosend = [_writer stringWithObject:requestDictionary];
    
    requestDictionary = NULL;
    
    
    [self sendSocketMessage:json_message_tosend];
    [inputStream close];
	[outputStream close];
}

- (void)requestSong:(NSNumber *)songId atTime:(NSString *)time
{
    NSMutableDictionary *requestDictionary =[NSMutableDictionary dictionary];
    
    [requestDictionary setObject: @"SONGREQUEST"  forKey: @"RequestType"];
    [requestDictionary setObject: songId  forKey: @"SongId"];
    if(time)
        [requestDictionary setObject: time  forKey: @"PlaySpecificTime"];
    [requestDictionary setObject: [NSString stringWithFormat:@"iOS %@",[[UIDevice currentDevice] systemVersion]] forKey: @"Source"];
    
    NSString *json_message_tosend = [_writer stringWithObject:requestDictionary];
    
    requestDictionary = NULL;
    
    [self sendSocketMessage:json_message_tosend];
    [inputStream close];
	[outputStream close];
}

-(void)sendSocketMessage:(NSString*)message
{
    NSString *nobreaks = [message stringByReplacingOccurrencesOfString:@"\n" withString:@""];
    NSString *finalMessage = [NSString stringWithFormat:@"%@\n\r",nobreaks];
    
    [self initNetworkCommunication];
	NSData *data = [[NSData alloc] initWithData:[finalMessage dataUsingEncoding:NSASCIIStringEncoding]];
	[outputStream write:[data bytes] maxLength:[data length]];
    
    [inputStream close];
	[outputStream close];
}

@end
