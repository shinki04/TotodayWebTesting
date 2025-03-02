package tests;

import config.DriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.Notification;
import utils.Tools;

import java.time.Duration;


public class RegisterTest extends DriverConfig {

    String loginURL = baseURL + "user/signin";
    private WebDriver driver;
    private Notification notification; // Khai báo Notification
    private static Tools tools;


    @BeforeSuite
    public void setupSuite() {
        driver = getDriver();
        driver.get(loginURL);
        notification = new Notification(driver); // Khởi tạo Notification với driver
        tools = new Tools(driver);

    }
    @BeforeMethod
    public void setupTMethod() {
        driver.findElement(By.id("pills-profile-tab")).click();

        sleep(3);

    }

    @Test(priority = 0)
    public void testSuccess() throws InterruptedException {

        String randomName = tools.generateRandomString(10);
        String randomPhone = 0 + tools.generateRandomNumber(9);
        String randomEmail = tools.generateRandomString(10) + "@gmail.com";
        String randomPassword = tools.generateRandomString(7);

        driver.findElement(By.id("signUpFullName")).sendKeys(randomName);
        driver.findElement(By.id("mobile")).sendKeys(randomPhone);
        driver.findElement(By.id("signUpEmail")).sendKeys(randomEmail);
        driver.findElement(By.id("signUpPassword")).sendKeys(randomPassword);
        sleep(2);

        driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]")).submit();
//      driver.findElement(By.className("signup-btn btn-form")).click();

        // Kiểm tra nếu có alert xuất hiện
        if (notification.isAlertPresent()) {
            String actualMessage = notification.getAlertText();
            System.out.println("Thông báo từ hệ thống: " + actualMessage);
            notification.acceptAlert(); // Đóng thông báo nếu có

            // So sánh kết quả mong đợi
            String expectedMessage = "Bạn đã đăng ký thành công";
            Assert.assertEquals(actualMessage, expectedMessage, "Thông báo không khớp với mong đợi!");
        }

        // Kiểm tra đăng nhập thành công
        String currentURL = driver.getCurrentUrl();
        System.out.println("Actual URL: " + currentURL);

        // Đảm bảo rằng không còn ở trang đăng nhập, tức là đăng nhập thành công
        Assert.assertNotEquals(currentURL, loginURL, "Đăng nhập thất bại! Vẫn ở trang đăng nhập.");

    }
    //mật khẩu không đúng định dạng hoặc email đã tồn tại. Vui lòng kiểm tra lại!
    @Test(priority = 1)
    public void testExistingAccount() throws InterruptedException {
        driver.findElement(By.id("signUpFullName")).sendKeys("Innologic");
        driver.findElement(By.id("mobile")).sendKeys("0300000009");
        driver.findElement(By.id("signUpEmail")).sendKeys("innologic25.team@gmail.com");
        driver.findElement(By.id("signUpPassword")).sendKeys("innologic2025");

        sleep(2);
        driver.manage().window().fullscreen();
        driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]")).submit();
        if (notification.isAlertPresent()) {
            String alertText = notification.getAlertText();
            System.out.println("Thông báo lỗi hiển thị: " + alertText);
            notification.acceptAlert();

            if (notification.isAlertPresent()) {
                String actualMessage = notification.getAlertText();
                System.out.println("Thông báo từ hệ thống: " + actualMessage);
                notification.acceptAlert(); // Đóng thông báo nếu có

                // So sánh kết quả mong đợi
                String expectedMessage = "mật khẩu không đúng định dạng hoặc email đã tồn tại. Vui lòng kiểm tra lại!";
                Assert.assertEquals(actualMessage, expectedMessage, "Thông báo không khớp với mong đợi!");
            }

            // Kiểm tra đăng nhập thành công
            String currentURL = driver.getCurrentUrl();
            System.out.println("Actual URL: " + currentURL);

            // Đảm bảo rằng không còn ở trang đăng nhập, tức là đăng nhập thành công
            Assert.assertNotEquals(currentURL, loginURL, "Đăng nhập thất bại! Vẫn ở trang đăng nhập.");

        }
    }

    @Test(priority = 2)
    public void testEmptyFields() throws InterruptedException {

        sleep(2);
        driver.manage().window().fullscreen();

        driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]")).submit();

        // Kiểm tra lỗi từng trường
        Assert.assertTrue(driver.findElement(By.id("signUpFullName")).isDisplayed(), "Không có thông báo lỗi cho họ tên");
        Assert.assertTrue(driver.findElement(By.id("mobile")).isDisplayed(), "Không có thông báo lỗi cho họ tên");
        Assert.assertTrue(driver.findElement(By.id("signUpEmail")).isDisplayed(), "Không có thông báo lỗi cho họ tên");
        Assert.assertTrue(driver.findElement(By.id("signUpPassword")).isDisplayed(), "Không có thông báo lỗi cho họ tên");

        // Kiểm tra nội dung lỗi
        Assert.assertEquals(driver.findElement(By.id("signUpFullName")).getText(), "* Trường này bắt buộc");
        Assert.assertEquals(driver.findElement(By.id("mobile")).getText(), "* Trường này bắt buộc");
        Assert.assertEquals(driver.findElement(By.id("signUpEmail")).getText(), "* Trường này bắt buộc");
        Assert.assertEquals(driver.findElement(By.id("signUpPassword")).getText(), "* Trường này bắt buộc");


    }

    @Test(priority = 3)
    public void testInvalidEmail() throws InterruptedException {
        String randomName = tools.generateRandomString(10);
        String randomPhone = 0 + tools.generateRandomNumber(9);
        String randomEmail = tools.generateRandomString(10) + "gemail.com";
        String randomPassword = tools.generateRandomString(7);

        driver.findElement(By.id("signUpFullName")).sendKeys(randomName);
        driver.findElement(By.id("mobile")).sendKeys(randomPhone);
        driver.findElement(By.id("signUpEmail")).sendKeys(randomEmail);
        driver.findElement(By.id("signUpPassword")).sendKeys(randomPassword);

        sleep(2);
        driver.manage().window().fullscreen();

        driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]")).submit();

    }


    @AfterSuite
    public void cleanupSuite() {
        quitDriver();
    }
}
