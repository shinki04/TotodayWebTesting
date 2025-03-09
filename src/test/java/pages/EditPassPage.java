package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import utils.Notification;

import java.time.Duration;

public class EditPassPage {
    private final WebDriver driver;
    private final Notification notification;

    // Input Fields
    private final By currentPasswordField = By.id("oldpassword");
    private final By newPasswordField = By.xpath("//input[@id='newpassword']");
    private final By confirmPasswordField = By.xpath("//input[@id='repassword']");

    // Buttons
    private final By updateButton = By.xpath("//button[contains(text(),'Cập nhật')]");
    private final By logoutButton = By.xpath("//a[contains(text(),'Đăng xuất')]");
    private final By loginButton = By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]");

    // Login Fields
    private final By signInEmailField = By.id("SignInEmail");
    private final By passwordField = By.id("password-field");

    // Error Messages
    private final By errorMessageLocator = By.id("error-message");

    public EditPassPage(WebDriver driver, Notification notification) {
        this.driver = driver;
        this.notification = notification;
    }

    /**
     * Navigate to Change Password Page
     */
    public void navigateToChangePasswordPage(String url) {
        driver.get(url);
    }

    /**
     * Login to the account
     */
    public void loginToAccount(String loginUrl, String account, String password) {
        driver.get(loginUrl);
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.presenceOfElementLocated(signInEmailField)).sendKeys(account);
        wait.until(ExpectedConditions.presenceOfElementLocated(passwordField)).sendKeys(password);
        driver.findElement(loginButton).click();
        notification.acceptAlert();
    }

    /**
     * Enter password details
     */
    public void enterCurrentPassword(String currentPassword) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(currentPasswordField));
        element.clear();
        element.sendKeys(currentPassword);
    }

    public void enterNewPassword(String newPassword) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(newPasswordField));
        element.clear();
        element.sendKeys(newPassword);
    }

    public void enterConfirmPassword(String confirmPassword) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(confirmPasswordField));
        element.clear();
        element.sendKeys(confirmPassword);
    }

    public void clickUpdateButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(updateButton)).click();
    }

    public void clickLogoutButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
        notification.acceptAlert();
    }

    /**
     * Get messages
     */
    public String getErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(errorMessageLocator)).getText().trim();
        } catch (TimeoutException e) {
            return null;
        }
    }

    public String getSuccessMessage() {
        return notification.getAlertText();
    }

    /**
     * Validate password change conditions
     */
    public boolean shouldExpectError(String currentPassword, String newPassword, String confirmPassword) {
        return currentPassword.isEmpty() || newPassword.isEmpty() || !newPassword.equals(confirmPassword) || newPassword.length() < 8;
    }

    /**
     * Verify error messages
     */
    public void verifyErrorMessage(String currentPassword, String newPassword, String confirmPassword) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        String testCase = String.format("Current: %s, New: %s, Confirm: %s", currentPassword, newPassword, confirmPassword);
        String expectation = "Thông báo lỗi 'Thông tin không hợp lệ' vì dữ liệu không hợp lệ";
        String errorMessage = getErrorMessage();

        Assert.assertNotNull(errorMessage, "Không hiển thị thông báo lỗi!");
        boolean isSuccess = errorMessage.contains("không hợp lệ");
        Assert.assertTrue(isSuccess, "Thông báo lỗi không đúng!");

        printTestResult(testCase, expectation, errorMessage, isSuccess);
    }

    public void verifySuccessAndRelogin(String loginUrl, String account, String newPassword) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        String testCase = "Account: " + account + ", New Password: " + newPassword;
        String expectation = "Thông báo thành công 'Đổi mật khẩu tài khoản thành công' và đăng nhập lại thành công";
        String successMessage = getSuccessMessage();

        Assert.assertNotNull(successMessage, "Không hiển thị thông báo thành công!");
        boolean isSuccess = successMessage.trim().equals("Đổi mật khẩu tài khoản thành công");
        Assert.assertTrue(isSuccess, "Thông báo thành công không đúng!");

        printTestResult(testCase, expectation, successMessage, isSuccess);

        acceptAlert();
        clickLogoutButton();
        loginToAccount(loginUrl, account, newPassword);
    }

    /**
     * Perform password change
     */
    public void changePassword(String loginUrl, String changePasswordUrl, String account, String currentPassword, String newPassword, String confirmPassword) {
        loginToAccount(loginUrl, account, currentPassword);
        navigateToChangePasswordPage(changePasswordUrl);

        enterCurrentPassword(currentPassword);
        enterNewPassword(newPassword);
        enterConfirmPassword(confirmPassword);
        clickUpdateButton();

        if (shouldExpectError(currentPassword, newPassword, confirmPassword)) {
            verifyErrorMessage(currentPassword, newPassword, confirmPassword);
        } else {
            verifySuccessAndRelogin(loginUrl, account, newPassword);
        }
    }

    /**
     * Print test results
     */
    private void printTestResult(String testCase, String expectation, String actualResult, boolean isSuccess) {
        System.out.println("==========================================");
        System.out.println("Đang test trường hợp: " + testCase);
        System.out.println("Kỳ vọng: " + expectation);
        System.out.println("Kết quả thực tế: " + (actualResult != null ? actualResult : "Không có kết quả"));
        System.out.println("So sánh kết quả: " + (isSuccess ? "PASS" : "FAIL"));
        System.out.println("==========================================");
    }

    public void acceptAlert() {
        notification.acceptAlert();
    }
}
