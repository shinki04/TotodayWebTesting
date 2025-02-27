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
import utils.ExcelReader;
import utils.Notification;
import utils.Tools;

public class AccountInformationTest  extends DriverConfig {
    String loginURL = baseURL + "profile";
    private WebDriver driver;
//    private Notification notification;
//    private static Tools tools;
//    private ExcelReader excelReader;

    @BeforeSuite
    public void setupSuite() {
        driver = getDriver();
//        notification = new Notification(driver);
//        tools = new Tools(driver);
//        excelReader = new ExcelReader("./src/test/resources/login.xlsx");

    }

    @BeforeMethod
    public void setupTMethod() {
        driver.get(loginURL);
        driver.findElement(By.id("SignInEmail")).sendKeys("innologic25.team@gmail.com");
        driver.findElement(By.id("password-field")).sendKeys("innologic2025");

        driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]")).click();

    }

    @Test
    public void testEditFullName() {
        String newFullName = "Nguyễn Văn A";
        // Tìm ô nhập liệu họ tên và chỉnh sửa giá trị
        WebElement fullNameInput = driver.findElement(By.id("fullname")); // Cập nhật ID thực tế
        fullNameInput.clear();
        fullNameInput.sendKeys(newFullName);

        // Nhấp vào nút "Lưu" để lưu thay đổi
        driver.findElement(By.className("btn-submit")).submit();

        // Chờ trang load lại (có thể cần Thread.sleep hoặc WebDriverWait nếu cần)
        try {
            sleep(2); // Tạm thời chờ 2 giây (nên dùng WebDriverWait thay thế)
        }
        catch (Exception e) {
            System.out.println("Thread bị gián đoạn!");
        }

        // Kiểm tra lại giá trị họ tên đã thay đổi thành công chưa
        WebElement updatedFullName = driver.findElement(By.id("full-name-display")); // Cập nhật ID thực tế
        String actualFullName = updatedFullName.getText();

        // So sánh kết quả mong đợi và thực tế
        Assert.assertEquals(actualFullName, newFullName, "Họ tên không được cập nhật chính xác!");
    }


    @AfterSuite
    public void cleanupSuite() {
//        quitDriver();
    }


}
