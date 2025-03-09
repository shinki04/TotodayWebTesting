package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Notification;

public class EditPassPage {
    private final WebDriver driver;
    private final Notification notification;

    // Locators
    private final By currentPasswordField = By.id("oldpassword");
    private final By newPasswordField = By.xpath("//input[@id='newpassword']");
    private final By confirmPasswordField = By.xpath("//input[@id='repassword']");
    private final By updateButton = By.xpath("//button[contains(text(),'Cập nhật')]");
    private final By logoutButton = By.xpath("//a[contains(text(),'Đăng xuất')]");
    private final By loginButton = By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]");
    private final By signInEmailField = By.id("SignInEmail");
    private final By passwordField = By.id("password-field");
    private final By errorMessageLocator = By.id("error-message");
    private final By successMessageLocator = By.cssSelector(".alert.alert-warning");

    // Constructor
    public EditPassPage(WebDriver driver, Notification notification) {
        this.driver = driver;
        this.notification = notification;
    }

    private WebElement getElement(By locator) {
        try {
            return driver.findElement(locator);
        } catch (Exception e) {
            return null;
        }
    }

    // Actions
    public void navigateTo(String url) { driver.get(url); }
    public void enterCurrentPassword(String currentPassword) { /* ... */ }
    public void enterNewPassword(String newPassword) { /* ... */ }
    public void enterConfirmPassword(String confirmPassword) { /* ... */ }
    public void clickUpdateButton() { /* ... */ }
    public void clickLogoutButton() { /* ... */ }
    public void loginToAccount(String loginUrl, String account, String password) { /* ... */ }
    public void changePassword(String loginUrl, String changePasswordUrl, String account,
                               String currentPassword, String newPassword, String confirmPassword) { /* ... */ }

    // Query Methods
    public boolean isErrorMessageDisplayed() {
        WebElement errorMsg = getElement(errorMessageLocator);
        return errorMsg != null && errorMsg.isDisplayed();
    }

    public String getErrorMessageText() {
        WebElement errorMsg = getElement(errorMessageLocator);
        return (errorMsg != null) ? errorMsg.getText().trim() : "";
    }

    public String getSuccessMessageText() {
        WebElement successMsg = getElement(successMessageLocator);
        return (successMsg != null) ? successMsg.getText().trim() : null;
    }

    public boolean isSuccessMessageDisplayed() {
        WebElement successMsg = getElement(successMessageLocator);
        return successMsg != null && successMsg.isDisplayed();
    }

    public boolean isUpdateButtonClickable() {
        WebElement button = getElement(updateButton);
        return button != null && button.isEnabled();
    }
}