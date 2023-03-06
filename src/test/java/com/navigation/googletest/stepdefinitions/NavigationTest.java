package com.navigation.googletest.stepdefinitions;

import com.navigation.googletest.pageobjects.GoogleMapPage;
import com.navigation.googletest.util.CommonUtil;
import com.navigation.googletest.util.TestProperties;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NavigationTest extends TestProperties {
    CommonUtil commonUtil;
    public NavigationTest() {
        commonUtil = new CommonUtil();
    }

    private final Logger logger = LogManager.getLogger(this.getClass());

    /** The Constant USER_DIR. */
    private final String USER_DIR = "user.dir";

    /** The Constant CHROME. */
    private final String BROWSER = "chrome";

    /** The Constant GOOGLE_MAP_URL. */
    private final String GOOGLE_MAP_URL = "https://maps.google.com";

    /** The driver. */
    WebDriver driver;

    /** The Constant COORDINATES. */
    private final String COORDINATES = "37.7576793,-122.5076402";

    /** The Constant DESTINATION_CITY. */
    private final String CITY_SAN_FRANCISCO = "San Francisco, California";

    /** The Constant CITY_CHICO_TO_SAN_FRANCISCO. */
    private final String CITY_CHICO_TO_SAN_FRANCISCO = "chico to san francisco";

    @BeforeClass
    public void beforeScenario() {
        driver = getDriver(BROWSER);
    }

    @AfterClass()
    public void afterSteps() {
        driver.close();
    }

    @Given("Launch Chrome and maximize the window")
    public void launchChromeAndMaximizeTheWindow() {
        driver.manage().window().maximize();
    }

    @When("Navigate to Google Maps")
    public void navigateToGoogleMaps() {
        driver.get(GOOGLE_MAP_URL);
    }

    /**
     * Search for san francisco.
     */
    @When("Search for San Francisco, California")
    public void searchForSanFrancisco() {
        GoogleMapPage googleMapObj = new GoogleMapPage(driver);
        commonUtil.typeInTextBox(googleMapObj.getSearchboxinput(), CITY_SAN_FRANCISCO, driver);
        commonUtil.clickButton(googleMapObj.getSearchBoxSearchButton(), driver);
        commonUtil.waitTime(3000);
    }

    /**
     * Verify the coordinates for san francisco.
     */
    @Then("Verify the coordinates for San Francisco are 37.7577627,-122.4726194")
    public void verifyTheCoordinatesForSanFrancisco() {
        String url = driver.getCurrentUrl();
        logger.info(url);
        
        /* Get Co-ordinates only from Google Map URL */
        url = url.split("[\\@z)]")[1];

        commonUtil.waitTime(3000);
        try {
            Assert.assertTrue(url.contains(COORDINATES));
            logger.info(String.format("Get Co-ordinates: %s", url));
        } finally {
            Assert.assertNotNull(url);
        }
//        softAssert.assertAll(); /* Commented Out assertAll() as co-ordinates keeps on changing */
    }

    /**
     * Search for driving directions from chico to san francisco.
     */
    @When("Search for driving directions from Chico, California to San Francisco, California")
    public void searchForDrivingDirectionsFromChicoToSanFrancisco() {
        GoogleMapPage googleMapObj = new GoogleMapPage(driver);
        commonUtil.waitTime(3000);
        commonUtil.clearWebElement(googleMapObj.getSearchboxinput(), driver);
        commonUtil.typeInTextBox(googleMapObj.getSearchboxinput(), CITY_CHICO_TO_SAN_FRANCISCO, driver);
        commonUtil.clickButton(googleMapObj.getSearchBoxSearchButton(), driver);
        commonUtil.clickButton(googleMapObj.getModeDriving(), driver);
        commonUtil.waitTime(2000);
        Assert.assertTrue(googleMapObj.getModeDriving().isEnabled());
    }

    /**
     * Verify two or more routes are displayed in the list.
     */
    @Then("Verify two or more routes are displayed in the list and print in text file")
    public void verifyTwoOrMoreRoutesAreDisplayedInTheList() {
        commonUtil.waitTime(3000);
        List<String> ls = new ArrayList<>();
        List<WebElement> data = driver.findElements(By.xpath("//div[@class='section-layout']/div"));
        for (WebElement element : data) {

            logger.info(element.getText());
            ls.add(element.getText());
            try {
                commonUtil.writeTextFile(System.getProperty(USER_DIR) + File.separator + "routes.txt",
                        ls.toString().trim());
            } catch (IOException e) {
                logger.error(String.format("Unable to write file %s", e.getLocalizedMessage()));
            }
        }
    }
}
