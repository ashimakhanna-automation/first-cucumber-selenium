#Author: ashimakhanna@gmail.com
@GoogleSearch
Feature: Google Search Verification
  Search a string on google and verify the window title

  Background: User navigates google home page
    Given I am on the "Google" page on URL "http://www.google.com/"
    Then I should see the "Google Search" button

  Scenario Outline: Successful search on google
    When I fill in "Search" with "<Search String>"
    And I click on the "Google Search" button
    Then I should see page having title "<Search String> - Google Search"
    
    Examples:
    |Search String|
    |Accenture|
    |Ashima Khanna|
    |Selenium Automation|