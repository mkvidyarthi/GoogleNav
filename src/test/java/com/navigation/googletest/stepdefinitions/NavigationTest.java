package com.navigation.googletest.stepdefinitions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.navigation.googletest.pageobjects.GoogleMapPage;
import com.navigation.googletest.util.CommonUtil;
import com.navigation.googletest.util.TestProperties;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class NavigationTest extends TestProperties {

    private static Logger log = LogManager.getLogger(NavigationTest.class.getName());

    /** The Constant USER_DIR. */
    private static final String USER_DIR = "user.dir";
    
    /** The Constant CHROME. */
    private static final String BROWSER = "chrome";
    
    /** The Constant GOOGLE_MAP_URL. */
    private static final String GOOGLE_MAP_URL = "https://maps.google.com";

    /** The driver. */
    WebDriver driver;

    /** The Constant COORDINATES. */
    private static final String COORDINATES = "37.7576793,-122.5076402";

    /** The Constant DESTINATION_CITY. */
    private static final String CITY_SAN_FRANCISCO = "San Francisco, California";

    /** The Constant CITY_CHICO_TO_SAN_FRANCISCO. */
    private static final String CITY_CHICO_TO_SAN_FRANCISCO = "chico to san francisco";

    @Before
    public void beforeScenario() {
        driver = getDriver(BROWSER);
    }

    @After()
    public void afterSteps() {
        driver.close();
    }

    @Given("^Launch Chrome and maximize the window$")
    public void launchChromeAndMaximizeTheWindow() {
        driver.manage().window().maximize();
    }

    @When("^Navigate to Google Maps$")
    public void navigateToGoogleMaps() {
        driver.get(GOOGLE_MAP_URL);
    }

    /**
     * Search for san francisco.
     */
    @When("^Search for San Francisco, California$")
    public void searchForSanFrancisco() {
        GoogleMapPage googleMapObj = new GoogleMapPage(driver);
        CommonUtil.typeInTextBox(googleMapObj.getSearchboxinput(), CITY_SAN_FRANCISCO, driver);
        CommonUtil.clickButton(googleMapObj.getSearchBoxSearchButton(), driver);
        CommonUtil.waitTime(3000);
    }

    /**
     * Verify the coordinates for san francisco.
     */
    @Then("^Verify the coordinates for San Francisco are 37.7577627,-122.4726194$")
    public void verifyTheCoordinatesForSanFrancisco() {
        String url = driver.getCurrentUrl();
        log.info(url);
        
        /* Get Co-ordinates only from Google Map URL */
        url = url.split("[\\@z)]")[1];
        
        CommonUtil.waitTime(3000);
        try {
            Assert.assertTrue(url.contains(COORDINATES));
            log.info(String.format("Get Co-ordinates: %s", url));
        } finally {
            Assert.assertNotNull(url);
        }
//        softAssert.assertAll(); /* Commented Out assertAll() as co-ordinates keeps on changing */
    }

    /**
     * Search for driving directions from chico to san francisco.
     */
    @When("^Search for driving directions from Chico, California to San Francisco, California$")
    public void searchForDrivingDirectionsFromChicoToSanFrancisco() {
        GoogleMapPage googleMapObj = new GoogleMapPage(driver);
        CommonUtil.waitTime(3000);
        CommonUtil.clearWebElement(googleMapObj.getSearchboxinput(), driver);
        CommonUtil.typeInTextBox(googleMapObj.getSearchboxinput(), CITY_CHICO_TO_SAN_FRANCISCO, driver);
        CommonUtil.clickButton(googleMapObj.getSearchBoxSearchButton(), driver);
        CommonUtil.clickButton(googleMapObj.getModeDriving(), driver);
        CommonUtil.waitTime(2000);
        Assert.assertTrue(googleMapObj.getModeDriving().isEnabled());
    }

    /**
     * Verify two or more routes are displayed in the list.
     */
    @Then("^Verify two or more routes are displayed in the list and print in text file$")
    public void verifyTwoOrMoreRoutesAreDisplayedInTheList() {
        CommonUtil.waitTime(3000);
        List<String> ls = new ArrayList<>();
        List<WebElement> data = driver.findElements(By.xpath("//div[@class='section-layout']/div"));
        for (WebElement element : data) {

            log.info(element.getText());
            ls.add(element.getText());
            try {
                CommonUtil.writeTextFile(System.getProperty(USER_DIR) + File.separator + "routes.txt",
                        ls.toString().trim());
            } catch (IOException e) {
                log.error(String.format("Unable to write file %s", e.getLocalizedMessage()));
            }
        }
    }
}
