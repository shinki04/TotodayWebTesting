package tests;

import base.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.EditPassPage;
import utils.FileReader;

import java.util.Map;

import static utils.FileReader.readDataFromExcel;

public class EditPassTest extends BaseTest {
    private EditPassPage editPassPage;
    private final String loginURL = baseURL + "/user/signin";
    private final String changePasswordURL = baseURL + "/profile/changepassword";

    @BeforeClass
    private void setupClass() {
        editPassPage = new EditPassPage(driver, notification);
    }

    @AfterMethod
    private void cleanupTest() {
        driver.get(baseURL);
    }

    @Test(priority = 0)
    public void testChangePasswordWithSuccess() {
        String account = "Hoa0987.team@gmail.com";
        String currentPassword = "abcd123456";
        String newPassword = "NewPass@2025";

        // Đăng nhập ban đầu
        editPassPage.loginToAccount(loginURL, account, currentPassword);
        sleep(2);

        // Navigate to change password page and update password
        driver.get(changePasswordURL);
        sleep(2);
        WebElement currentPassField = editPassPage.enterCurrentPassword();
        if (currentPassField != null) {
            currentPassField.sendKeys(currentPassword);
        }
        sleep(1);
        editPassPage.enterNewPassword(newPassword);
        sleep(1);
        editPassPage.enterConfirmPassword(newPassword);
        sleep(1);
        editPassPage.clickUpdateBtn();
        sleep(3);

    }

    @DataProvider(name = "passwordChangeData")
    public Object[][] passwordChangeData() {
        return readDataFromExcel("src/test/resources/edit_password.xlsx", "Sheet1");
    }

    @Test(dataProvider = "passwordChangeData", priority = 0)
    public void testChangePassword(Map<String, String> data) {
        String account = data.get("Account");
        String currentPassword = data.get("Current Password");
        String newPassword = data.get("New Password");
        String confirmPassword = data.get("Confirm Password");
        String testCase = data.get("TestCase");

        System.out.println("Test case: " + testCase );
        System.out.println("Account: " + account );
        System.out.println("Current Password: " + currentPassword );
        System.out.println("New Password: " + newPassword );
        System.out.println("Confirm Password: " + confirmPassword);
        // Đăng nhập với account và currentPassword
        editPassPage.loginToAccount(loginURL, account, currentPassword);

        // Thực hiện đổi mật khẩu
        editPassPage.changePassword(loginURL, changePasswordURL, account, currentPassword, newPassword, confirmPassword);

        // Kiểm tra kết quả
        String successMessage = editPassPage.getSuccessMessageText(); // Đổi tên
        if (testCase.equals("Đổi mật khẩu thành công")) {
            // Kỳ vọng thành công: Có thông báo với class "alert alert-warning"
            Assert.assertNotNull(successMessage, "Success message should be displayed for: " + account);
            Assert.assertEquals(successMessage.trim(), "Đổi mật khẩu tài khoản thành công",
                    "Success message should match expected text");
            Assert.assertTrue(editPassPage.isSuccessMessageDisplayed(),
                    "Success message with class 'alert alert-warning' should be displayed for: " + account);
            editPassPage.clickLogoutButton();
            editPassPage.loginToAccount(loginURL, account, newPassword);
        } else {
            // Kỳ vọng thất bại: Không có thông báo
            Assert.assertFalse(editPassPage.isSuccessMessageDisplayed(),
                    "No success message should be displayed for failed case: " + account);
        }
    }
}