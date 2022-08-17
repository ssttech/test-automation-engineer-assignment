package com.ssttek.stepDefinitions;

import com.ssttek.pages.HomeDecorPage;
import com.ssttek.pages.SignInPage;
import com.ssttek.utilities.BrowserUtils;
import com.ssttek.utilities.ConfigurationReader;
import com.ssttek.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignInSteps {

    protected WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 20L);

    private HomeDecorPage homeDecorPage= new HomeDecorPage();
    private SignInPage signInPage = new SignInPage();

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
}
