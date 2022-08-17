package com.ssttek.pages;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class SignInPage extends BasePage{

    @FindBy(id = "userid")
    private WebElement usernameInput;

    @FindBy(id = "signin-continue-btn")
    private WebElement continueButton;

    @FindBy(id = "pass")
    private WebElement passwordInput;

    @FindBy(id = "sgnBt")
    private WebElement signInButton;

}