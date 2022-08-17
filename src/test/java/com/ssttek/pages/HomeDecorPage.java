package com.ssttek.pages;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Getter
public class HomeDecorPage extends BasePage{

    @FindBy(xpath = "//section[@id='s0-27_2-9-0-1[0]-0-0']//div[@class='s-item__image']")
    private List<WebElement> productsLinksList;
}
