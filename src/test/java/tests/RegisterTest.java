package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.RegisterPage;
import utils.Tools;

//@Listeners(utils.ExcelTestListener.class)

public class RegisterTest extends BaseTest {
    private RegisterPage registerPage;

    @BeforeMethod
    public void setupTest() {
        registerPage = new RegisterPage(getDriver());
        registerPage.navigateToSignInPage(); // Chuyển đến trang đăng nhập trước
        registerPage.openRegisterTab(); // Sau đó mới nhấn vào tab đăng ký
    }

    @Test(priority = 0)
    public void testRegistrationWithSuccess() {
        String randomName = Tools.generateRandomString(10);
        String randomPhone = "0" + Tools.generateRandomNumber(9);
        String randomEmail = Tools.generateRandomString(10) + "@gmail.com";
        String randomPassword = Tools.generateRandomString(7);

        registerPage.fillRegistrationForm(randomName, randomPhone, randomEmail, randomPassword);
        registerPage.submitRegistration();

        if (registerPage.isAlertPresent()) {
            System.out.println("Thông báo xuất hiện: " + registerPage.getAlertText());
            registerPage.acceptAlert();
        } else {
            System.out.println("Không có thông báo hiển thị - Đăng ký thành công - Pass");
        }
    }

    @Test(priority = 1)
    public void testRegisterWithExistingAccount() {
        registerPage.fillRegistrationForm("Innologic", "0300000009", "innologic25.team@gmail.com", "innologic2025");
        sleep(3);
        registerPage.submitRegistration();
        sleep(3);

        if (registerPage.isAlertPresent()) {
            String errorMessage = "mật khẩu không đúng định dạng hoặc email đã tồn tại. Vui lòng kiểm tra lại!";
            String alertText = registerPage.getAlertText();
            System.out.println("Thông báo lỗi hiển thị: " + alertText);
            registerPage.acceptAlert();
            System.out.println("==========================================");
            System.out.println("Check Error : ");
            System.out.println("Actual : " + alertText);
            System.out.println("Expect : " + errorMessage);
            Assert.assertEquals(alertText, errorMessage, "Error Message not equal");
            System.out.println("==========================================");

        } else {
            System.out.println("Không có thông báo lỗi hiển thị");
            Assert.fail("Test thất bại: Hệ thống cho phép đăng ký tài khoản đã tồn tại.");
        }
    }

    @Test(priority = 2)
    public void testRegisterWithEmptyFullName() {
        registerPage.submitRegistration();
        sleep(3);

        Assert.assertTrue(getDriver().findElement(By.id("signUpFullName")).isDisplayed(), "Không có lỗi cho họ tên");
   }
    @Test(priority = 3)
    public void testRegisterWithEmptyMobile() {
        registerPage.submitRegistration();
        sleep(3);

        Assert.assertTrue(getDriver().findElement(By.id("mobile")).isDisplayed(), "Không có lỗi cho số điện thoại");
       }

    @Test(priority = 4)
    public void testRegisterWithEmptyEmail() {
        registerPage.submitRegistration();
        sleep(3);

        Assert.assertTrue(getDriver().findElement(By.id("signUpEmail")).isDisplayed(), "Không có lỗi cho email");
//        Assert.assertTrue(getDriver().findElement(By.id("signUpPassword")).isDisplayed(), "Không có lỗi cho mật khẩu");
    }
    @Test(priority = 5)
    public void testRegisterWithEmptyPassword() {
        registerPage.submitRegistration();
        sleep(3);

        Assert.assertTrue(getDriver().findElement(By.id("signUpPassword")).isDisplayed(), "Không có lỗi cho mật khẩu");
    }

    @Test(priority = 6)
    public void testRegisterWithInvalidEmail() {
        String randomName = Tools.generateRandomString(10);
        String randomPhone = "0" + Tools.generateRandomNumber(9);
        String randomEmail = Tools.generateRandomString(10) + "gemail.com"; // Email sai định dạng
        String randomPassword = Tools.generateRandomString(7);

        registerPage.fillRegistrationForm(randomName, randomPhone, randomEmail, randomPassword);
        sleep(3);
        registerPage.submitRegistration();
        sleep(3);

        if (registerPage.isAlertPresent()) {
            String errorMessage = "Email sai định dạng. Vui lòng kiểm tra lại!";
            String alertText = registerPage.getAlertText();
            System.out.println("Thông báo lỗi hiển thị: " + alertText);
            registerPage.acceptAlert();
            System.out.println("==========================================");
            System.out.println("Check Error : ");
            System.out.println("Actual : " + alertText);
            System.out.println("Expect : " + errorMessage);
            Assert.assertEquals(alertText, errorMessage, "Error Message not equal");
            System.out.println("==========================================");
        } else {
            System.out.println("Không có thông báo lỗi hiển thị");
            Assert.fail("Test thất bại: Hệ thống cho phép đăng ký với email sai định dạng.");
        }
    }
}