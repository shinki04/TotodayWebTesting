package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;
    private String loginURL;

    // Locators
    private By emailField = By.id("SignInEmail");
    private By passwordField = By.id("password-field");
    private By submitButton = By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]");
    private By accountIcon = By.xpath("//img[@alt='Tài khoản']");
    private By signOutLink = By.xpath("//a[@href='/user/signout']");
    private By passwordToggleButton = By.cssSelector(".fa-sharp.fa-solid.fa-eye-slash");

    // Constructor
    public LoginPage(WebDriver driver, String baseURL) {
        this.driver = driver;
        this.loginURL = baseURL + "/user/signin";
    }

    // Methods
    public void navigateToLoginPage() {
        driver.get(loginURL);
    }

    public void enterEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(submitButton).click();
    }

    public String getPassText() {
        return driver.findElement(passwordField).getAttribute("value");
    }

    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    public void logout() throws InterruptedException {
        driver.findElement(accountIcon).click();
        driver.findElement(signOutLink).click();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void togglePasswordVisibility() {
        driver.findElement(passwordToggleButton).click();
    }

    public String getPasswordFieldType() {
        WebElement passwordInput = driver.findElement(passwordField);
        return passwordInput.getAttribute("type");
    }
}