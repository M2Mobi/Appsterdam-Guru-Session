@feature_countries
Feature: Countries

    Scenario: Check single country without scrolling
        Given I am logged in
        Then I am on the "Country list" screen
        Then I touch the "Angola" text

    Scenario: Check single country with scrolling
        Given I am logged in
        Then I am on the "Country list" screen
        Then I scroll until I see the "Yemen" text
        Then I touch the "Yemen" text

    Scenario: Open country, visit it and filter the list
        Given I am logged in
        Then I am on the "Country list" screen
        Then I scroll until I see the "Netherlands" text
        Then I touch the "Netherlands" text
        Then I press view with id "detail_visited"
        Then I go back
        Then I press view with id "menu_list_myplaces"
        Then I should see text containing "Netherlands"
        Then I should not see "Nepal"

    Scenario: Open, visit, and filter for multiple countries
        Given I am logged in
        Then I am on the "Country list" screen
        Then I scroll until I see the "Brazil" text
        Then I touch the "Brazil" text
        Then I press view with id "detail_visited"
        Then I go back
        Then I scroll until I see the "Denmark" text
        Then I touch the "Denmark" text
        Then I press view with id "detail_visited"
        Then I go back
        Then I scroll until I see the "Greece" text
        Then I touch the "Greece" text
        Then I press view with id "detail_visited"
        Then I go back
        Then I press view with id "menu_list_myplaces"
        Then I should see text containing "Brazil"
        Then I should see text containing "Denmark"
        Then I should see text containing "Greece"