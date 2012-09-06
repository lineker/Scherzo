//
//  FirstViewController.m
//  Request-A-Song
//
//  Created by Lineker Tomazeli on 12-01-05.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "FeedbackViewController.h"
#import "SocketJSONParser.h"
#import <QuartzCore/QuartzCore.h>

@implementation FeedbackViewController
@synthesize messageBox;
@synthesize doneButton;
@synthesize doneWritting;
//@synthesize messageShowed;
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    //[self initNetworkCommunication];
	// Do any additional setup after loading the view, typically from a nib.
    
    [[NSNotificationCenter defaultCenter] addObserver:self 
                                             selector:@selector(keyboardWillShow:) 
                                                 name:UIKeyboardWillShowNotification 
                                               object:nil];
	[[NSNotificationCenter defaultCenter] addObserver:self 
                                             selector:@selector(keyboardWillHide:) 
                                                 name:UIKeyboardWillHideNotification 
                                               object:nil];
    
	//set notification for when a key is pressed.
	[[NSNotificationCenter defaultCenter] addObserver:self 
                                             selector: @selector(keyPressed:) 
                                                 name: UITextViewTextDidChangeNotification 
                                               object: nil];
    
	//turn off scrolling and set the font details.
	messageBox.scrollEnabled = YES;
	messageBox.font = [UIFont fontWithName:@"Helvetica" size:14]; 
    
    messageBox.layer.cornerRadius = 5;
    //messageBox.clipsToBounds = YES;
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
    

}
-(void) keyboardWillShow:(NSNotification *)note{
    
}
-(void) keyPressed: (NSNotification*) notification{

}

-(void) keyboardWillHide:(NSNotification *)note{

}
- (IBAction)dismissKeyboard:(id)sender {
    
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated
{
	[super viewWillDisappear:animated];
}

- (void)viewDidDisappear:(BOOL)animated
{
	[super viewDidDisappear:animated];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPhone) {
        return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
    } else {
        return YES;
    }
}
//send feed back
-(IBAction)SendFeedback:(id)sender
{
    [messageBox resignFirstResponder];
        SocketJSONParser *j = [[SocketJSONParser alloc] init]; 
        
        BOOL sent = [j sendFeedback:messageBox.text];
        
        if(sent)
        {
            messageBox.text = @"";
            
            
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Feedback" message:@"Thank you for your feedback!" delegate:self cancelButtonTitle:@"Close" otherButtonTitles:nil];
            [alert show];
        }else {
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Server not found!" 
                                                            message:@"Please try again." 
                                                           delegate:self 
                                                  cancelButtonTitle:@"Close" 
                                                  otherButtonTitles:nil];
            [alert show];
        }
   
}

@end
