//
//  M2MViewController.m
//  GuruSession
//
//  Created by Blackberry on 2/5/14.
//  Copyright (c) 2014 Blackberry. All rights reserved.
//

#import "M2MLoginViewController.h"

@interface M2MLoginViewController ()

@end

NSString *const kDummyUsername = @"m2mobi";
NSString *const kDummyPassword = @"password";

@implementation M2MLoginViewController

- (IBAction)loginButtonTapped:(id)sender {
    
    if ([self.usernameTextField.text isEqualToString:kDummyUsername]) {
        if ([self.passwordTextField.text isEqualToString:kDummyPassword]) {
            [self performSegueWithIdentifier:@"loginSuccesfulSegue" sender:self];
        } else {
            [self showAlertWithMessage:@"Invalid password"];
        }
    } else {
        [self showAlertWithMessage:@"Invalid username/password combination"];
    }
}


-(void)showAlertWithMessage:(NSString *)alertMessage
{
    UIAlertView *wrongCredentialsAlert = [[UIAlertView alloc] initWithTitle:@"Failed to login"
                                                                    message:alertMessage
                                                                   delegate:nil
                                                          cancelButtonTitle:@"Ok"
                                                          otherButtonTitles:nil, nil];
    [wrongCredentialsAlert show];
}

@end
