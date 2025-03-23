package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.RegisterPage;
import utils.Tools;

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
            System.out.println("Không có thông báo hiển thị");
        }
    }

    @Test(priority = 1)
    public void testRegisterWithExistingAccount() {
        registerPage.fillRegistrationForm("Innologic", "0300000009", "innologic25.team@gmail.com", "innologic2025");
        registerPage.submitRegistration();

        if (registerPage.isAlertPresent()) {
            String alertText = registerPage.getAlertText();
            System.out.println("Thông báo lỗi hiển thị: " + alertText);
            registerPage.acceptAlert();
            Assert.assertTrue(true, "Đăng ký thất bại như mong đợi do email đã tồn tại.");
        } else {
            System.out.println("Không có thông báo lỗi hiển thị");
            Assert.fail("Test thất bại: Hệ thống cho phép đăng ký tài khoản đã tồn tại.");
        }
    }

    @Test(priority = 2)
    public void testRegisterWithEmptyFields() {
        registerPage.submitRegistration();

        // Kiểm tra lỗi hiển thị trên các trường
        Assert.assertTrue(getDriver().findElement(By.id("signUpFullName")).isDisplayed(), "Không có lỗi cho họ tên");
        Assert.assertTrue(getDriver().findElement(By.id("mobile")).isDisplayed(), "Không có lỗi cho số điện thoại");
        Assert.assertTrue(getDriver().findElement(By.id("signUpEmail")).isDisplayed(), "Không có lỗi cho email");
        Assert.assertTrue(getDriver().findElement(By.id("signUpPassword")).isDisplayed(), "Không có lỗi cho mật khẩu");
    }

    @Test(priority = 3)
    public void testRegisterWithInvalidEmail() {
        String randomName = Tools.generateRandomString(10);
        String randomPhone = "0" + Tools.generateRandomNumber(9);
        String randomEmail = Tools.generateRandomString(10) + "gemail.com"; // Email sai định dạng
        String randomPassword = Tools.generateRandomString(7);

        registerPage.fillRegistrationForm(randomName, randomPhone, randomEmail, randomPassword);
        registerPage.submitRegistration();
    }
}
