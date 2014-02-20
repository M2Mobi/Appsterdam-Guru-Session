//
//  M2MCountriesDataSource.h
//  GuruSession
//
//  Created by Blackberry on 2/6/14.
//  Copyright (c) 2014 Blackberry. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "M2MCountryObject.h"

@interface M2MCountriesDataSource : NSObject <UITableViewDataSource>

// Methods called from tableviewController
-(void)filteredOnVisitedWithSelection:(BOOL)selected;
-(M2MCountryObject *)getCountryObjectForIndex:(NSIndexPath *)indexPath;

@end
