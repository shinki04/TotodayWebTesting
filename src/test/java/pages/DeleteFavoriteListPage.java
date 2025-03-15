package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Notification;

public class DeleteFavoriteListPage {
    private final WebDriver driver;
    private final String baseURL;
    private final Notification notification;

    // Locators
    private final By signInEmailField = By.id("SignInEmail");
    private final By passwordField = By.id("password-field");
    private final By loginButton = By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]");
    private final By removeButton = By.xpath("//div[@class='col-md-8']//div[2]//div[1]//div[1]//div[1]//div[1]//span[1]//i[1]");
    private final By errorMessageLocator = By.cssSelector(".alert.alert-danger");
    private final By successMessageLocator = By.cssSelector(".alert.alert-success");

    // Constructor
    public DeleteFavoriteListPage(WebDriver driver, String baseURL, Notification notification) {
        this.driver = driver;
        this.baseURL = baseURL;
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
    public void navigateTo(String url) {
        driver.get(url);
    }

    public void loginToAccount(String loginUrl, String email, String password) {
        driver.manage().window().maximize();
        driver.get(loginUrl);
        WebElement emailField = getElement(signInEmailField);
        WebElement passField = getElement(passwordField);
        WebElement loginBtn = getElement(loginButton);

        if (emailField != null) emailField.sendKeys(email);
        if (passField != null) passField.sendKeys(password);
        if (loginBtn != null) loginBtn.click();

        if (notification.isAlertPresent()) {
            String alertText = notification.getAlertText();
            System.out.println("Thông báo sau đăng nhập: " + alertText);
            notification.acceptAlert();
        }
    }

    public void removeProduct(String productId) {
        WebElement productElement = getElement(By.cssSelector("[data-id='" + productId + "']"));
        if (productElement != null) {
            WebElement removeBtn = productElement.findElement(removeButton);
            if (removeBtn != null) removeBtn.click();
        }
    }

    public void deleteFavoriteItem(String loginUrl, String wishlistUrl, String email, String password, String productId) {
        loginToAccount(loginUrl, email, password);
        navigateTo(wishlistUrl);
        removeProduct(productId);
    }

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

    public boolean isRemoveButtonClickable(String productId) {
        WebElement productElement = getElement(By.cssSelector("[data-id='" + productId + "']"));
        if (productElement != null) {
            WebElement removeBtn = productElement.findElement(removeButton);
            return removeBtn != null && removeBtn.isEnabled();
        }
        return false;
    }

    public boolean isProductExist(String productId) {
        WebElement productElement = getElement(By.cssSelector("[data-id='" + productId + "']"));
        return productElement != null && productElement.isDisplayed();
    }
}