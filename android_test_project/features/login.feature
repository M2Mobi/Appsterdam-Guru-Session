@feature_login
Feature: Login
    In order access the app
    As an authenticated user
    I want to be able to login

    Scenario: As a user I must enter a username in order to login
        Given I am on the "Login" screen
        Then I press the "Login" button
        Then I should see text containing "Invalid username"

    Scenario: As a user I must enter a username in order to login
        Given I am on the "Login" screen
        Then I clear input field with id "login_username"
        Then I enter text "example username" into field with id "login_username"
        Then I press the "Login" button
        Then I should see text containing "Invalid password"

    Scenario: As a user I get notified of invalid account information
        Given I am on the "Login" screen
        Then I clear input field with id "login_username"
        Then I enter text "wrong" into field with id "login_username"
        Then I clear input field with id "login_password"
        Then I enter text "wrong" into field with id "login_password"
        Then I press the "Login" button
        Then I should see text containing "invalid username/password combination"

    @how_to_demo
    Scenario: As a user I must enter the correct account information in order to login
        Given I am on the "Login" screen
        Then I clear input field with id "login_username"
        Then I enter text "guru" into field with id "login_username"
        Then I clear input field with id "login_password"
        Then I enter text "secret" into field with id "login_password"
        Then I press the "Login" button
        Then I should see text containing "Country list"

    @compare_layout
    Scenario: As a developer I want to do a regression test on the layouts
        Given I am on the "Login" screen
        Then the current screen should resemble the screenshot "login_screen"