Feature: It is requested to create a scenario using Selenium and Gauge on ebay.com
  From product details within the sub-category (home decor) in the home and garden category at ebay.com;

  Background:
   Given user goes to ebay webpage
   ##Then User clicks to Sign in link and goes to sign in page
   ##And User enters a valid email
   ##And User clicks to Continue button
   ##And User enters a valid password
   ##And User clicks to Sign in button
   ##Then User should be signed in successfully

    Scenario: Verify that User should be add 3 products to cart
      When User goes to Home_Garden category page
      And User goes to Home_Decor page
      And User clicks to a product image
      When User clicks to addToCart button
      When User goes to cart
      Then User should see the product is added