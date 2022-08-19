package com.ssttech.pages;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class ProductDetailsPage extends BasePage {
    @FindBy(id = "isCartBtn_btn")
    private WebElement addToCartButton;
}
