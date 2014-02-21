@feature_countries
Feature: Countries

    Scenario: When I get on the country screen I want to select angola
        Given I passed login feature tests
        Given I am on the country screen
        Then I touch "Angola"

    Scenario: When I get on the country screen I want to select angola
        Given I passed login feature tests
        Given I am on the country screen
        Then I scroll to cell with "Yemen" label
        Then I touch "Yemen"
        Then I toggle the "VisitedSwitch" switch

    Scenario: When I get on the country screen I want to select Netherlands and visit it
        Given I passed login feature tests
        Given I am on the country screen
        Then I scroll to cell with "Netherlands" label
        Then I touch "Netherlands"
        Given I am on the country detail screen
        Then I touch "VisitedSwitch"

    Scenario: When I get on the country screen I want to select Netherlands and visit it and check if the filter works correctly
        Given I passed login feature tests
        Given I am on the country screen
        Then I scroll to cell with "Netherlands" label
        Then I touch "Netherlands"
        Given I am on the country detail screen
        Then I touch "VisitedSwitch"
        Then I touch "Countries"
        Then I touch "Visited"
        Then I should see text containing "Netherlands"
        Then I should not see "Nepal"

    Scenario: Open, visit, and filter for multiple countries
        Given I passed login feature tests
        Given I am on the country screen
        Then I select country "Angola" as visited
        Then I select country "Cuba" as visited
        Then I select country "Netherlands" as visited
        Then I touch "Visited"
        Then I should see text containing "Angola"
        Then I should see text containing "Cuba"
        Then I should see text containing "Netherlands"