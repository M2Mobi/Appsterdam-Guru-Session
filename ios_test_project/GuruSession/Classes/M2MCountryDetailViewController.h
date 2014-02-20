//
//  M2MCountryDetailViewController.h
//  GuruSession
//
//  Created by Blackberry on 2/5/14.
//  Copyright (c) 2014 Blackberry. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "M2MCountryObject.h"

@interface M2MCountryDetailViewController : UIViewController

// IBOutlets
@property (weak, nonatomic) IBOutlet UIImageView *countryFlagImageView;
@property (weak, nonatomic) IBOutlet UISwitch *countryVisitedSwitch;
@property (weak, nonatomic) IBOutlet UITextView *countryDetailTextView;

// IBActions
- (IBAction)countryVisitedValueChanged:(id)sender;

// properties
@property (nonatomic, strong) M2MCountryObject *detailCountryInfo;

@end
