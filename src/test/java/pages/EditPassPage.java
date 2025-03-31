package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Notification;

import java.time.Duration;

public class EditPassPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators cho trang đăng nhập
    private final By emailField = By.id("SignInEmail");
    private final By passwordField = By.id("password-field");
    private final By loginButton = By.xpath("//button[contains(text(),'Đăng nhập')]"); // Giả định nút đăng nhập

    // Locators cho trang đổi mật khẩu
    private final By currentPass = By.xpath("//input[@id='oldpassword']");
    private final By newPass = By.xpath("//input[@id='newpassword']");
    private final By confirmPass = By.xpath("//input[@id='repassword']");
    private final By updateButton = By.xpath("//button[contains(text(),'Cập nhật')]");
    private final By errorMessageLocator = By.id("error-message");
    private final By successMessageLocator = By.cssSelector(".alert.alert-warning");

    // Locators cho đăng xuất
    private final By userButton = By.xpath("//img[@alt='Tài khoản']");
    private final By logoutButton = By.xpath("//a[@href='/user/signout']");

    // Constructor
    public EditPassPage(WebDriver driver, Notification notification) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private WebElement getElement(By locator) {
        try {
            return driver.findElement(locator);
        } catch (Exception e) {
            return null;
        }
    }

    private WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Phương thức đăng nhập
    public void loginToAccount(String loginUrl, String email, String password) {
        driver.get(loginUrl);

        WebElement emailElement = waitForElement(emailField);
        if (emailElement != null) {
            emailElement.clear();
            emailElement.sendKeys(email);
        } else {
            throw new RuntimeException("Không tìm thấy trường Email!");
        }

        WebElement passwordElement = waitForElement(passwordField);
        if (passwordElement != null) {
            passwordElement.clear();
            passwordElement.sendKeys(password);
        } else {
            throw new RuntimeException("Không tìm thấy trường Password!");
        }

        WebElement loginBtn = waitForElement(loginButton);
        if (loginBtn != null) {
            loginBtn.click();
        } else {
            throw new RuntimeException("Không tìm thấy nút Đăng nhập!");
        }
    }

    // Các phương thức cho trang đổi mật khẩu
    public WebElement enterCurrentPassword() {
        WebElement oldPasswordField = getElement(currentPass);
        if (oldPasswordField != null) {
            oldPasswordField.clear();
            return oldPasswordField;
        }
        return null;
    }

    public void enterNewPassword(String newPassword) {
        WebElement newPasswordField = getElement(newPass);
        if (newPasswordField != null) {
            newPasswordField.clear();
            newPasswordField.sendKeys(newPassword);
        }
    }

    public void enterConfirmPassword(String confirmPassword) {
        WebElement confirmPasswordField = getElement(confirmPass);
        if (confirmPasswordField != null) {
            confirmPasswordField.clear();
            confirmPasswordField.sendKeys(confirmPassword);
        }
    }

    public void clickUpdateBtn() {
        WebElement button = getElement(updateButton);
        if (button != null) {
            button.submit();
        }
    }

    public void clickLogoutBtn() {
        WebElement userBtn = waitForElement(userButton);
        if (userBtn != null) {
            userBtn.click();
        }
        WebElement logoutBtn = waitForElement(logoutButton);
        if (logoutBtn != null) {
            logoutBtn.click();
        }
    }

    public void changePassword(String loginUrl, String changePasswordUrl, String account,
                               String currentPassword, String newPassword, String confirmPassword) {
        driver.get(changePasswordUrl);
        WebElement currentPassField = enterCurrentPassword();
        if (currentPassField != null) {
            currentPassField.sendKeys(currentPassword);
        }
        enterNewPassword(newPassword);
        enterConfirmPassword(confirmPassword);
        clickUpdateBtn();
    }

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