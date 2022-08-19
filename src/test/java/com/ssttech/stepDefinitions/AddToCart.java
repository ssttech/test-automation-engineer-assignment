package com.ssttech.stepDefinitions;

import com.ssttech.pages.CartPage;
import com.ssttech.pages.HomeDecorPage;
import com.ssttech.pages.ProductDetailsPage;
import com.ssttech.utilities.BrowserUtils;
import com.ssttech.utilities.ConfigurationReader;
import com.ssttech.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

public class AddToCart {

    protected WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 20L);

    protected HomeDecorPage homeDecorPage = new HomeDecorPage();
    protected ProductDetailsPage productDetailsPage = new ProductDetailsPage();
    protected CartPage cartPage = new CartPage();

    private String mainWindowHandle = Driver.getDriver().getWindowHandle();
    private WebElement randomElement;
    private List<String> likedProductsNames = new ArrayList<>();
    private List<String> addedToCartProductsNames = new ArrayList<>();

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

            int random = new Random().nextInt(homeDecorPage.getProductsLinksList().size());
            homeDecorPage.getProductsLinksList().get(random).click();
            addedToCartProductsNames.add(homeDecorPage.getAddedToCartProductsLinksList().get(random).getText());

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
            BrowserUtils.waitFor(3);

        });

        Driver.getDriver().switchTo().window(mainWindowHandle);
    }

    @When("User goes to cart")
    public void userGoesToCart() {
        homeDecorPage.getCartButton().click();
    }

    @Then("User should see the products are added")
    public void userShouldSeeTheProductsAreAdded() {

        // (Sometimes) The names of some products show slight differences in the cart after they are added.
        // I think it is a bug.
        List<String> productNamesText = BrowserUtils.getElementsText(cartPage.getProductsNamesList());
        addedToCartProductsNames.forEach(each ->
                Assert.assertTrue("Names don't match! Expected: " + each + " Actual: " + productNamesText, productNamesText.contains(each)));

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

    }

    @Then("User should not see the deleted item on the cart")
    public void userShouldNotSeeTheDeletedItemOnTheCart() {

        BrowserUtils.waitForInvisibilityOf(randomElement);
        BrowserUtils.verifyElementNotDisplayed(randomElement);

        // for looking the result by manually
        BrowserUtils.waitFor(5);
    }

    @When("User likes {int} different products")
    public void userLikes3DifferentProducts(int count) {

        // creating some random numbers for selecting random products to add to the watchlist
        List<Integer> nums = new ArrayList<>();
        int random;
        while (nums.size() < 3) {
            if (homeDecorPage.getLikeButtonsList().size() > 0)
                random = new Random().nextInt(homeDecorPage.getLikeButtonsList().size());
            else
                random = new Random().nextInt(homeDecorPage.getLikeLinksList().size());
            if (!nums.contains(random))
                nums.add(random);
        }

        for (int i = 0; i < count; i++) {

            // there are two different page(environments) to be shown to the user. It changes accordingly on time/intensity etc.
            if (homeDecorPage.getLikeButtonsList().size() > 0) {

                // like one of the product from the table randomly
                BrowserUtils.clickWithJS(homeDecorPage.getLikeButtonsList().get(nums.get(i)));
                // adding to list the name of product that liked
                likedProductsNames.add(homeDecorPage.getLikedProductsList().get(nums.get(i)).getText());
            } else {

                // like one of the product from the table randomly
                BrowserUtils.clickWithJS(homeDecorPage.getLikeLinksList().get(nums.get(i)));
                // adding to list the name of product that liked
                likedProductsNames.add(homeDecorPage.getLikedProductsListFromLinks().get(nums.get(i)).getText());
            }

            // wait for the product adding to watchlist process is getting done
            BrowserUtils.waitFor(2);
        }
    }

    @And("User clicks to Watchlist link")
    public void userClicksToWatchlistLink() {
        BrowserUtils.waitFor(2);
        BrowserUtils.clickWithTimeOut(homeDecorPage.getWatchListButton(), 5);
    }

    @Then("User should see the liked products in the Watchlist")
    public void userShouldSeeTheLikedProductsInTheWatchlist() {

        List<String> productsInWatchListText = BrowserUtils.getElementsText(homeDecorPage.getProductsInWatchList());

        likedProductsNames.forEach(each ->
                Assert.assertTrue(("Names don't match. Expected: " + likedProductsNames + ", \nActual: " + productsInWatchListText), productsInWatchListText.contains(each)));

        // (Sometimes) The names of some products show slight differences in the watchlist after they are added.
        // I think it is a bug.
    }
}
