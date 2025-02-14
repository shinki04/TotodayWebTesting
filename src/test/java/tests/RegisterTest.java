package tests;

import config.DriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utils.Notification;

public class RegisterTest extends DriverConfig {

    String loginURL = baseURL + "user/signin";
    private WebDriver driver;
    private Notification notification; // Khai báo Notification

    @BeforeSuite
    public void setupSuite() {
        driver = getDriver();
        driver.get(loginURL);
        notification = new Notification(driver); // Khởi tạo Notification với driver
    }

    @Test(priority = 0)
    public void testRegistration() throws InterruptedException {
        driver.findElement(By.id("pills-profile-tab")).click();
        Thread.sleep(3000); // Sử dụng Thread.sleep thay vì sleep(3000) nếu không có import

        driver.findElement(By.id("signUpFullName")).sendKeys("Nguyen Thi Ngoc Nga");
        driver.findElement(By.id("mobile")).sendKeys("0937800012");
        driver.findElement(By.id("signUpEmail")).sendKeys("NgocNga@gmail.com");
        driver.findElement(By.id("signUpPassword")).sendKeys("NgocNga1998");
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

    @AfterSuite
    public void cleanupSuite() { // Thêm public để tránh lỗi
//        quitDriver();
    }
}
