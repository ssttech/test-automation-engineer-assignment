package com.ssttech.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class Driver {

    private static final String BROWSER = System.getProperty("browser") != null
            ? System.getProperty("browser")
            : ConfigurationReader.getProperty("browser");

    private static final InheritableThreadLocal<WebDriver> DRIVER_POOL = new InheritableThreadLocal<>();

    public static final Logger LOGGER = LoggerFactory.getLogger(Driver.class);

    private Driver() {
    }

    /**
     * Retrieves the WebDriver instance from the driver pool.
     * If no instance is available, a new one is created and set in the pool.
     *
     * @return the WebDriver instance for the current thread
     */
    public static WebDriver getDriver() {
        // Check if the driver pool is empty for the current thread
        if (DRIVER_POOL.get() == null) {
            // Create and set a new WebDriver instance in the pool
            DRIVER_POOL.set(createDriver());
        }
        // Return the WebDriver instance from the pool
        return DRIVER_POOL.get();
    }

    /**
     * Creates a WebDriver instance based on the specified browser type.
     * The browser type is read from the configuration properties.
     *
     * @return a WebDriver instance configured for the specified browser
     */
    private static WebDriver createDriver() {
        LOGGER.info("Thread ID: {}, Starting Driver initialization for browser: {}", Thread.currentThread().getId(), BROWSER);

        // Determine which WebDriver to initialize based on the browser type
        return switch (BROWSER.toLowerCase()) {
            case "remote" -> setupRemoteDriver(); // Setup a remote WebDriver for grid execution
            case "chrome" -> setupChromeDriver(); // Setup a local Chrome WebDriver
            case "firefox" -> setupFirefoxDriver(); // Setup a local Firefox WebDriver
            case "safari" -> setupSafariDriver(); // Setup a local Safari WebDriver
            default -> {
                LOGGER.error("Unknown browser type : {}", BROWSER);
                // Throw an exception for unsupported browser types
                throw new IllegalArgumentException("Unknown browser type : " + BROWSER);
            }
        };
    }

    /**
     * Create a remote {@link WebDriver} instance to interact with the grid.
     *
     * @return a remote {@link WebDriver} instance
     */
    private static WebDriver setupRemoteDriver() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(Browser.CHROME.browserName());

        // Specify the URL of the grid hub
        URL url;
        try {
            url = new URL("http://localhost:4444/wd/hub");
        } catch (MalformedURLException e) {
            throw new RuntimeException("Grid URL is invalid!", e);
        }
        // Create the remote driver
        return new RemoteWebDriver(url, capabilities);
    }


    private static WebDriver setupChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        String chromeOptions = ConfigurationReader.getProperty("chrome.options");
        if (chromeOptions != null && !chromeOptions.isEmpty()) {
            options.addArguments(chromeOptions.split(","));
        }
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        return new ChromeDriver(options);
    }

    private static WebDriver setupFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        String firefoxOptions = ConfigurationReader.getProperty("firefox.options");
        if (firefoxOptions != null && !firefoxOptions.isEmpty()) {
            options.addArguments(firefoxOptions.split(","));
        }
        return new FirefoxDriver(options);
    }

    private static WebDriver setupSafariDriver() {
        return new SafariDriver();
    }

    private static void setDriverDefaults(WebDriver driver) {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public static void closeDriver() {
        if (DRIVER_POOL.get() != null) {
            try {
                DRIVER_POOL.get().quit();
            } catch (Exception e) {
                LOGGER.error("Error occurred while closing the WebDriver instance", e);
            } finally {
                DRIVER_POOL.remove();
            }
        }
    }
}