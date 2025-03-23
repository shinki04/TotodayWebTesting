package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Notification;

public class EditPassPage {
    private final WebDriver driver;

    // Locators
    private final By updateButton = By.xpath("//button[contains(text(),'Cập nhật')]");
    private final By errorMessageLocator = By.id("error-message");
    private final By successMessageLocator = By.cssSelector(".alert.alert-warning");

    // Constructor
    public EditPassPage(WebDriver driver, Notification notification) {
        this.driver = driver;
    }

    private WebElement getElement(By locator) {
        try {
            return driver.findElement(locator);
        } catch (Exception e) {
            return null;
        }
    }

    // Actions
    public void clickLogoutButton() { /* ... */ }
    public void loginToAccount(String loginUrl, String account, String password) { /* ... */ }
    public void changePassword(String loginUrl, String changePasswordUrl, String account,
                               String currentPassword, String newPassword, String confirmPassword) { /* ... */ }

    // Query Methods

    public String getSuccessMessageText() {
        WebElement successMsg = getElement(successMessageLocator);
        return (successMsg != null) ? successMsg.getText().trim() : null;
    }

    public boolean isSuccessMessageDisplayed() {
        WebElement successMsg = getElement(successMessageLocator);
        return successMsg != null && successMsg.isDisplayed();
    }
}