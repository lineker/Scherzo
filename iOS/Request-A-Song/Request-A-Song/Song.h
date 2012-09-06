//
//  Song.h
//  Request-A-Song
//
//  Created by Lineker Tomazeli on 12-01-25.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Song : NSObject

- (id)initWithDictionary:(NSDictionary *)array;
@property (nonatomic, copy) NSString *songName;
@property (nonatomic, copy) NSString *artist;
@property (nonatomic, copy) NSString *album;
@property (nonatomic, copy) NSNumber* songId;
@property (nonatomic, copy) NSDate *date;
@property (nonatomic, copy) NSString *length;
@property (nonatomic, copy) NSNumber *isPlaying;
@end
