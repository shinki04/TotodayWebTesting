package tests;

import config.DriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utils.ExcelReader;
import utils.Notification;
import utils.Tools;

import java.io.IOException;
import java.util.List;

public class Demo extends DriverConfig {
    String loginURL = baseURL + "user/signin";
    private WebDriver driver;
    private Notification notification;
    private static Tools tools;
//    private PopupHandler popupHandler;
    private ExcelReader excelReader;

    @BeforeSuite
    public void setupSuite() {
        driver = getDriver();
        notification = new Notification(driver);
        tools = new Tools(driver);
//        popupHandler = new PopupHandler(driver);
        excelReader = new ExcelReader("./src/test/resources/login.xlsx");
    }

    @BeforeMethod
    public void setupTMethod() {
        driver.get(loginURL);

    }

    @Test(priority = 0)
    public void testSuccess() throws InterruptedException, IOException {
        driver.manage().window().maximize();

        // Đọc dữ liệu từ file Excel (giả sử thông tin đăng nhập nằm ở Sheet 0, dòng đầu tiên)
        List<String[]> loginData = excelReader.readExcelData(0);
        String email = loginData.get(0)[0]; // Cột 1: Email
        String password = loginData.get(0)[1]; // Cột 2: Mật khẩu

        driver.get(loginURL);

        // Nhập thông tin đăng nhập từ file Excel
        driver.findElement(By.id("SignInEmail")).sendKeys(email);
        driver.findElement(By.id("password-field")).sendKeys(password);

        // Gửi biểu mẫu đăng nhập
        driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]")).click();
        Thread.sleep(2000);

        // Kiểm tra nếu có alert xuất hiện
        Notification notification = new Notification(driver);
        if (notification.isAlertPresent()) {
            String actualMessage = notification.getAlertText();
            System.out.println("Thông báo từ hệ thống: " + actualMessage);
            notification.acceptAlert(); // Đóng thông báo nếu có

            // So sánh kết quả mong đợi
            String expectedMessage = "Đăng nhập thành công!";
            Assert.assertEquals(actualMessage, expectedMessage, "Thông báo không khớp với mong đợi!");
        }

        // Kiểm tra đăng nhập thành công
        String currentURL = driver.getCurrentUrl();
        System.out.println("Actual URL: " + currentURL);

        // Đảm bảo rằng không còn ở trang đăng nhập, tức là đăng nhập thành công
        Assert.assertNotEquals(currentURL, loginURL, "Đăng nhập thất bại! Vẫn ở trang đăng nhập.");

        System.out.println("Đăng nhập thành công! Hiện tại ở URL: " + currentURL);

//         Nhấn vào đường dẫn đăng xuất
        sleep(5);
        driver.findElement(By.xpath("//img[@alt='Tài khoản']")).click();
        driver.findElement(By.xpath("//a[@href='/user/signout']")).click();
    }



    @AfterSuite
    public void cleanupSuite() {
//        quitDriver();
    }
}
