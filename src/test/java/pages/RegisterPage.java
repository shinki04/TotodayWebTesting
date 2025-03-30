package pages;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.Notification;
import utils.Tools;

public class RegisterPage extends BaseTest {
    private WebDriver driver;
    private Notification notification;
    private Tools tools;

    private By fullNameField = By.id("signUpFullName");
    private By mobileField = By.id("mobile");
    private By emailField = By.id("signUpEmail");
    private By passwordField = By.id("signUpPassword");
    private By registerButton = By.xpath("//button[contains(text(),'Đăng Ký')]");
    private By profileTab = By.id("pills-profile-tab");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.notification = new Notification(driver);
    }

    // Điều hướng đến trang đăng nhập trước khi mở tab đăng ký
    public void navigateToSignInPage() {
        driver.get(baseURL +"/user/signin"); // Thay thế URL đúng của bạn
    }

    // Mở tab đăng ký sau khi điều hướng đến trang đăng nhập
    public void openRegisterTab() {
        navigateToSignInPage();
        driver.findElement(profileTab).click();
        sleep(3);
    }

    public void fillRegistrationForm(String name, String phone, String email, String password) {
        driver.findElement(fullNameField).sendKeys(name);
        driver.findElement(mobileField).sendKeys(phone);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
    }

    public void submitRegistration() {
        driver.findElement(registerButton).submit();
    }

    public boolean isAlertPresent() {
        return notification.isAlertPresent();
    }

    public String getAlertText() {
        return notification.getAlertText();
    }

    public void acceptAlert() {
        notification.acceptAlert();
    }

}