package pages;

import base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Tools;

import java.time.Duration;

public class StoreLocatorPage {
    private final WebDriver driver;
    private final Tools tools;
    private final WebDriverWait wait;

    // Locators
    private final By storeLocatorLink = By.linkText("Hệ thống cửa hàng");
    private final By searchLocationInput = By.id("locations");
    private final By dropdownCity = By.xpath("//select[@name='change-tinh']");
    private final By storeListHeader = By.xpath("(//h1[contains(text(),'Hệ thống cửa hàng')])[1]");

    public StoreLocatorPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.tools = new Tools(driver,wait);
        this.wait = wait;
    }

    // Actions
    public void navigateToStoreLocator() {
        driver.findElement(storeLocatorLink).click();
    }

    public void selectCity(String cityName) {
        WebElement cityDropdown = driver.findElement(dropdownCity);
        Select select = new Select(cityDropdown);
        select.selectByVisibleText(cityName);
    }

    public void selectCityByValue(String value) {
        WebElement cityDropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownCity));
        Select select = new Select(cityDropdown);
        select.selectByValue(value);
    }

    public void clickViewMap(String xpath) {
        WebElement viewMapButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", viewMapButton);
            viewMapButton.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewMapButton);
        }
    }

    public String getStoreAddress(String xpath) {
        WebElement addressElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        return addressElement.getText();
    }

    public boolean isStoreListDisplayed() {
        return driver.findElement(storeListHeader).isDisplayed();
    }

    public boolean isCityInAddress(String cityName) {
        WebElement cityDropdown = driver.findElement(dropdownCity);
        return cityDropdown.getText().trim().contains(cityName);
    }

    public WebElement findDistrictElement(String district) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//b[contains(text(),'" + district + "')]")));
    }
}