package com.ssttek.pages;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Getter
public class CartPage extends BasePage{

    @FindBy(xpath = "//button[@data-test-id='cart-remove-item']")
    private List<WebElement> removeButtonsList;

    @FindBy(xpath = "//div[@class='listsummary-content']")
    private List<WebElement> productList;

    @FindBy(xpath = "//span[contains(text(),'Items')]")
    private WebElement itemsIndicator;

}
