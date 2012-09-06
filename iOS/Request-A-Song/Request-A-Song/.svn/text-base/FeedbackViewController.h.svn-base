//
//  FirstViewController.h
//  Request-A-Song
//
//  Created by Lineker Tomazeli on 12-01-05.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SBJson.h"

@interface FeedbackViewController : UIViewController<NSStreamDelegate>
{
    SBJsonParser *_parser;
    SBJsonWriter *_writer;
    
    NSInputStream	*inputStream;
	NSOutputStream	*outputStream;
    
    IBOutlet UITextView *messageBox;
    IBOutlet UIBarButtonItem *doneButton;
    
    BOOL doneWritting;
    
}

//- (void) initNetworkCommunication;
//- (void) receivedSocketmessage:(NSString *)message;
@property (nonatomic, retain) UIBarButtonItem *doneButton;
@property (nonatomic, retain) UITextView *messageBox;
@property (nonatomic,assign) BOOL doneWritting;

- (IBAction)SendFeedback:(id)sender;
//- (void)sendSocketMessage:(NSString*)message;
- (IBAction)dismissKeyboard:(id)sender;
@end
