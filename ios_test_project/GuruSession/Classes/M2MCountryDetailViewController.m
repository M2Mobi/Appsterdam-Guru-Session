//
//  M2MCountryDetailViewController.m
//  GuruSession
//
//  Created by Blackberry on 2/5/14.
//  Copyright (c) 2014 Blackberry. All rights reserved.
//

#import "M2MCountryDetailViewController.h"

@interface M2MCountryDetailViewController ()

@end

NSString *const kFlagURL = @"http://www.geonames.org/flags/x/%@.gif";

@implementation M2MCountryDetailViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    NSURL *url = [NSURL URLWithString:[NSString stringWithFormat:kFlagURL,[self.detailCountryInfo.countryCode lowercaseString]]];
    NSData *data = [NSData dataWithContentsOfURL:url];
    UIImage *img = [[UIImage alloc] initWithData:data];
    [self.countryFlagImageView setImage:img];
    
    NSString *detailString = [NSString stringWithFormat:@"Continent : %@ \nCapital : %@ \nPopulation : %@",
                              self.detailCountryInfo.countryContinent,
                              self.detailCountryInfo.countryCapital,
                              self.detailCountryInfo.countryPopulation];
    
    [self.countryDetailTextView setText:detailString];
    
    [self.countryVisitedSwitch setOn:self.detailCountryInfo.visitedCountry];
}

- (IBAction)countryVisitedValueChanged:(id)sender
{
    if ([sender isOn]) {
        self.detailCountryInfo.visitedCountry = YES;
    } else {
        self.detailCountryInfo.visitedCountry = NO;
    }
}

@end
