package com.ssttech.utilities;

import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface BrowserUtils {

    static String getCurrentUrl() {
        return Driver.getDriver().getCurrentUrl();
    }

    /**
     * This method will wait throughout the given time.
     *
     * @param second : time in seconds to wait explicitly
     */
    static void sleep(int second) {
        try {
            Thread.sleep(second * 1000L);
        } catch (InterruptedException ignored) {

        }
    }


    /**
     * This method will wait until the given element is visible.
     *
     * @param element : element to wait for visibility of it
     */
    static void waitForClickability(WebElement element) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 10);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }


    /**
     * This method looks for if the given URL and Title are present in the browser's tabs.
     *
     * @param expectedInUrl    : expected URL , for verify if the url contains given String.
     *                         - If condition matches, will break loop.
     * @param expectedInTitle: expected Title, expectedInTitle to be compared against actualTitle
     */
    static void switchWindowAndVerify(String expectedInUrl, String expectedInTitle) {

        Set<String> allWindowsHandles = Driver.getDriver().getWindowHandles();

        for (String each : allWindowsHandles) {

            Driver.getDriver().switchTo().window(each);

            System.out.println("Current URL: " + Driver.getDriver().getCurrentUrl());

            if (Driver.getDriver().getCurrentUrl().contains(expectedInUrl)) {
                break;
            }
        }

        //5. Assert:Title contains "expectedInTitle"
        String actualTitle = Driver.getDriver().getTitle();
        Assert.assertTrue(actualTitle.contains(expectedInTitle));
    }


    /**
     * This method accepts a String "expectedTitle" and Asserts if it is true
     *
     * @param expectedTitle: expected title to be compared against actualTitle
     */
    static void verifyTitle(String expectedTitle) {
        Assert.assertEquals(Driver.getDriver().getTitle(), expectedTitle);
    }


    /**
     * Creating a utility method for ExplicitWait, so we don't have to repeat the lines
     *
     * @param webElement : This method will accept a WebElement
     */
    static void waitForInvisibilityOf(WebElement webElement) {
        //Driver.getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), 10);
        wait.until(ExpectedConditions.invisibilityOf(webElement));
    }


    /**
     * This method will accept a String "expectedText" and Asserts if it is true
     *
     * @param expectedInUrl : This method will accept a String "expectedText"
     */
    static void verifyURLContains(String expectedInUrl) {
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains(expectedInUrl));
    }


    /**
     * This method returns a list of strings of given dropdown element's options
     *
     * @param dropdown : This method will accept a WebElement which can be assumed to be a dropdown
     * @return : List<String> of dropdown element's options
     */
    static List<String> dropdownOptionsAsStringList(WebElement dropdown) {
        Select select = new Select(dropdown);
        return select.getOptions()
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }


    /**
     * This method will accept a WebElement which can be used to a Select and selects the given option
     *
     * @param dropdown : This method will accept a WebElement which can be assumed to be a dropdown
     * @param option   : This method will accept a String "option"
     */
    static void selectAnOptionFromDropdown(WebElement dropdown, String option) {
        Select select = new Select(dropdown);
        select.selectByVisibleText(option);
    }

    /**
     * This method will accept a WebElement which can be used to a Select
     *
     * @param productDropdown: This method will accept a WebElement which can be assumed to be a dropdown
     * @return This method will return the first selected option given element
     */
    static WebElement getFirstSelectedOption(WebElement productDropdown) {
        Select select = new Select(productDropdown);
        return select.getFirstSelectedOption();
    }


    /**
     * This method selects the given value from a list of RadioButtons
     * It compares text of the radio button with the given value
     *
     * @param radioButtons : List of RadioButtons
     * @param value:       String value of will be selected radio button
     */
    static void clickRadioButton(@NotNull List<WebElement> radioButtons, String value) {
        radioButtons.stream()
                .filter(each -> each.getText().equalsIgnoreCase(value))
                .findFirst()
                .ifPresent(WebElement::click);
    }

    /**
     * Switches to new window by the exact title. Returns to original window if target title not found
     *
     * @param targetTitle : Title of the window to switch to
     */
    static void switchToWindow(String targetTitle) {
        String origin = Driver.getDriver().getWindowHandle();
        for (String handle : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(handle);
            if (Driver.getDriver().getTitle().equals(targetTitle)) {
                return;
            }
        }
        Driver.getDriver().switchTo().window(origin);
    }

    /**
     * Moves the mouse to given element
     *
     * @param element on which to hover
     */
    static void hover(WebElement element) {
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(element).perform();
    }

    /**
     * return a list of string from a list of elements
     *
     * @param list of webelements
     * @return list of string
     */
    static List<String> getElementsText(List<WebElement> list) {
        return list.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Extracts text from list of elements matching the provided locator into new List<String>
     *
     * @param locator : By locator of the elements to extract text from
     * @return list of strings
     */
    static List<String> getElementsText(By locator) {
        return Driver.getDriver().findElements(locator).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Performs a pause
     *
     * @param seconds : number of seconds to pause for
     */
    static void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Waits for the provided element to be visible on the page
     *
     * @param element         : WebElement to wait for
     * @param timeToWaitInSec : number of seconds to wait for
     * @return : WebElement
     */
    static WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeToWaitInSec);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits for element matching the locator to be visible on the page
     *
     * @param locator : By locator of the element to wait for
     * @param timeout : number of seconds to wait for
     * @return : WebElement
     */
    static WebElement waitForVisibility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeout);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits for provided element to be clickable
     *
     * @param element : WebElement to wait for
     * @param timeout : number of seconds to wait for
     */
    static void waitForClickablility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeout);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Waits for element matching the locator to be clickable
     *
     * @param locator : By locator of the element to wait for
     * @param timeout : number of seconds to wait for
     * @return : WebElement
     */
    static WebElement waitForClickablility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeout);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * waits for backgrounds processes on the browser to complete
     *
     * @param timeOutInSeconds : number of seconds to wait for
     */
    static void waitForPageToLoad(long timeOutInSeconds) {
        ExpectedCondition<Boolean> expectation = driver ->
                ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeOutInSeconds);
            wait.until(expectation);
        } catch (Throwable error) {
            error.printStackTrace();
        }
    }

    /**
     * Verifies whether the element matching the provided locator is displayed on page
     *
     * @param by : By locator of the element to verify
     * @throws AssertionError if the element matching the provided locator is not found or not displayed
     */
    static void verifyElementDisplayed(By by) {
        try {
            Assert.assertTrue("Element not visible: " + by, Driver.getDriver().findElement(by).isDisplayed());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            Assert.fail("Element not found: " + by);

        }
    }

    /**
     * Verifies whether the element matching the provided locator is NOT displayed on page
     *
     * @param by : By locator of the element to verify
     * @throws AssertionError the element matching the provided locator is displayed
     */
    static void verifyElementNotDisplayed(By by) {
        try {
            Assert.assertFalse("Element should not be visible: " + by, Driver.getDriver().findElement(by).isDisplayed());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifies whether the element matching the provided locator is NOT displayed on page
     *
     * @param element : WebElement to verify
     * @throws AssertionError the element matching the provided element is displayed
     */
    static void verifyElementNotDisplayed(WebElement element) {
        try {
            Assert.assertFalse("Element should not be visible: " + element, element.isDisplayed());
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            e.printStackTrace();
        }
    }


    /**
     * Verifies whether the element is displayed on page
     *
     * @param element : WebElement to verify
     * @throws AssertionError if the element is not found or not displayed
     */
    static void verifyElementDisplayed(WebElement element) {
        try {
            Assert.assertTrue("Element not visible: " + element, element.isDisplayed());
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            Assert.fail("Element not found: " + element);

        }
    }


    /**
     * Waits for element to be not stale
     *
     * @param element : WebElement to wait for
     */
    static void waitForStaleElement(WebElement element) {
        int y = 0;
        while (y <= 15) {
            if (y == 1)
                try {
                    element.isDisplayed();
                    break;
                } catch (StaleElementReferenceException st) {
                    y++;
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (WebDriverException we) {
                    y++;
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }
    }


    /**
     * Clicks on an element using JavaScript
     *
     * @param element : WebElement to click on
     */
    static void clickWithJS(WebElement element) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", element);
    }


    /**
     * Scrolls down to an element using JavaScript
     *
     * @param element : WebElement to scroll down to
     */
    static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Performs double click action on an element using Actions class
     *
     * @param element : WebElement to double click on
     */
    static void doubleClick(WebElement element) {
        new Actions(Driver.getDriver()).doubleClick(element).build().perform();
    }

    /**
     * Changes the HTML attribute of a Web Element to the given value using JavaScript
     *
     * @param element        : WebElement to change the attribute of
     * @param attributeName  : name of the attribute to change
     * @param attributeValue : value of the attribute to change
     */
    static void setAttribute(WebElement element, String attributeName, String attributeValue) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attributeName, attributeValue);
    }

    /**
     * Highlights an element by changing its background and border color
     *
     * @param element : WebElement to highlight
     */
    static void highlight(WebElement element) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
        waitFor(1);
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].removeAttribute('style', 'background: yellow; border: 2px solid red;');", element);
    }

    /**
     * Checks or unchecks given checkbox
     *
     * @param element : WebElement to check or uncheck
     * @param check   : true to check, false to uncheck
     */
    static void selectCheckBox(WebElement element, boolean check) {
        if (check) {
            if (!element.isSelected()) {
                element.click();
            }
        } else {
            if (element.isSelected()) {
                element.click();
            }
        }
    }

    /**
     * attempts to click on provided element until given time runs out
     *
     * @param element : WebElement to click on
     * @param timeout : number of seconds to wait for
     */
    static void clickWithTimeOut(WebElement element, int timeout) {
        for (int i = 0; i < timeout; i++) {
            try {
                element.click();
                return;
            } catch (WebDriverException e) {
                waitFor(1);
            }
        }
    }

    /**
     * executes the given JavaScript command on given web element
     *
     * @param element : WebElement to execute the command on
     */
    static void executeJScommand(WebElement element, String command) {
        JavascriptExecutor jse = (JavascriptExecutor) Driver.getDriver();
        jse.executeScript(command, element);
    }

    /**
     * executes the given JavaScript command on given web element
     *
     * @param command : JavaScript command to execute
     */
    static void executeJScommand(String command) {
        JavascriptExecutor jse = (JavascriptExecutor) Driver.getDriver();
        jse.executeScript(command);
    }


    /**
     * This method will recover in case of exception after unsuccessful the click,
     * and will try to click on element again.
     *
     * @param by       : By locator of the element to click on
     * @param attempts : number of attempts to click on element
     */
    static void clickWithWait(By by, int attempts) {
        int counter = 0;
        //click on element as many as you specified in attempts parameter
        while (counter < attempts) {
            try {
                //selenium must look for element again
                clickWithJS(Driver.getDriver().findElement(by));
                //if click is successful - then break
                break;
            } catch (WebDriverException e) {
                //if click failed
                //print exception
                //print attempt
                e.printStackTrace();
                ++counter;
                //wait for 1 second, and try to click again
                waitFor(1);
            }
        }
    }

    /**
     * checks that an element is present on the DOM of a page. This does not
     * * necessarily mean that the element is visible.
     *
     * @param by   : By locator of the element to check
     * @param time : number of seconds to wait for
     */
    static void waitForPresenceOfElement(By by, long time) {
        new WebDriverWait(Driver.getDriver(), time).until(ExpectedConditions.presenceOfElementLocated(by));
    }

}