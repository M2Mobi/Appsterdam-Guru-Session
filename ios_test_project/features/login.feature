@feature_login
Feature: Login

    Scenario: As a user I must enter a username in order to login
        Given I am on the "Login" screen
        Then I press the "Login" button
        Then an error message should appear stating "Invalid username"

    Scenario: As a user I must enter a username in order to login
        Given I am on the "Login" screen
        Then I enter "m2mobi" into the "username_field" field
        Then I press the "Login" button
        Then an error message should appear stating "Invalid password"

    Scenario: As a user I get notified of invalid account information
        Given I am on the "Login" screen
        Then I enter "wrong" into the "username_field" field
        Then I enter "wrong" into the "password_field" field
        Then I press the "Login" button
        Then an error message should appear stating "Invalid username/password combination"

    @how_to_demo
    Scenario: As a user I must enter the correct account information in order to login
        Given I am on the "Login" screen
        Then I enter "m2mobi" into the "username_field" field
        Then I enter "password" into the "password_field" field
        Then I press the "Login" button
        Then I should see text containing "Countries"

    @compare_layout
    Scenario: As a developer I want to do a regression test on the layouts
        Given I am on the "Login" screen
        Then I enter "m2mobi" into the "username_field" field
        Then I enter "password" into the "password_field" field
        Then the current screen should resemble the screenshot "layout"
