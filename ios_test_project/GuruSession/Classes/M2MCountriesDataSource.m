//
//  M2MCountriesDataSource.m
//  GuruSession
//
//  Created by Blackberry on 2/6/14.
//  Copyright (c) 2014 Blackberry. All rights reserved.
//

#import "M2MCountriesDataSource.h"

@interface M2MCountriesDataSource ()

@property (nonatomic, strong) NSArray *rawCountriesArray;
@property (nonatomic, strong) NSMutableArray *displayCountriesArray;

@end

NSString *const kJSONFileName = @"countriesList";
NSString *const kJSONKeyForCountries = @"geonames";

NSString *const kCountryCellIdentifier = @"countryCell";

NSInteger const kAccessoryViewHeightWidth = 30;

@implementation M2MCountriesDataSource

- (id)init{
    
    self = [super init];
    if(self){
        self.rawCountriesArray = [NSArray array];
        self.displayCountriesArray = [NSMutableArray array];
        
        [self loadAllCountries];
    }
    return self;
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    // Return the number of sections.
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return [self.displayCountriesArray count];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:kCountryCellIdentifier forIndexPath:indexPath];
    
    // Obtain the countryObject from out array
    M2MCountryObject *selectedCountry = [self.displayCountriesArray objectAtIndex:indexPath.row];
    
    [cell.textLabel setText:selectedCountry.countryName];
    
    if (selectedCountry.visitedCountry) {
        UIImageView *visitedImageView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, kAccessoryViewHeightWidth, kAccessoryViewHeightWidth)];
        visitedImageView.image = [UIImage imageNamed:@"visitedFlag"];
        visitedImageView.contentMode = UIViewContentModeScaleAspectFit;
        cell.accessoryView = visitedImageView;
    } else {
        cell.accessoryView = nil;
    }
    
    return cell;
}

#pragma mark - Actions Handling
-(void)filteredOnVisitedWithSelection:(BOOL)selected
{
    // If we have objects in our displaying array, we want to empty this array
    if ([self.displayCountriesArray count]){
        [self.displayCountriesArray removeAllObjects];
    }
    
    // Check the objects if the visisted BOOL has been set
    if (selected) {
        for (M2MCountryObject *object in self.rawCountriesArray) {
            if (object.visitedCountry) {
                [self.displayCountriesArray addObject:object];
            }
        }
    } else {
        self.displayCountriesArray = [NSMutableArray arrayWithArray:self.rawCountriesArray];
    }
}

-(M2MCountryObject *)getCountryObjectForIndex:(NSIndexPath *)indexPath
{
    return [self.displayCountriesArray objectAtIndex:indexPath.row];
}

#pragma mark - Data Handling
-(void)loadAllCountries
{
    NSArray *rawJSONArray = [[self getCountriesAsDict] objectForKey:kJSONKeyForCountries];
    
    NSSortDescriptor *descriptor = [[NSSortDescriptor alloc] initWithKey:@"countryName" ascending:YES];
    NSArray *descriptors = [NSArray arrayWithObject: descriptor];
    rawJSONArray = [rawJSONArray sortedArrayUsingDescriptors:descriptors];
    
    for (NSDictionary *countryDict in rawJSONArray)
    {
        M2MCountryObject *countryObject = [[M2MCountryObject alloc] initWithDictionary:countryDict];
        [self.displayCountriesArray addObject:countryObject];
    }
    
    self.rawCountriesArray = [NSArray arrayWithArray:self.displayCountriesArray];
}

-(NSDictionary *)getCountriesAsDict
{
    NSString *documentsDirectory = [[NSBundle mainBundle] pathForResource:kJSONFileName ofType:@"JSON"];
    NSAssert(documentsDirectory, @"Can't find JSON");

    NSError *error;
    NSData *documentData = [NSData dataWithContentsOfFile:documentsDirectory];
    NSDictionary *JSONAsDict = [NSJSONSerialization JSONObjectWithData:documentData  options: NSJSONReadingMutableContainers error: &error];
    
    return JSONAsDict;
}

@end
