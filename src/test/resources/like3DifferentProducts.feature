@regression
Feature: Like 3 different product from the list

  Background:
    Given user goes to ebay webpage
    Then User clicks to Sign in link and goes to sign in page
    And User enters a valid email
    And User clicks to Continue button
    And User enters a valid password
    And User clicks to Sign in button
    Then User should be signed in successfully

  @tc3
  Scenario: Verify that User should be able to like 3 different products
    When User goes to Home_Garden category page
    And User goes to Home_Decor page
    When User likes 3 different products
    And User clicks to Watchlist link
    Then User should see the liked products in the Watchlist

    # Sometimes the webpage of the ebay asking captcha problems.
    # Therefore the TC is going to be FAILED