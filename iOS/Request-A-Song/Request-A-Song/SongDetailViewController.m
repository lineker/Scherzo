//
//  SongDetailViewController.m
//  Request-A-Song
//
//  Created by Lineker Tomazeli on 12-01-25.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "SongDetailViewController.h"
#import "Song.h"
#import "SocketJSONParser.h"

@implementation SongDetailViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

/*
// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView
{
}
*/

/*
// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad
{
    [super viewDidLoad];
}
*/

- (void)viewDidUnload
{
    labelLength = nil;
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    selectedSong = sender;
}

-(void)viewWillAppear:(BOOL)animated
{
    if(selectedSong != nil)
    {
        NSLog(@"setting text");
        labelSongName.text = selectedSong.songName;
        labelArtistName.text = selectedSong.artist;
        labelAlbumName.text = selectedSong.album;
        labelLength.text = selectedSong.length;
        NSDate *Date=[NSDate date];
        
        datePicker.minimumDate=Date;
    }
}

-(void)setSong:(Song*)mysong
{
    selectedSong = mysong;
    NSLog(@"%@",mysong);
}

-(IBAction)requestSongWithDate:(id)sender;
{
    NSLog(@"request Song with date=%@",datePicker.date);
    
    SocketJSONParser *j = [[SocketJSONParser alloc] init]; 
    
    NSDateFormatter *df = [NSDateFormatter new];
    [df setDateFormat:@"HH:mm"];    
    
    [j requestSong:selectedSong.songId atTime:[df stringFromDate:datePicker.date]];
    
    df = nil;
    
     UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Song Requested" message:@"Thank you for requesting a song" delegate:self cancelButtonTitle:@"Close" otherButtonTitles:nil];
    [alert show];
    [self.navigationController popViewControllerAnimated:YES];
}
@end
