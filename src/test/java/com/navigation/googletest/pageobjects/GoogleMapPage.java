package com.navigation.googletest.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * The Class GoogleMapPage.
 */
public class GoogleMapPage {

    /**
     * Instantiates a new google map page.
     *
     * @param driver the driver
     */
    public GoogleMapPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
      }
    
    /** The search box search button. */
    @FindBy(id="searchbox-searchbutton")
    private WebElement searchBoxSearchButton;
    
    /** The search box input. */
    @FindBy(id = "searchboxinput")
    private WebElement searchBoxInput;

    /** The direction button. */
    @FindBy(xpath = "//button[@data-value='Directions']")
    private WebElement directionButton;
    
    /** The mode driving. */
    @FindBy(xpath="//div[@data-tooltip='Driving']")
    private WebElement modeDriving;
    
    /**
     * Gets the mode driving.
     *
     * @return the mode driving
     */
    public WebElement getModeDriving() {
        return modeDriving;
    }

    /**
     * Gets the searchboxinput.
     *
     * @return the searchboxinput
     */
    public WebElement getSearchboxinput() {
        return searchBoxInput;
    }
    
    /**
     * Gets the search box search button.
     *
     * @return the search box search button
     */
    public WebElement getSearchBoxSearchButton() {
        return searchBoxSearchButton;
    }
    
    /**
     * Gets the direction button.
     *
     * @return the direction button
     */
    public WebElement getDirectionButton() {
        return directionButton;
    }
}
