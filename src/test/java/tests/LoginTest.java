package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    private String loginURL = baseURL + "/user/signin";


//    @BeforeSuite
//    public void setupSuite() {
//        driver.get(loginURL);
//     }

    @BeforeMethod
    public void setupTMethod() {
        driver.get(loginURL);

    }

    @Test(priority = 0)
    public void testSuccess() throws InterruptedException {

        driver.manage().window().maximize();

        // Nhập thông tin đăng nhập
        driver.findElement(By.id("SignInEmail")).sendKeys("innologic25.team@gmail.com");
        driver.findElement(By.id("password-field")).sendKeys("innologic2025");

        // Gửi biểu mẫu đăng nhập
        driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]")).click();
        sleep(2);

        // Kiểm tra nếu có alert xuất hiện
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

        // Nhấn vào đường dẫn đăng xuất
        driver.findElement(By.xpath("//img[@alt='Tài khoản']")).click();
        driver.findElement(By.xpath("//a[@href='/user/signout']")).click();

    }

    @Test(priority = 1)
    public void testNonExistentEmail() throws InterruptedException {
        driver.findElement(By.id("SignInEmail")).sendKeys("invalid_email@gmail.com");
        driver.findElement(By.id("password-field")).sendKeys("innologic2025");
        driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]")).click();
        sleep(2);


        // Kiểm tra nếu có alert xuất hiện
        if (notification.isAlertPresent()) {
            String actualMessage = notification.getAlertText();
            System.out.println("Thông báo từ hệ thống: " + actualMessage);
            notification.acceptAlert(); // Đóng thông báo nếu có

            // So sánh kết quả mong đợi
            String expectedMessage = "Tài khoản hoặc mật khẩu không chính xác. Vui lòng kiểm tra lại!";
            Assert.assertEquals(actualMessage, expectedMessage, "Thông báo không chính xác!");
        }

    // Kiểm tra xem người dùng có bị giữ lại ở trang đăng nhập hay không
        String currentURL = driver.getCurrentUrl();
        System.out.println("Expected URL (Trang đăng nhập): " + loginURL);
        System.out.println("Actual URL: " + currentURL);

    // Đảm bảo rằng vẫn ở trang đăng nhập, tức là đăng nhập không thành công
        Assert.assertEquals(currentURL, loginURL, "Hệ thống không nên cho phép đăng nhập với email chưa đăng ký tài khoản!");
    }

        @Test(priority = 2)
    public void testInvalidEmailFormat() throws InterruptedException {
        driver.findElement(By.id("SignInEmail")).sendKeys("invalid-email-format");
        driver.findElement(By.id("password-field")).sendKeys("innologic2025");
        driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]")).click();
        sleep(2);


            // Kiểm tra nếu có alert xuất hiện
            if (notification.isAlertPresent()) {
                String actualMessage = notification.getAlertText();
                System.out.println("Thông báo từ hệ thống: " + actualMessage);
                notification.acceptAlert(); // Đóng thông báo nếu có

                // So sánh kết quả mong đợi
                String expectedMessage = "Tài khoản hoặc mật khẩu không chính xác. Vui lòng kiểm tra lại!";
                Assert.assertEquals(actualMessage, expectedMessage, "Thông báo không chính xác!");
            }

            // Kiểm tra xem người dùng có bị giữ lại ở trang đăng nhập hay không
            String currentURL = driver.getCurrentUrl();
            System.out.println("Expected URL (Trang đăng nhập): " + loginURL);
            System.out.println("Actual URL: " + currentURL);

            // Đảm bảo rằng vẫn ở trang đăng nhập, tức là đăng nhập không thành công
            Assert.assertEquals(currentURL, loginURL, "Hệ thống không nên cho phép đăng nhập với email sai định dạng!");
        }

    @Test(priority = 3)
    public void testEmptyPassword() throws InterruptedException {
        driver.findElement(By.id("SignInEmail")).sendKeys("innologic25.team@gmail.com");
        driver.findElement(By.id("password-field")).sendKeys("");
        driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]")).click();
        sleep(2);


        // Kiểm tra nếu popup lỗi xuất hiện
        if (popupHandler.isPopupPresent("formErrorContent")) {
            String actualMessage = popupHandler.getPopupMessage("formErrorContent");
            System.out.println("Thông báo từ hệ thống: " + actualMessage);

            // So sánh kết quả mong đợi
            String expectedMessage = "* Trường này bắt buộc";
            Assert.assertEquals(actualMessage, expectedMessage, "Thông báo không chính xác!");
        } else {
            System.out.println("Không có thông báo lỗi xuất hiện!");
        }

    }

        @Test(priority = 4)
    public void testEmptyEmail() throws InterruptedException {
        driver.findElement(By.id("SignInEmail")).sendKeys("");
        driver.findElement(By.id("password-field")).sendKeys("innologic2025");
        driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]")).click();
        sleep(2);


            if (popupHandler.isPopupPresent("formErrorContent")) {
                String actualMessage = popupHandler.getPopupMessage("formErrorContent");
                System.out.println("Thông báo từ hệ thống: " + actualMessage);

                // So sánh kết quả mong đợi
                String expectedMessage = "* Trường này bắt buộc";
                Assert.assertEquals(actualMessage, expectedMessage, "Thông báo không chính xác!");
            } else {
                System.out.println("Không có thông báo lỗi xuất hiện!");
            }
        }

    @Test(priority = 5)
    public void testEmptyEmailAndPassword() throws InterruptedException {
        driver.findElement(By.id("SignInEmail")).sendKeys("");
        driver.findElement(By.id("password-field")).sendKeys("");
        driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]")).click();
        sleep(2);


        // Kiểm tra nếu có alert xuất hiện
        if (notification.isAlertPresent()) {
            String actualMessage = notification.getAlertText();
            System.out.println("Thông báo từ hệ thống: " + actualMessage);
            notification.acceptAlert(); // Đóng thông báo nếu có

            // So sánh kết quả mong đợi
            String expectedMessage = "Tài khoản hoặc mật khẩu không chính xác. Vui lòng kiểm tra lại!";
            Assert.assertEquals(actualMessage, expectedMessage, "Thông báo không chính xác!");
        }

        // Kiểm tra xem người dùng có bị giữ lại ở trang đăng nhập hay không
        String currentURL = driver.getCurrentUrl();
        System.out.println("Expected URL (Trang đăng nhập): " + loginURL);
        System.out.println("Actual URL: " + currentURL);

        // Đảm bảo rằng vẫn ở trang đăng nhập, tức là đăng nhập không thành công
        Assert.assertEquals(currentURL, loginURL, "Hệ thống không nên cho phép đăng nhập với email sai định dạng!");
    }

    @Test(priority = 6)
    public void testInvalidEmailSpecialChars() throws InterruptedException {
        // Nhập email chứa ký tự đặc biệt không hợp lệ
        driver.findElement(By.id("SignInEmail")).sendKeys("user!@gmail.com");
        driver.findElement(By.id("password-field")).sendKeys("validPassword123");

        // Gửi biểu mẫu đăng nhập
        driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]")).click();
        Thread.sleep(2000);

        // Kiểm tra nếu có alert xuất hiện
        if (notification.isAlertPresent()) {
            String actualMessage = notification.getAlertText();
            System.out.println("Thông báo từ hệ thống: " + actualMessage);
            notification.acceptAlert(); // Đóng thông báo nếu có

            // So sánh kết quả mong đợi
            String expectedMessage = "Tài khoản hoặc mật khẩu không chính xác. Vui lòng kiểm tra lại!";
            Assert.assertEquals(actualMessage, expectedMessage, "Thông báo không chính xác!");
        }

        // Kiểm tra xem người dùng có bị giữ lại ở trang đăng nhập hay không
        String currentURL = driver.getCurrentUrl();
        System.out.println("Expected URL (Trang đăng nhập): " + loginURL);
        System.out.println("Actual URL: " + currentURL);

        // Đảm bảo rằng vẫn ở trang đăng nhập, tức là đăng nhập không thành công
        Assert.assertEquals(currentURL, loginURL, "Hệ thống không nên cho phép đăng nhập với email sai định dạng!");
    }


    @Test(priority = 7)
    public void testShortPassword() throws InterruptedException {
        // Nhập email hợp lệ
        driver.findElement(By.id("SignInEmail")).sendKeys("user@gmail.com");
        // Nhập mật khẩu dưới 6 ký tự
        driver.findElement(By.id("password-field")).sendKeys("12345");

        // Gửi biểu mẫu đăng nhập
        driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]")).click();
        Thread.sleep(2000);

        if (notification.isAlertPresent()) {
            String alertText = notification.getAlertText();
            System.out.println("Thông báo từ hệ thống: " + alertText);
            notification.acceptAlert();
        }

        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, loginURL, "Hệ thống không chặn mật khẩu quá ngắn!");

        System.out.println("Testcase: Password dưới 6 ký tự - Passed");
    }

    @Test(priority = 8)
    public void testEmailContainingSpaces() throws InterruptedException {
        // Nhập email có khoảng trắng
        driver.findElement(By.id("SignInEmail")).sendKeys("user @gmail.com");
        driver.findElement(By.id("password-field")).sendKeys("validPassword123");

        // Gửi biểu mẫu đăng nhập
        driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]")).click();
        Thread.sleep(2000);

        if (notification.isAlertPresent()) {
            String alertText = notification.getAlertText();
            System.out.println("Thông báo từ hệ thống: " + alertText);
            notification.acceptAlert();
        }

        String currentURL = driver.getCurrentUrl();
        Assert.assertEquals(currentURL, loginURL, "Hệ thống không chặn email có khoảng trắng!");

        System.out.println("Testcase: Email có khoảng trắng - Passed");
    }

    @Test(priority = 9)
    public void testPasswordContainingSpaces() throws InterruptedException {
        // Nhập email hợp lệ
        driver.findElement(By.id("SignInEmail")).sendKeys("user@gmail.com");
        // Nhập mật khẩu có khoảng trắng
        driver.findElement(By.id("password-field")).sendKeys("123 456");

        // Gửi biểu mẫu đăng nhập
        driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]")).click();
        Thread.sleep(2000);

        // Kỳ vọng hệ thống không cho phép đăng nhập
        String expectedMessage = "Tài khoản hoặc mật khẩu không chính xác. Vui lòng kiểm tra lại!";
        String actualMessage = "Không có thông báo từ hệ thống"; // Mặc định nếu không có alert

        if (notification.isAlertPresent()) {
            actualMessage = notification.getAlertText();
            System.out.println("Thông báo từ hệ thống: " + actualMessage);
            notification.acceptAlert();
        }

        // So sánh thông báo kỳ vọng với thực tế
        Assert.assertEquals(actualMessage, expectedMessage, "Thông báo không khớp!");

        // Kiểm tra URL vẫn ở trang đăng nhập
        String actualURL = driver.getCurrentUrl();
        System.out.println("Expected URL: " + loginURL);
        System.out.println("Actual URL  : " + actualURL);

        Assert.assertEquals(actualURL, loginURL, "Hệ thống không nên cho phép đăng nhập với mật khẩu có khoảng trắng!");

        System.out.println("Testcase: Password có khoảng trắng - Passed");
    }


    @Test(priority = 10)
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

    @AfterMethod
    public void cleanupSuite() {
//        quitDriver();
    }
}