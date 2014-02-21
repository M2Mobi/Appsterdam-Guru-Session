require 'calabash-cucumber/calabash_steps'
require 'chunky_png'
include ChunkyPNG::Color

Given /^I am on the login screen$/ do
    check_element_exists("view marked:'LoginScreen'")
    sleep(STEP_PAUSE)
end

Given /^I am on the country screen$/ do
    check_element_exists("view marked:'CountryList'")
    sleep(STEP_PAUSE)
end

Given /^I am on the country detail screen$/ do
    check_element_exists("view marked:'CountryDetail'")
    sleep(STEP_PAUSE)
end

Then /^I scroll to cell with "([^\"]*)" label$/ do |name|
    wait_poll(:until_exists => "label text:'#{name}'", :timeout => 20) do
        scroll("tableView", :down)
    end
end

Then /^I select country "([^\"]*)" as visited$/ do |name|
    wait_poll(:until_exists => "label text:'#{name}'", :timeout => 20) do
        scroll("tableView", :down)
    end
    steps %{
    Then I touch "#{name}"
    Then I toggle the "VisitedSwitch" switch
    Then I touch "Countries"
    }
end

Given /^I passed login feature tests$/ do
        check_element_exists("view marked:'LoginScreen'")
        sleep(STEP_PAUSE)
        steps %{
            Then I enter "m2mobi" into the "username_field" field
            Then I enter "password" into the "password_field" field
            Then I press the "Login" button
    }
end

And /^I press the Login button$/ do
    steps %{
        Then I press the "Login" button
    }
end

Then /^an error message should appear stating "([^\"]*)"$/ do |name|
    steps %{
		Then I should see text containing "#{name}"
    }
end
