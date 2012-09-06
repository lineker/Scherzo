//
//  SongDetailViewController.h
//  Request-A-Song
//
//  Created by Lineker Tomazeli on 12-01-25.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Song.h"

@interface SongDetailViewController : UIViewController
{
    __weak IBOutlet UILabel *labelSongName;
    __weak IBOutlet UILabel *labelArtistName;
    __weak IBOutlet UILabel *labelAlbumName;
    __weak IBOutlet UIDatePicker *datePicker;
    Song *selectedSong;
    __weak IBOutlet UILabel *labelLength;
}

-(void)setSong:(Song*)mysong;

-(IBAction)requestSongWithDate:(id)sender;
@end
