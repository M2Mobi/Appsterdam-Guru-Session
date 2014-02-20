//
//  M2MCountryObject.h
//  GuruSession
//
//  Created by Blackberry on 2/5/14.
//  Copyright (c) 2014 Blackberry. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface M2MCountryObject : NSObject

// properties
@property (nonatomic, strong) NSString *countryName;
@property (nonatomic, strong) NSString *countryContinent;
@property (nonatomic, strong) NSString *countryCapital;
@property (nonatomic, strong) NSString *countryPopulation;
@property (nonatomic, strong) NSString *countryCode;
@property (nonatomic, assign) BOOL visitedCountry;

// initialiser
-(id)initWithDictionary:(NSDictionary *)rawDictionary;

@end
