package com.ssttek.pages;

import com.ssttek.utilities.Driver;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class BasePage {

    @FindBy(xpath = "//span[@id='gh-ug']/a")
    private WebElement signInPageLink;

    @FindBy(linkText = "Sign in")
    private WebElement signInPageLink2;

    @FindBy(id = "gh-ug")
    private WebElement hiUsernameExpendableMenu;

    @FindBy(xpath = "(//a[contains(text(),'Home & Garden')])[1]")
    private WebElement homeGardenPageLink;

    @FindBy(xpath = "//div[contains(text(),'Home Décor')]")
    private WebElement homeDecorPageLink;

    @FindBy(xpath = "//li[@id='gh-minicart-hover']//a[1]")
    private WebElement cartButton;

    public BasePage() {
        PageFactory.initElements(Driver.getDriver(),this);
    }
}
