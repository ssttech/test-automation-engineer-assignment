package com.ssttech.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Making abstract this class and implementing encapsulation, so we are closing
 * access to the object of this class from outside the class
 */
public abstract class Driver {

    /**
     * We make WebDriver private, because we want to close access from outside the class.
     * We make it static because we will use it in a static method.
     */
    //private static WebDriver driver; // value is null by default

    private static final InheritableThreadLocal<WebDriver> driverPool = new InheritableThreadLocal<>();

    /**
     * Create a re-usable utility method which will return same driver instance when we call it
     */
    public static WebDriver getDriver() {

        if (driverPool.get() == null) {

            /*
            We read our browserType from configuration.properties.
            This way, we can control which browser is opened from outside our code, from configuration.properties.
             */
            String browserType = ConfigurationReader.getProperty("browser");

            /*
                Depending on the browserType that will be return from configuration.properties file
                switch statement will determine the case, and open the matching browser
            */
            switch (browserType) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driverPool.set(new ChromeDriver(getChromeOptions(false, "disable-infobars", "disable-popup-blocking", "start-maximized")));
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driverPool.set(new FirefoxDriver());
                    break;
                case "safari":
                    WebDriverManager.safaridriver().setup();
                    driverPool.set(new SafariDriver());
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driverPool.set(new EdgeDriver());
                    break;
                case "opera":
                    WebDriverManager.operadriver().setup();
                    driverPool.set(new OperaDriver());
                    break;
            }
            driverPool.get().manage().window().maximize();
            driverPool.get().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        }

        return driverPool.get();

    }

    /**
     * This method returns chromeOptions after specified
     */
    private static ChromeOptions getChromeOptions(boolean isHeadless, String... args) {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments(args);
        options.setHeadless(isHeadless);
        return options;
    }

    /**
     * This method will make sure our driver value is always null after using quit() method
     */
    public static void closeDriver() {
        if (driverPool.get() != null) {
            driverPool.get().quit(); // this line will terminate the existing session. value will not even be null
            driverPool.remove();
        }
    }
}