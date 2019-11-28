package com.navigation.googletest.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.util.concurrent.Uninterruptibles;

public class CommonUtil {
    private static Logger log = LogManager.getLogger(CommonUtil.class.getName());
    
    private CommonUtil() {

    }

    /**
     * Wait for element is clickable.
     *
     * @param driver
     *            the driver
     * @param we
     *            the we
     */
    public static void waitForElementIsClickable(final WebDriver driver, final WebElement we) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(we));
    }

    /**
     * Type in text box.
     *
     * @param we
     *            the we
     * @param value
     *            the value
     * @param driver
     *            the driver
     */
    public static void typeInTextBox(final WebElement we, final String value, final WebDriver driver) {
        waitForElementIsClickable(driver, we);
        highlightWebElement(we, driver);
        we.sendKeys(value);
    }

    /**
     * Click button.
     *
     * @param element
     *            the element
     * @param driver
     *            the driver
     */
    public static void clickButton(final WebElement element, final WebDriver driver) {
        waitForElementIsClickable(driver, element);
        element.click();
    }

    /**
     * Clear web element.
     *
     * @param element
     *            the element
     * @param driver
     *            the driver
     */
    public static void clearWebElement(final WebElement element, final WebDriver driver) {
        waitForElementIsClickable(driver, element);
        element.clear();
    }

    /**
     * Highlight web element.
     *
     * @param element
     *            the element
     * @param driver
     *            the driver
     */
    private static void highlightWebElement(final WebElement element, final WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].style.border='3px dotted blue'", element);
    }

    /**
     * Wait time.
     *
     * @param waitTime
     *            the wait time
     */
    public static void waitTime(final long waitTime) {
        Uninterruptibles.sleepUninterruptibly(waitTime, TimeUnit.MILLISECONDS);
    }

    /**
     * Write text file.
     *
     * @param filePath
     *            the file path
     * @param outputMessageText
     *            the output message text
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public static void writeTextFile(final String filePath, final String outputMessageText) throws IOException {
        try (FileOutputStream writeFile = new FileOutputStream(filePath)) {
            writeFile.write(outputMessageText.getBytes());
        } catch (IOException e) {
            log.error("Unable to write file ", e);
        }
    }

    public static void writeAndAppendTextFile(final String filePath, final String outputMessageText) throws IOException {
            try {
                Files.write(Paths.get(filePath), outputMessageText.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                log.error("Unable to append file ", e);
            }
    }
}
