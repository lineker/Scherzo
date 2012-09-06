//
//  LibraryViewController.m
//  Request-A-Song
//
//  Created by Lineker Tomazeli on 12-01-05.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "LibraryViewController.h"
#import "Song.h"
#import "SongDetailViewController.h"
#import "SBJson.h"
#import "SocketJSONParser.h"



@implementation LibraryViewController
@synthesize songs,output;
@synthesize inputStream, outputStream;
@synthesize messageShowed;

//initiate socket
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

//Create a stream socket delagate method
- (void)stream:(NSStream *)theStream handleEvent:(NSStreamEvent)streamEvent {
    
	NSLog(@"stream event %i", streamEvent);
	NSLog(@"input stream is -> %@",inputStream);
	switch (streamEvent) {
			
		case NSStreamEventOpenCompleted:
			NSLog(@"Stream opened");
            output = [NSMutableString stringWithFormat:@""];
			break;
		case NSStreamEventHasBytesAvailable:
            
			if (theStream == inputStream) {
				
				uint8_t buffer[1024];
				int len;
				//NSMutableString* output = [NSMutableString stringWithFormat:@""];
				int s = 0;
                while ([inputStream hasBytesAvailable]) {
                    NSLog(@"%i",s);
                    s++;
					len = [inputStream read:buffer maxLength:sizeof(buffer)];
					if (len > 0) {
						
						[output appendFormat:@"%@",[[NSString alloc] initWithBytes:buffer length:len encoding:NSASCIIStringEncoding]];
					}
				}
                
                
			}
			break;
            
        case NSStreamStatusClosed:
             NSLog(@"GOT TO CLOSED");
			
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
                
                NSLog(@"server said: %@", output);
                [self messageReceived:output];
                
            }
            
			break;
        }
		default:
			NSLog(@"Unknown event");
	}
    
}

- (void) messageReceived:(NSString *)message {
    id object = [_parser objectWithString:message];
    NSLog(@"message received");
    if ([object isKindOfClass:[NSDictionary class]])
    {
        songs = [object objectForKey:@"SongsList"];// treat as a dictionary, or reassign to a dictionary ivar
        NSLog(@"songs %@",songs);
    }
    else if ([object isKindOfClass:[NSArray class]])
    {
        NSLog(@"ARRAY %@",object);// treat as an array or reassign to an array ivar.
    }
    else if ([object isKindOfClass:[NSString class]])
    {
        NSLog(@"String %@",object);// treat as an array or reassign to an array ivar.
    }
    else
    {
        
        NSLog(@"NONE %@",[message class]);
    }
    
    
    [self.tableView reloadData];
    //check this
    //[self loadContentForVisibleCells]; 
    
}

-(void)sendSocketMessage:(NSString*)message
{
    NSLog(@"message: %@",message);
    NSString *nobreaks = [message stringByReplacingOccurrencesOfString:@"\n" withString:@""];
    NSString *finalMessage = [NSString stringWithFormat:@"%@\n\r",nobreaks];
    
    [self initNetworkCommunication];
	NSData *data = [[NSData alloc] initWithData:[finalMessage dataUsingEncoding:NSASCIIStringEncoding]];
	[outputStream write:[data bytes] maxLength:[data length]];
}

- (void)getPlaylist
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
    
    [requestDictionary setObject: @"PLAYLIST"  forKey: @"RequestType"];
    [requestDictionary setObject: [NSString stringWithFormat:@"iOS %@",[[UIDevice currentDevice] systemVersion]] forKey: @"Source"];
    
    NSString *json_message_tosend = [_writer stringWithObject:requestDictionary];
    
    requestDictionary = nil;
    
    [self sendSocketMessage:json_message_tosend];
    
}
-(IBAction) refresh:(id) sender
{
    [self getPlaylist];
}

#pragma mark - View lifecycle

- (void)viewWillAppear:(BOOL)animated
{
    [self getPlaylist];
    [super viewWillAppear:animated];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    //[self.tableView reloadData];
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
    
    return cell;
}

#pragma mark - Table view delegate

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    selectedSong = [[Song alloc] initWithDictionary:[self.songs objectAtIndex:indexPath.row]];
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Request" message:@"You can request a song or specify a time" delegate:self cancelButtonTitle:nil otherButtonTitles:nil];
    
    [alert addButtonWithTitle:@"Request selected Song"];
    [alert addButtonWithTitle:@"Specify time"];
    [alert addButtonWithTitle:@"Cancel"];
    
    [alert show];
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
    NSLog(@"%d",buttonIndex);
    
    if( [[alertView buttonTitleAtIndex:buttonIndex] isEqualToString:@"Close"])
    {
        NSLog(@"closeee");
    }
    else if(buttonIndex == 0)
    {
        NSLog(@"request songId = %d",[selectedSong.songId intValue]);
        
        [self makeSongRequest];
    }
    else if(buttonIndex == 1)
    {
        NSLog(@"Show calendar");
        [self performSegueWithIdentifier:@"SongDetail" sender:selectedSong];
    }
}

// This will get called too before the view appears
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    if ([[segue identifier] isEqualToString:@"SongDetail"]) {
        
        // Get destination view
        SongDetailViewController *vc = [segue destinationViewController];
        
        
        // Pass the information to your destination view
        [vc setSong:selectedSong];
    }
}

- (void)makeSongRequest
{
    NSLog(@"Song request without date");
    
    SocketJSONParser *j = [[SocketJSONParser alloc] init]; 
    
    [j requestSong:selectedSong.songId atTime:nil];
    
    j = nil;
    
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Song Requested" message:@"Thank you for requesting a song" delegate:self cancelButtonTitle:@"Close" otherButtonTitles:nil];
    [alert show];
}

@end
