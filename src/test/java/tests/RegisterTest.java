package tests;

import config.DriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utils.Notification;
import utils.Tools;


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
    public void testRegistrationWithSuccess() throws InterruptedException {

        String randomName = tools.generateRandomString(10);
        String randomPhone = 0 + tools.generateRandomNumber(9);
        String randomEmail = tools.generateRandomString(10) + "@gmail.com";
        String randomPassword = tools.generateRandomString(7);

        driver.findElement(By.id("signUpFullName")).sendKeys(randomName);
        driver.findElement(By.id("mobile")).sendKeys(randomPhone);
        driver.findElement(By.id("signUpEmail")).sendKeys(randomEmail);
        driver.findElement(By.id("signUpPassword")).sendKeys(randomPassword);
        sleep(2);
//        // Kiểm tra URL
//        String expectedUrl = "https://totoday.vn/user/signin";
//        String actualUrl = driver.getCurrentUrl();
//
//        Assert.assertEquals(actualUrl, expectedUrl, "URL không chuyển đến đúng trang!");

//        driver.findElement(By.xpath("//form[@id='formSignUp']")).submit();
        driver.findElement(By.xpath("//button[contains(text(),'Đăng Ký')]")).submit();

        if (notification.isAlertPresent()) {
            System.out.println("Thông báo xuất hiện với nội dung: " + notification.getAlertText());
            notification.acceptAlert(); // Hoặc notification.dismissAlert();
        } else {
            System.out.println("Không có thông báo nào hiển thị");
        }


    }

    @Test(priority = 1)
    public void testRegisterWithExistingAccount() throws InterruptedException {
        driver.findElement(By.id("signUpFullName")).sendKeys("Innologic");
        driver.findElement(By.id("mobile")).sendKeys("0300000009");
        driver.findElement(By.id("signUpEmail")).sendKeys("innologic25.team@gmail.com");
        driver.findElement(By.id("signUpPassword")).sendKeys("innologic2025");

        sleep(2);
        driver.findElement(By.xpath("//button[contains(text(),'Đăng Ký')]")).submit();

        if (notification.isAlertPresent()) {
            String alertText = notification.getAlertText();
            System.out.println("Thông báo lỗi hiển thị: " + alertText);
            notification.acceptAlert();

            // Test PASSED nếu thông báo lỗi xuất hiện
            Assert.assertTrue(true, "Đăng ký thất bại như mong đợi do email đã tồn tại.");
        } else {
            System.out.println("Không có thông báo lỗi hiển thị");

            // Test FAILED nếu không có thông báo lỗi
            Assert.fail("Test thất bại: Hệ thống cho phép đăng ký tài khoản đã tồn tại.");
        }


      }

    @Test(priority = 2)
    public void testRegisterWithEmptyFields() throws InterruptedException {

        sleep(2);
        driver.findElement(By.xpath("//button[contains(text(),'Đăng Ký')]")).submit();

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
    public void testRegisterWithInvalidEmail() throws InterruptedException {
        String randomName = tools.generateRandomString(10);
        String randomPhone = 0 + tools.generateRandomNumber(9);
        String randomEmail = tools.generateRandomString(10) + "gemail.com";
        String randomPassword = tools.generateRandomString(7);

        driver.findElement(By.id("signUpFullName")).sendKeys(randomName);
        driver.findElement(By.id("mobile")).sendKeys(randomPhone);
        driver.findElement(By.id("signUpEmail")).sendKeys(randomEmail);
        driver.findElement(By.id("signUpPassword")).sendKeys(randomPassword);

        sleep(2);
        driver.findElement(By.xpath("//button[contains(text(),'Đăng Ký')]")).submit();

    }


    @AfterSuite
    public void cleanupSuite() {
        quitDriver();
    }
}
