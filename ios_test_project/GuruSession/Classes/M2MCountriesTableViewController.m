//
//  M2MCountriesTableViewController.m
//  GuruSession
//
//  Created by Blackberry on 2/5/14.
//  Copyright (c) 2014 Blackberry. All rights reserved.
//

#import "M2MCountriesTableViewController.h"
#import "M2MCountryDetailViewController.h"
#import "M2MCountryObject.h"
#import "M2MCountriesDataSource.h"

@interface M2MCountriesTableViewController ()

@property (nonatomic, strong) M2MCountriesDataSource *countriesDataSource;

@end

@implementation M2MCountriesTableViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    [self.tableView setDataSource:self.countriesDataSource];
    [self.tableView setDelegate:self];
}

-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];

    // reloading the tableview, so it will display our "visited" countries correctly
    [self.countriesDataSource filteredOnVisitedWithSelection:self.filterButton.selected];
    [self.tableView reloadData];
}

- (M2MCountriesDataSource *)countriesDataSource
{
    if (!_countriesDataSource) {
        _countriesDataSource = [[M2MCountriesDataSource alloc] init];
    }
    return _countriesDataSource;
}

#pragma mark - Segue Handling
-(void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    // Get the index of the selected cell
    NSIndexPath *selectedIndex =[self.tableView indexPathForSelectedRow];
    [self.tableView deselectRowAtIndexPath:selectedIndex animated:YES];
    
    // Obtain the countryObject from out array
    M2MCountryObject *selectedCountry = [self.countriesDataSource getCountryObjectForIndex:selectedIndex];
    
    // Set the detail view controller title
    M2MCountryDetailViewController *detailController = [segue destinationViewController];
    detailController.title = selectedCountry.countryName;
    detailController.detailCountryInfo = selectedCountry;
}

#pragma mark - Button Handling
- (IBAction)filterVisitedTapped:(UIButton *)sender
{
    if (!sender.selected) {
        self.filterButton.selected = YES;
        [self.countriesDataSource filteredOnVisitedWithSelection:YES];
    } else {
        self.filterButton.selected = NO;
        [self.countriesDataSource filteredOnVisitedWithSelection:NO];
    }
    [self.tableView reloadData];
}
@end
