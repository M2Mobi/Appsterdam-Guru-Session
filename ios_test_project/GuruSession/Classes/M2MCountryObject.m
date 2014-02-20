//
//  M2MCountryObject.m
//  GuruSession
//
//  Created by Blackberry on 2/5/14.
//  Copyright (c) 2014 Blackberry. All rights reserved.
//

#import "M2MCountryObject.h"

@implementation M2MCountryObject


-(id)initWithDictionary:(NSDictionary *)rawDictionary
{
    self = [super init];
    if(self != nil) {
        self.countryName = [rawDictionary objectForKey:@"countryName"];
        self.countryContinent = [rawDictionary objectForKey:@"continentName"];
        self.countryCapital = [rawDictionary objectForKey:@"capital"];
        self.countryPopulation = [rawDictionary objectForKey:@"population"];
        self.countryCode = [rawDictionary objectForKey:@"countryCode"];
    }
    return self;
}

@end
