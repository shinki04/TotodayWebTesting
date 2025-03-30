package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ExcelReader;
import utils.Notification;

import java.io.IOException;
import java.util.List;

//@Listeners(utils.ExcelTestListener.class)

public class LoginTest extends BaseTest {
    private LoginPage loginPage;
    private Notification notification;

    @BeforeMethod
    public void setupMethod() {
        loginPage = new LoginPage(driver, baseURL);
        notification = new Notification(driver);
        loginPage.navigateToLoginPage();
    }

    @Test(priority = 0)
    public void testSuccess() throws InterruptedException, IOException {
        driver.manage().window().maximize();
        excelReader = new ExcelReader("./src/test/resources/login.xlsx");
        List<String[]> loginData = excelReader.readExcelData(0);
        String email = loginData.get(0)[0];
        String password = loginData.get(0)[1];

        loginPage.login(email, password);
        Thread.sleep(2000);

        if (notification.isAlertPresent()) {
            String actualMessage = notification.getAlertText();
            System.out.println("Thông báo từ hệ thống: " + actualMessage);
            notification.acceptAlert();
            String expectedMessage = "Đăng nhập thành công!";
            Assert.assertEquals(actualMessage, expectedMessage, "Thông báo không khớp!");
        }

        String currentURL = loginPage.getCurrentUrl();
        Assert.assertNotEquals(currentURL, baseURL + "/user/signin", "Đăng nhập thất bại!");
        System.out.println("Đăng nhập thành công! Hiện tại ở URL: " + currentURL);

        loginPage.logout();
    }

    @Test(priority = 1)
    public void testNonExistentEmail() throws InterruptedException {
        loginPage.login("invalid_email@gmail.com", "innologic2025");
        Thread.sleep(2000);

        if (notification.isAlertPresent()) {
            String actualMessage = notification.getAlertText();
            System.out.println("Thông báo từ hệ thống: " + actualMessage);
            notification.acceptAlert();
            String expectedMessage = "Tài khoản hoặc mật khẩu không chính xác. Vui lòng kiểm tra lại!";
            Assert.assertEquals(actualMessage, expectedMessage, "Thông báo không chính xác!");
        }

        String currentURL = loginPage.getCurrentUrl();
        Assert.assertEquals(currentURL, baseURL + "/user/signin", "Không nên đăng nhập với email không tồn tại!");
    }

    @Test(priority = 2)
    public void testInvalidEmailFormat() throws InterruptedException {
        loginPage.login("invalid-email-format", "innologic2025");
        Thread.sleep(2000);

        if (notification.isAlertPresent()) {
            String actualMessage = notification.getAlertText();
            System.out.println("Thông báo từ hệ thống: " + actualMessage);
            notification.acceptAlert();
            String expectedMessage = "Tài khoản hoặc mật khẩu không chính xác. Vui lòng kiểm tra lại!";
            Assert.assertEquals(actualMessage, expectedMessage, "Thông báo không chính xác!");
        }

        String currentURL = loginPage.getCurrentUrl();
        Assert.assertEquals(currentURL, baseURL + "/user/signin", "Không nên đăng nhập với email sai định dạng!");
    }

    @Test(priority = 3)
    public void testEmptyPassword() throws InterruptedException {
        loginPage.login("innologic25.team@gmail.com", "");
        Thread.sleep(2000);

        if (popupHandler.isPopupPresent("formErrorContent")) {
            String actualMessage = popupHandler.getPopupMessage("formErrorContent");
            System.out.println("Thông báo từ hệ thống: " + actualMessage);
            String expectedMessage = "* Trường này bắt buộc";
            Assert.assertEquals(actualMessage, expectedMessage, "Thông báo không chính xác!");
        }
    }

    @Test(priority = 4)
    public void testEmptyEmail() throws InterruptedException {
        loginPage.login("", "innologic2025");
        Thread.sleep(2000);

        if (popupHandler.isPopupPresent("formErrorContent")) {
            String actualMessage = popupHandler.getPopupMessage("formErrorContent");
            System.out.println("Thông báo từ hệ thống: " + actualMessage);
            String expectedMessage = "* Trường này bắt buộc";
            Assert.assertEquals(actualMessage, expectedMessage, "Thông báo không chính xác!");
        }
    }

