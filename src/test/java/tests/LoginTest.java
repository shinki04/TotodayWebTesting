package tests;

import config.DriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utils.Notification;
import utils.Tools;

public class LoginTest extends DriverConfig {
    String loginURL = baseURL + "user/signin";
    private WebDriver driver;
    private Notification notification;
    private static Tools tools;

    @BeforeSuite
    public void setupSuite() {
        driver = getDriver();
        driver.get(loginURL);
        notification = new Notification(driver);
        tools = new Tools(driver);
     }

    @BeforeMethod
    public void setupTMethod() {
        driver.get(loginURL);

    }

    @Test(priority = 0)
    public void testLoginWithSuccess() throws InterruptedException {
        // Nhập thông tin đăng nhập
        driver.findElement(By.id("SignInEmail")).sendKeys("innologic25.team@gmail.com");
        driver.findElement(By.id("password-field")).sendKeys("innologic2025");

        // Gửi biểu mẫu đăng nhập
        driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]")).submit();
        sleep(2);

        // Kiểm tra nếu có alert xuất hiện
        if (notification.isAlertPresent()) {
            String alertText = notification.getAlertText();
            System.out.println("Thông báo từ hệ thống: " + alertText);
            notification.acceptAlert(); // Đóng thông báo nếu có
        }

        // Kiểm tra đăng nhập thành công
        String currentURL = driver.getCurrentUrl();
        Assert.assertNotEquals(currentURL, loginURL, "Đăng nhập thất bại! Vẫn ở trang đăng nhập.");

        System.out.println("Đăng nhập thành công! Hiện tại ở URL: " + currentURL);
    }
    @Test(priority = 2)
    public void testLoginWithNonExistentEmail() throws InterruptedException {
        driver.findElement(By.id("SignInEmail")).sendKeys("invalid_email@gmail.com");
        driver.findElement(By.id("password-field")).sendKeys("innologic2025");
        driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]")).submit();
        sleep(2);


        if (notification.isAlertPresent()) {
            String alertText = notification.getAlertText();
            System.out.println("Thông báo từ hệ thống: " + alertText);
            notification.acceptAlert();
            Assert.assertEquals(alertText, "Email không tồn tại", "Thông báo không chính xác!");
        }

        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, loginURL, "Hệ thống không nên cho phép đăng nhập với email không tồn tại!");
    }

    @Test(priority = 3)
    public void testLoginWithInvalidEmailFormat() throws InterruptedException {
        driver.findElement(By.id("SignInEmail")).sendKeys("invalid-email-format");
        driver.findElement(By.id("password-field")).sendKeys("innologic2025");
        driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]")).submit();
        sleep(2);


        if (notification.isAlertPresent()) {
            String alertText = notification.getAlertText();
            System.out.println("Thông báo từ hệ thống: " + alertText);
            notification.acceptAlert();
            Assert.assertEquals(alertText, "Email không hợp lệ", "Hệ thống không kiểm tra định dạng email!");
        }

        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, loginURL, "Hệ thống không nên cho phép đăng nhập với email sai định dạng!");
    }

    @Test(priority = 4)
    public void testLoginWithEmptyPassword() throws InterruptedException {
        driver.findElement(By.id("SignInEmail")).sendKeys("innologic25.team@gmail.com");
        driver.findElement(By.id("password-field")).sendKeys("");
        driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]")).submit();
        sleep(2);


        if (notification.isAlertPresent()) {
            String alertText = notification.getAlertText();
            System.out.println("Thông báo từ hệ thống: " + alertText);
            notification.acceptAlert();
            Assert.assertEquals(alertText, "Vui lòng nhập mật khẩu", "Thông báo không chính xác khi bỏ trống mật khẩu!");
        }

        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, loginURL, "Hệ thống không nên cho phép đăng nhập với mật khẩu trống!");
    }

    @Test(priority = 5)
    public void testLoginWithEmptyEmail() throws InterruptedException {
        driver.findElement(By.id("SignInEmail")).sendKeys("");
        driver.findElement(By.id("password-field")).sendKeys("innologic2025");
        driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]")).submit();
        sleep(2);


        if (notification.isAlertPresent()) {
            String alertText = notification.getAlertText();
            System.out.println("Thông báo từ hệ thống: " + alertText);
            notification.acceptAlert();
            Assert.assertEquals(alertText, "Vui lòng nhập email", "Thông báo không chính xác khi bỏ trống email!");
        }

        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, loginURL, "Hệ thống không nên cho phép đăng nhập với email trống!");
    }

    @Test(priority = 6)
    public void testLoginWithEmptyEmailAndPassword() throws InterruptedException {
        driver.findElement(By.id("SignInEmail")).sendKeys("");
        driver.findElement(By.id("password-field")).sendKeys("");
        driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]")).submit();
        sleep(2);


        if (notification.isAlertPresent()) {
            String alertText = notification.getAlertText();
            System.out.println("Thông báo từ hệ thống: " + alertText);
            notification.acceptAlert();
            Assert.assertEquals(alertText, "Vui lòng nhập email và mật khẩu", "Thông báo không chính xác khi bỏ trống cả hai trường!");
        }

        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, loginURL, "Hệ thống không nên cho phép đăng nhập với cả email và mật khẩu trống!");
    }

    @Test(priority = 7)
    public void testPasswordVisibilityToggle() throws InterruptedException {
        driver.findElement(By.id("SignInEmail")).sendKeys("innologic25.team@gmail.com");
        driver.findElement(By.id("password-field")).sendKeys("innologic2025");

        // Nhấn vào nút hiển thị mật khẩu
        WebElement toggleButton = driver.findElement(By.cssSelector(".fa-sharp.fa-solid.fa-eye-slash"));
        toggleButton.click();
        Thread.sleep(1000);

        // Kiểm tra hiển thị mật khẩu
        WebElement passwordInput = driver.findElement(By.id("password-field"));
        String inputType = passwordInput.getAttribute("type");

        //Kiểm tra nếu mật khẩu đang hiển thị (type="text")
        Assert.assertEquals(inputType, "text", "Lỗi: Mật khẩu không hiển thị đúng! Giá trị thực tế: " + inputType);

        // Nhấn lại để ẩn mật khẩu
        toggleButton.click();
        Thread.sleep(1000);

        // Kiểm tra lại nếu mật khẩu đã bị ẩn (type="password")
        inputType = passwordInput.getAttribute("type");
        Assert.assertEquals(inputType, "password", "Lỗi: Mật khẩu không bị ẩn đúng! Giá trị thực tế: " + inputType);
    }



    @AfterSuite
    public void cleanupSuite() {
//        quitDriver();
    }
}