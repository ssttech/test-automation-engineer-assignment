package com.ssttech.pages;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

@Getter
public class HomeDecorPage extends BasePage {

    @FindBy(xpath = "//section[@id='s0-27_2-9-0-1[0]-0-0']//div[@class='s-item__image']")
    private List<WebElement> productsLinksList;

    @FindBy(xpath = "//section[@id='s0-27_2-9-0-1[0]-0-0']//div[@class='s-item__image']/../following-sibling::div//h3")
    private List<WebElement> addedToCartProductsLinksList;

    @FindBy(xpath = "//button[@class='s-item__watchheart-click']")
    private List<WebElement> likeButtonsList;

    @FindBy(xpath = "//span[@class='s-item__watchheart']/a")
    private List<WebElement> likeLinksList;

    @FindBy(xpath = "//button[@class='s-item__watchheart-click']/../preceding-sibling::div//h3")
    private List<WebElement> likedProductsList; // use getText()

    @FindBy(xpath = "//span[@class='s-item__watchheart']/preceding-sibling::div//h3")
    private List<WebElement> likedProductsListFromLinks; // use getText()

}