    @Test(priority = 5)
    public void testEmptyEmailAndPassword() throws InterruptedException {
        loginPage.login("", "");
        Thread.sleep(2000);

        if (notification.isAlertPresent()) {
            String actualMessage = notification.getAlertText();
            System.out.println("Thông báo từ hệ thống: " + actualMessage);
            notification.acceptAlert();
            String expectedMessage = "Tài khoản hoặc mật khẩu không chính xác. Vui lòng kiểm tra lại!";
            Assert.assertEquals(actualMessage, expectedMessage, "Thông báo không chính xác!");
        }

        String currentURL = loginPage.getCurrentUrl();
        Assert.assertEquals(currentURL, baseURL + "/user/signin", "Không nên đăng nhập khi cả email và mật khẩu trống!");
    }

    @Test(priority = 6)
    public void testInvalidEmailSpecialChars() throws InterruptedException {
        loginPage.login("user!@gmail.com", "validPassword123");
        Thread.sleep(2000);

        if (notification.isAlertPresent()) {
            String actualMessage = notification.getAlertText();
            System.out.println("Thông báo từ hệ thống: " + actualMessage);
            notification.acceptAlert();
            String expectedMessage = "Tài khoản hoặc mật khẩu không chính xác. Vui lòng kiểm tra lại!";
            Assert.assertEquals(actualMessage, expectedMessage, "Thông báo không chính xác!");
        }

        String currentURL = loginPage.getCurrentUrl();
        Assert.assertEquals(currentURL, baseURL + "/user/signin", "Không nên đăng nhập với email chứa ký tự đặc biệt!");
    }

    @Test(priority = 7)
    public void testShortPassword() throws InterruptedException {
        loginPage.login("user@gmail.com", "12345");
        Thread.sleep(2000);

        if (notification.isAlertPresent()) {
            String alertText = notification.getAlertText();
            System.out.println("Thông báo từ hệ thống: " + alertText);
            notification.acceptAlert();
        }

        String currentURL = loginPage.getCurrentUrl();
        Assert.assertEquals(currentURL, baseURL + "/user/signin", "Không nên đăng nhập với mật khẩu quá ngắn!");
    }

    @Test(priority = 8)
    public void testEmailContainingSpaces() throws InterruptedException {
        loginPage.login("user @gmail.com", "validPassword123");
        Thread.sleep(2000);

        if (notification.isAlertPresent()) {
            String alertText = notification.getAlertText();
            System.out.println("Thông báo từ hệ thống: " + alertText);
            notification.acceptAlert();
        }

        String currentURL = loginPage.getCurrentUrl();
        Assert.assertEquals(currentURL, baseURL + "/user/signin", "Không nên đăng nhập với email chứa khoảng trắng!");
    }

    @Test(priority = 9)
    public void testPasswordContainingSpaces() throws InterruptedException {
        loginPage.login("user@gmail.com", "123 456");
        Thread.sleep(2000);

        String expectedMessage = "Tài khoản hoặc mật khẩu không chính xác. Vui lòng kiểm tra lại!";
        String actualMessage = "Không có thông báo từ hệ thống";

        if (notification.isAlertPresent()) {
            actualMessage = notification.getAlertText();
            System.out.println("Thông báo từ hệ thống: " + actualMessage);
            notification.acceptAlert();
        }

        Assert.assertEquals(actualMessage, expectedMessage, "Thông báo không khớp!");
        String currentURL = loginPage.getCurrentUrl();
        Assert.assertEquals(currentURL, baseURL + "/user/signin", "Không nên đăng nhập với mật khẩu chứa khoảng trắng!");
    }

    @Test(priority = 10)
    public void testPasswordVisibilityToggle() throws InterruptedException {
        loginPage.enterEmail("innologic25.team@gmail.com");
        loginPage.enterPassword("innologic2025");

        loginPage.togglePasswordVisibility();
        Thread.sleep(1000);
        String inputType = loginPage.getPasswordFieldType();
        Assert.assertEquals(inputType, "text", "Mật khẩu không hiển thị đúng!");

        loginPage.togglePasswordVisibility();
        Thread.sleep(1000);
        inputType = loginPage.getPasswordFieldType();
        Assert.assertEquals(inputType, "password", "Mật khẩu không ẩn đúng!");
    }

    @AfterMethod
    public void cleanupSuite() {
//        cleanupSuite();
    }
}