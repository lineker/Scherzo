//
//  FeedbackViewController.m
//  Request-A-Song
//
//  Created by Lineker Tomazeli on 12-01-05.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "NowPlayingViewController.h"
#import "Song.h"
#import "SBJson.h" 

@implementation NowPlayingViewController
@synthesize songs,output;
@synthesize inputStream, outputStream;
@synthesize messageShowed;

- (void) initNetworkCommunication {
	
    
    
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
    
	//NSLog(@"stream event %i", streamEvent);
	
	switch (streamEvent) {
			
		case NSStreamEventOpenCompleted:
			NSLog(@"Stream opened");
            output = [NSMutableString stringWithFormat:@""];
			break;
		case NSStreamEventHasBytesAvailable:
            
			if (theStream == inputStream) {
				
				uint8_t buffer[1024];
				int len;

				while ([inputStream hasBytesAvailable]) {
					len = [inputStream read:buffer maxLength:sizeof(buffer)];
					if (len > 0) {
						
						//output = [[NSString alloc] initWithBytes:buffer length:len encoding:NSASCIIStringEncoding];
						[output appendFormat:@"%@",[[NSString alloc] initWithBytes:buffer length:len encoding:NSASCIIStringEncoding]];
						
					}
				}
                
               
			}
			break;
            
			
		case NSStreamEventErrorOccurred:
        {
            if(messageShowed == NO)
            {
                messageShowed = YES;
                UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Server not found!" 
                                                                message:@"Please try again." 
                                                               delegate:self 
                                                      cancelButtonTitle:@"Close" 
                                                      otherButtonTitles:nil];
                [alert show];
            }
			NSLog(@"Can not connect to the host!");
			
			break;
        }
		case NSStreamEventEndEncountered:
        {
            [theStream close];
            [theStream removeFromRunLoop:[NSRunLoop currentRunLoop] forMode:NSDefaultRunLoopMode];
            theStream = NULL;
            theStream = nil;
			
            if (nil != output) {
                
                //NSLog(@"server said: %@", output);
                [self messageReceived:output];
                
            }
            
			break;
        }
		default:
        {
			//NSLog(@"Unknown event");
        }
	}
    
}

- (void) messageReceived:(NSString *)message {
    id object = [_parser objectWithString:message];
    
    if ([object isKindOfClass:[NSDictionary class]])
    {
        songs = [object objectForKey:@"SongsList"];// treat as a dictionary, or reassign to a dictionary ivar
        //NSLog(@"songs %@",songs);
    }
    else if ([object isKindOfClass:[NSArray class]])
    {
        //NSLog(@"ARRAY %@",object);// treat as an array or reassign to an array ivar.
    }
    else
    {
        //NSLog(@"NONE");
    }
            
    
    [self.tableView reloadData];
    //check this
    //[self loadContentForVisibleCells]; 
    
}

-(void)sendSocketMessage:(NSString*)message
{
    //NSLog(@"message: %@",message);
    NSString *nobreaks = [message stringByReplacingOccurrencesOfString:@"\n" withString:@""];
    NSString *finalMessage = [NSString stringWithFormat:@"%@\n\r",nobreaks];
    
    [self initNetworkCommunication];
	NSData *data = [[NSData alloc] initWithData:[finalMessage dataUsingEncoding:NSASCIIStringEncoding]];
	[outputStream write:[data bytes] maxLength:[data length]];
}

- (void)getPlayingList
{
    if(messageShowed)
    {
        messageShowed = NO;
    }
    if(_parser == nil)
    {
        _parser = [[SBJsonParser alloc] init];
    }
    if(_writer == nil)
    {
        _writer = [[SBJsonWriter alloc] init];
        _writer.humanReadable = YES;
        _writer.sortKeys = YES;
    }
    
    NSMutableDictionary *requestDictionary =[NSMutableDictionary dictionary];
    
    [requestDictionary setObject: @"PLAYING"  forKey: @"RequestType"];
    [requestDictionary setObject: [NSString stringWithFormat:@"iOS %@",[[UIDevice currentDevice] systemVersion]] forKey: @"Source"];
    
    NSString *json_message_tosend = [_writer stringWithObject:requestDictionary];
    
    requestDictionary = nil;
    
    [self sendSocketMessage:json_message_tosend];

}
-(IBAction) refresh:(id) sender
{
    
    [self getPlayingList];
}


#pragma mark - View lifecycle

- (void)viewWillAppear:(BOOL)animated
{
    [self getPlayingList];
    [super viewWillAppear:animated];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    [self.tableView reloadData];
    
    // Uncomment the following line to preserve selection between presentations.
    // self.clearsSelectionOnViewWillAppear = NO;
 
    // Uncomment the following line to display an Edit button in the navigation bar for this view controller.
    // self.navigationItem.rightBarButtonItem = self.editButtonItem;
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}


- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    //NSLog(@"count %@",songs);
    return [self.songs count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
    }
	
    NSDictionary *song = [self.songs objectAtIndex:indexPath.row];
	cell.textLabel.text = [song objectForKey:@"Title"];
	cell.detailTextLabel.text = [song objectForKey:@"Artist"];
    NSNumber *isplaying = [song objectForKey:@"isPlaying"];
    //NSLog(@"isPlaying : %@",isplaying);
    //NSLog(@"%@",indexPath.row);
    
    //if([isplaying intValue] != 0)
    //{
    //    cell.accessoryType = UITableViewCellAccessoryCheckmark;
    //}
    
    
    return cell;
}

@end
