//
//  M2MCountriesTableViewController.h
//  GuruSession
//
//  Created by Blackberry on 2/5/14.
//  Copyright (c) 2014 Blackberry. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface M2MCountriesTableViewController : UITableViewController

// IBOutlets
@property (weak, nonatomic) IBOutlet UIButton *filterButton;

// IBActions
- (IBAction)filterVisitedTapped:(UIButton *)sender;

@end
