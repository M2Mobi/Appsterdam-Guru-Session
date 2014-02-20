//
//  M2MViewController.h
//  GuruSession
//
//  Created by Blackberry on 2/5/14.
//  Copyright (c) 2014 Blackberry. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface M2MLoginViewController : UIViewController

// IBOutlets
@property (weak, nonatomic) IBOutlet UITextField *usernameTextField;
@property (weak, nonatomic) IBOutlet UITextField *passwordTextField;
@property (weak, nonatomic) IBOutlet UIButton *loginButton;

// IBActions
- (IBAction)loginButtonTapped:(id)sender;

@end
