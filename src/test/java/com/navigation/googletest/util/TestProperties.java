package com.navigation.googletest.util;

import io.github.bonigarcia.wdm.config.Architecture;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestProperties {
    
    /** The driver. */
    private final WebDriver driver = null;
    
    /** The logger. */
    private final Logger logger = LogManager.getLogger(this.getClass());

    /** The Constant CHROME. */
    private static final String CHROME = "chrome";

    /** The Constant FIREFOX. */
    private static final String FIREFOX = "firefox";

    /** The Constant EDGE. */
    private static final String EDGE = "edge";

    /** The Constant IE. */
    private static final String IE = "ie";

    /** check Config. */
    private static final String CHECK_CONFIG = "Unrecognized browserName specified, please check environment.properties or your testng.xml configuration: ";
    
    protected WebDriver getDriver(final String browserType) {
        switch (browserType.toLowerCase()) {
        case CHROME:
            return initializeChrome();
        case FIREFOX:
            return initializeFirefox();
        case IE:
            return initializeIE();
        case EDGE:
            return initializeEdge();
        default:
            logger.error(CHECK_CONFIG + browserType);
        }
        return driver;
    }

    private ChromeDriver initializeChrome() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }

    private FirefoxDriver initializeFirefox() {
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver();
    }

    private InternetExplorerDriver initializeIE() {
        WebDriverManager.iedriver().architecture(Architecture.X32).setup();
        return new InternetExplorerDriver(getIEOptions());
    }

    private InternetExplorerOptions getIEOptions() {
        InternetExplorerOptions options = new InternetExplorerOptions();
        options.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "");
        options.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        return options;
    }

    private EdgeDriver initializeEdge() {
        WebDriverManager.edgedriver().setup();
        return new EdgeDriver();
    }
}
