package tests;

import base.BaseTest;
import config.DriverConfig;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.AccountInformationPage;

import java.io.IOException;

/**
 * Class test cho trang thông tin tài khoản
 */
public class AccountInformationTest extends BaseTest {

    private AccountInformationPage accInformationPage;

    @BeforeClass
    public void setupClass() {
        try {
            accInformationPage = new AccountInformationPage(DriverConfig.getDriver());
            accInformationPage.loginToAccount();
        } catch (IOException e) {
            System.err.println("Không thể khởi tạo AccountInformationPage do lỗi đọc file Excel: " + e.getMessage());
            Assert.fail("Lỗi trong setupClass: " + e.getMessage());
        }
    }

    @BeforeMethod
    public void setupMethod() {
        accInformationPage.navigateToProfile();
    }

    @Test
    public void testEditFullName() {
        String actualFullName = accInformationPage.updateFullNameFromExcel(0);
        String expectedFullName = accInformationPage.getExpectedFullName(0);
        Assert.assertEquals(actualFullName, expectedFullName, "Họ tên không được cập nhật!");
        sleep(2);
    }

    @Test(priority = 2)
    public void testEditBirthday() throws InterruptedException {
        String actualBirthday = accInformationPage.updateBirthday("15/08/1995");
        String expectedBirthday = "15/08/1995";
        Assert.assertEquals(actualBirthday, expectedBirthday, "Ngày sinh không được cập nhật chính xác!");
        sleep(2);
    }

    @Test(priority = 3)
    public void testEditPhoneNumber() throws InterruptedException {
        String originalPhoneNumber = accInformationPage.getPhoneNumber();
        String finalPhoneNumber = accInformationPage.tryUpdatePhoneNumber();
        Assert.assertEquals(finalPhoneNumber, originalPhoneNumber,
                "Số điện thoại không được phép cập nhật vì có thuộc tính disabled, nhưng nó đã bị thay đổi!");
        sleep(2);

        String expectedNewPhone = accInformationPage.getExpectedPhoneNumber();
        Assert.assertNotEquals(finalPhoneNumber, expectedNewPhone,
                "Số điện thoại không nên khớp với giá trị từ Excel vì nó bị disabled!");
        sleep(2);
    }

    @Test(priority = 4)
    public void testEditEmail() throws InterruptedException {
        String randomEmail = "random" + System.currentTimeMillis() + "@gmail.com";
        sleep(2);
        String actualEmail = accInformationPage.updateEmail(randomEmail);
        String expectedEmail = "innologic25.team@gmail.com";
        Assert.assertEquals(actualEmail, expectedEmail, "Email đã thực hiện việc cập nhật!");
        sleep(2);
    }

    @Test(priority = 5)
    public void testSelectMaleRadio() throws InterruptedException {
        boolean isSelected = accInformationPage.selectAndUpdateMaleGender();
        Assert.assertTrue(isSelected, "Radio button 'male' không được chọn!");
        sleep(2);
    }

    @Test(priority = 6)
    public void testSelectFemaleRadio() throws InterruptedException {
        boolean isSelected = accInformationPage.selectAndUpdateFemaleGender();
        Assert.assertTrue(isSelected, "Radio button 'female' không được chọn!");
        sleep(2);
    }

    @Test(priority = 7)
    public void testUpdateAddress() throws InterruptedException {
        String actualAddress = accInformationPage.updateAddressFromExcel();
        String expectedAddress = accInformationPage.getExpectedAddress();
        Assert.assertEquals(actualAddress, expectedAddress, "Địa chỉ cập nhật không đúng!");
        sleep(2);
    }

    @Test(priority = 8)
    public void testSelectProvince() throws InterruptedException {
        String actualProvince = accInformationPage.selectAndUpdateProvince("Hồ Chí Minh");
        String expectedProvince = "Hồ Chí Minh";
        Assert.assertEquals(actualProvince, expectedProvince, "Lỗi chọn tỉnh!");
        sleep(2);
    }

    @Test(priority = 9, dependsOnMethods = "testSelectProvince")
    public void testSelectDistrict() throws InterruptedException {
        String actualDistrict = accInformationPage.selectAndUpdateDistrict("Quận 1");
        String expectedDistrict = "Quận 1";
        Assert.assertEquals(actualDistrict, expectedDistrict, "Lỗi chọn quận!");
        sleep(2);
    }

    @Test(priority = 10, dependsOnMethods = "testSelectDistrict")
    public void testSelectWardAndSubmit() throws InterruptedException {
        String actualWard = accInformationPage.selectAndUpdateWard("Phường Đa Kao");
        String expectedWard = "Phường Đa Kao";
        Assert.assertEquals(actualWard, expectedWard, "Lỗi chọn phường!");
        sleep(2);
    }

    @AfterClass
    public void tearDown() {
//        cleanupSuite();
    }
}