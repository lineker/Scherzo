//
//  Song.m
//  Request-A-Song
//
//  Created by Lineker Tomazeli on 12-01-25.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "Song.h"

@implementation Song
@synthesize songName = mSongName, artist = mArtist, songId = mSongId, album = mAlbum, date = mDate, length = mLength, isPlaying = mIsPlaying;

- (id)initWithDictionary:(NSDictionary *)array
{
    
    self = [super init];
    if (self) {
        NSLog(@"%@",[array objectForKey:@"Title"]);
        self.songName = [array objectForKey:@"Title"];
        self.artist = [array objectForKey:@"Artist"];
        self.songId = [array objectForKey:@"Id"];
        self.length = [array objectForKey:@"Length"];
        self.isPlaying = [array objectForKey:@"isPlaying"];
    }    
    return self;
}


@end
