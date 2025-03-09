package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.*;


public class RegisterTest extends BaseTest {
        private String loginURL = baseURL + "/user/signin";

        @BeforeMethod
        public void setupTMethod() {
            driver.get(loginURL);
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
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight / 2);");
        sleep(5);
        driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]")).submit();
        sleep(5);

        String popupClass = "formErrorContent"; // Tên class của popup

        // Kiểm tra nếu popup có xuất hiện
        Assert.assertTrue(popupHandler.isPopupPresent(popupClass), "Không có thông báo lỗi hiển thị");

        // Kiểm tra nội dung lỗi hiển thị đúng
        Assert.assertEquals(popupHandler.getPopupMessage(popupClass), "* Trường này bắt buộc", "Sai thông báo lỗi cho họ tên");
        Assert.assertEquals(popupHandler.getPopupMessage(popupClass), "* Trường này bắt buộc * Số điện thoại sai", "Sai thông báo lỗi cho số điện thoại");
        Assert.assertEquals(popupHandler.getPopupMessage(popupClass), "* Trường này bắt buộc * Địa chỉ thư điện tử sai", "Sai thông báo lỗi cho email");
        Assert.assertEquals(popupHandler.getPopupMessage(popupClass), "* Trường này bắt buộc * Tối thiểu 6 số ký tự được cho phép", "Sai thông báo lỗi cho mật khẩu");


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
        cleanupTest();
    }
}
