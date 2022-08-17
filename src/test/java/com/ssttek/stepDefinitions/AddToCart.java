package com.ssttek.stepDefinitions;

import com.ssttek.pages.CartPage;
import com.ssttek.pages.HomeDecorPage;
import com.ssttek.pages.ProductDetailsPage;
import com.ssttek.pages.SignInPage;
import com.ssttek.utilities.BrowserUtils;
import com.ssttek.utilities.ConfigurationReader;
import com.ssttek.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.LinkedHashSet;
import java.util.Random;

public class AddToCart {

    protected WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 20L);

    protected SignInPage signInPage = new SignInPage();
    protected HomeDecorPage homeDecorPage = new HomeDecorPage();
    protected ProductDetailsPage productDetailsPage = new ProductDetailsPage();
    protected CartPage cartPage = new CartPage();

    private String mainWindowHandle = Driver.getDriver().getWindowHandle();
    private WebElement randomElement;

    @Given("user goes to ebay webpage")
    public void userGoesToEbayWebpage() {
        Driver.getDriver().get(ConfigurationReader.getProperty("ebay_url"));
    }


    @When("User goes to Home_Garden category page")
    public void userGoesToHome_GardenCategoryPage() {
        homeDecorPage.getHomeGardenPageLink().click();
    }

    @And("User goes to Home_Decor page")
    public void userGoesToHome_DecorPage() {
        homeDecorPage.getHomeDecorPageLink().click();
    }

    @And("User clicks to a product image")
    public void userClicksToAProductImage() {
        for (int i = 0; i < 3; i++) {
            wait.until(ExpectedConditions.elementToBeClickable(homeDecorPage.getProductsLinksList().get(0)));
            wait.until(ExpectedConditions.visibilityOfAllElements(homeDecorPage.getProductsLinksList()));
            homeDecorPage.getProductsLinksList().get(new Random().nextInt(homeDecorPage.getProductsLinksList().size())).click();
            Driver.getDriver().switchTo().window(mainWindowHandle);
        }
    }

    @When("User clicks to addToCart button")
    public void userClicksToAddToCartButton() {

        LinkedHashSet<String> windowHandles = (LinkedHashSet<String>) Driver.getDriver().getWindowHandles();

        windowHandles.removeIf(p -> p.equals(mainWindowHandle));

        windowHandles.forEach(p -> {

            Driver.getDriver().switchTo().window(p);

            wait.until(ExpectedConditions.visibilityOf(productDetailsPage.getAddToCartButton()));
            wait.until(ExpectedConditions.elementToBeClickable(productDetailsPage.getAddToCartButton()));

            // sometimes it throws ElementClickInterceptedException with the normal selenium click method. So, I used javascript executor to click
            BrowserUtils.clickWithJS(productDetailsPage.getAddToCartButton());

            // wait for the product adding to cart
            BrowserUtils.waitFor(2);

        });

        Driver.getDriver().switchTo().window(mainWindowHandle);
    }

    @When("User goes to cart")
    public void userGoesToCart() {
        homeDecorPage.getCartButton().click();
    }

    @Then("User should see the product is added")
    public void userShouldSeeTheProductIsAdded() {

        Assert.assertEquals(3, cartPage.getProductList().size());

        Assert.assertEquals("Items (3)", cartPage.getItemsIndicator().getText());
    }

    @When("User removes one item from the cart")
    public void userRemovesOneItemFromTheCart() {

        randomElement = cartPage.getRemoveButtonsList().get(new Random().nextInt(cartPage.getRemoveButtonsList().size()));

        wait.until(ExpectedConditions.elementToBeClickable(randomElement));
        wait.until(ExpectedConditions.visibilityOf(randomElement));

        // sometimes it throws ElementClickInterceptedException with the normal selenium click method. So, I used javascript executor to click
        BrowserUtils.clickWithJS(randomElement);

        //randomElement.click();
    }

    @Then("User should not see the deleted item on the cart")
    public void userShouldNotSeeTheDeletedItemOnTheCart() {

        BrowserUtils.waitForInvisibilityOf(randomElement);
        BrowserUtils.verifyElementNotDisplayed(randomElement);

        // for looking the result by manually
        BrowserUtils.waitFor(5);
    }
}
