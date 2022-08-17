package com.ssttek.stepDefinitions;

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
import org.openqa.selenium.By;
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

    private static final String mainWindowHandle = Driver.getDriver().getWindowHandle();

    @Given("user goes to ebay webpage")
    public void userGoesToEbayWebpage() {
        Driver.getDriver().get(ConfigurationReader.getProperty("ebay_url"));
    }

    @Then("User clicks to Sign in link and goes to sign in page")
    public void userClicksToSignInLinkAndGoesToSignInPage() {
        homeDecorPage.getSignInPageLink().click();
    }

    @And("User enters a valid email")
    public void userEntersAValidEmail() {
        signInPage.getUsernameInput().sendKeys(ConfigurationReader.getProperty("email"));
    }

    @And("User clicks to Continue button")
    public void userClicksToContinueButton() {
        signInPage.getContinueButton().click();
    }

    @And("User enters a valid password")
    public void userEntersAValidPassword() {
        signInPage.getPasswordInput().sendKeys("password");
    }

    @And("User clicks to Sign in button")
    public void userClicksToSignInButton() {
        signInPage.getSignInButton().click();
    }

    @Then("User should be signed in successfully")
    public void userShouldBeSignedInSuccessfully() {
        BrowserUtils.hover(signInPage.getHiUsernameExpendableMenu());
        wait.until(ExpectedConditions.visibilityOf(Driver.getDriver().findElement(By.linkText("Account settings"))));
        BrowserUtils.verifyElementDisplayed(By.linkText("Account settings"));
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
            productDetailsPage.getAddToCartButton().click();
        });

        Driver.getDriver().switchTo().window(mainWindowHandle);
    }

    @When("User goes to cart")
    public void userGoesToCart() {
        homeDecorPage.getCartButton().click();
    }

    @Then("User should see the product is added")
    public void userShouldSeeTheProductIsAdded() {

        int productCountOnCart = Driver.getDriver().findElements(By.xpath("//div[@class='listsummary-content']")).size();
        WebElement itemsCount = Driver.getDriver().findElement(By.xpath("//span[contains(text(),'Items')]"));


        Assert.assertEquals(3, productCountOnCart);

        Assert.assertEquals("Items (3)", itemsCount.getText());
    }
}
