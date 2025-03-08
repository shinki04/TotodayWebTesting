package tests;

import base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.ExcelReader;
import utils.Notification;
import utils.Tools;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AccountInformationTest  extends BaseTest {
    private String loginURL = baseURL + "/user/signin";

      private Notification notification;

    @BeforeClass
    public void setupClass() {
        driver = getDriver();
        driver.get(loginURL);
        tools = new Tools(driver);

        driver.manage().window().maximize();
        excelReader = new ExcelReader("./src/test/resources/accountln_information.xlsx");
        notification = new Notification(driver);
        // Đăng nhập tài khoản
        driver.findElement(By.id("SignInEmail")).sendKeys("innologic25.team@gmail.com");
        driver.findElement(By.id("password-field")).sendKeys("innologic2025");
        driver.findElement(By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]")).click();

        // Xác nhận thông báo nếu có
        notification.acceptAlert();

    }

    @BeforeTest
    public void loginToAccount(){

    }

    @BeforeMethod
    public void setupTestMethod() {
        driver.get(baseURL + "/profile");
//        driver.findElement(By.xpath("//img[@alt='Tài khoản']")).click();
//        driver.findElement(By.xpath("//div[@class='user']//li[1]")).click();
    }



    @Test
    public void testEditFullName() throws InterruptedException, IOException {
        List<String[]> fullNameData = excelReader.readExcelData(0);
        for (String[] row : fullNameData) {
            String newFullName = row[0]; // Lấy dữ liệu từ cột đầu tiên (fullname)
            System.out.println(newFullName);

            driver.findElement(By.id("floatingInput")).clear();
            driver.findElement(By.id("floatingInput")).sendKeys(newFullName);
            driver.findElement(By.xpath("//button[contains(text(),'Cập nhật')]")).submit();

            WebElement updatedFullName = driver.findElement(By.id("floatingInput"));
            String actualFullName = updatedFullName.getAttribute("value");

            Assert.assertEquals(actualFullName, newFullName, "Họ tên không được cập nhật!");

        }
    }
    @Test (priority = 2)
    public void testEditBirthday() {

        String newBirthday = "15/08/1995";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = arguments[1];", driver.findElement(By.id("birthday")), "15/08/1995"); // YYYY-MM-DD
        driver.findElement(By.xpath("//button[contains(text(),'Cập nhật')]")).submit();

        // Kiểm tra lại giá trị đã thay đổi thành công chưa
        WebElement updatedFullName = driver.findElement(By.id("birthday"));
        String actualBirthday = updatedFullName.getAttribute("value"); // Lấy nội dung của ô input
        System.out.println("Sinh nhật sau khi cập nhật: " + actualBirthday);
        // Chuyển đổi về định dạng dd/MM/yyyy để kiểm tra
        LocalDate formattedDate = LocalDate.parse(actualBirthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String formattedBirthday = formattedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Assert.assertEquals(formattedBirthday, newBirthday, "Họ tên không được cập nhật chính xác!");
    }

    @Test(priority = 3)
    public void testEditPhoneNumber() {
        String randomPhone = "0" + tools.generateRandomNumber(9);
        WebElement updatedPhoneNumber = driver.findElement(By.xpath("(//input[@placeholder='Số điện thoại'])[1]"));

        // Dùng JavaScript để xóa thuộc tính 'disabled'
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].removeAttribute('disabled');", updatedPhoneNumber);
        String expectedPhoneNumber = updatedPhoneNumber.getAttribute("value");
        // Xóa nội dung cũ và nhập số điện thoại mới
        updatedPhoneNumber.clear();
        updatedPhoneNumber.sendKeys(randomPhone);

        // Nhấn nút cập nhật
        driver.findElement(By.xpath("//button[contains(text(),'Cập nhật')]")).submit();
        sleep(2);

        WebElement inputPhoneNumber = driver.findElement(By.xpath("(//input[@placeholder='Số điện thoại'])[1]"));
        js.executeScript("arguments[0].removeAttribute('disabled');", inputPhoneNumber);

        // Lấy nội dung mới từ ô input
        String actualPhoneNumber = inputPhoneNumber.getAttribute("value");
        System.out.println("Số điện thoại sau khi cập nhật: " + actualPhoneNumber);

        // Kiểm tra kết quả so sánh
        Assert.assertEquals(actualPhoneNumber, expectedPhoneNumber, "Số điện thoại đã thực hiện cập nhật!");
    }




    @Test(priority = 4)
    public void testEditEmail() {
        String expectedEmail = "innologic25.team@gmail.com";
        String randomEmail = tools.generateRandomString(10) + "@gmail.com";
        // Tìm lại phần tử email
        sleep(3);
        WebElement updatedEmail = driver.findElement(By.id("fEmail"));
        // Dùng JavaScript để xóa thuộc tính 'disabled'
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].removeAttribute('disabled');", updatedEmail);
        updatedEmail.clear();
        updatedEmail.sendKeys(randomEmail);

        // Nhấn nút cập nhật
        WebElement updateButton = driver.findElement(By.xpath("//button[contains(text(),'Cập nhật')]"));
        updateButton.submit();

        // Đợi trang tải lại và lấy lại phần tử email
        WebElement inputEmail = driver.findElement(By.id("fEmail"));
        // Lấy giá trị mới từ ô input
        String actualEmail = inputEmail.getAttribute("value");

        // Kiểm tra kết quả
        Assert.assertEquals(actualEmail, expectedEmail, "Email đã thực hiện việc cập nhật!");
    }


    @Test(priority = 5)
    public void testSelectMaleRadio() throws InterruptedException {
        // Giá trị mong đợi
        String expectedMaleValue = "1";

        // Chọn radio button "male"
        WebElement maleRadio = driver.findElement(By.xpath("(//input[@id='gender'])[1]"));
        maleRadio.click();
        sleep(2);
        Assert.assertTrue(maleRadio.isSelected() , "Giá trị radio button 'male' không đúng!"); // Truoc khi ấn submit
        driver.findElement(By.xpath("//button[contains(text(),'Cập nhật')]")).submit();

    }

    @Test(priority = 6)
    public void testSelectFemaleRadio() throws InterruptedException {
        // Giá trị mong đợi
        String expectedFemaleValue = "2";

        // Chọn radio button "male"
        WebElement femaleRadio = driver.findElement(By.xpath("(//input[@id='gender'])[2]"));
        femaleRadio.click();
        sleep(2);
        Assert.assertTrue(femaleRadio.isSelected() , "Giá trị radio button 'male' không đúng!");
        driver.findElement(By.xpath("//button[contains(text(),'Cập nhật')]")).submit();


    }


    @Test(priority = 7)
    public void testUpdateAddress() throws InterruptedException, IOException  {
        List<String[]> fullAddressData = excelReader.readExcelData(0);
        for (String[] row : fullAddressData) {
            String newAddress = row[2];
            System.out.println(newAddress);

            driver.findElement(By.id("address")).clear();
            driver.findElement(By.id("address")).sendKeys(newAddress);
            driver.findElement(By.xpath("//button[contains(text(),'Cập nhật')]")).submit();

            WebElement inputAddress = driver.findElement(By.id("address"));
            // Lấy lại giá trị địa chỉ sau khi cập nhật
            String actualAddress = inputAddress.getAttribute("value");
            Assert.assertEquals(actualAddress, newAddress, "Địa chỉ cập nhật không đúng!");


        }

//        String expectedAddress = "1225 Đường ABC, Quận XYZ, Thành phố HCM";
//        // Nhập địa chỉ mới
//        WebElement addressField = driver.findElement(By.id("address"));
//        addressField.clear();
//        addressField.sendKeys(expectedAddress);
//
//        // Nhấn nút "Cập nhật"
//        WebElement updateButton = driver.findElement(By.xpath("//button[contains(text(),'Cập nhật')]"));
//        updateButton.submit();
//
//        WebElement inputAddress = driver.findElement(By.id("address"));
//        // Lấy lại giá trị địa chỉ sau khi cập nhật
//        String actualAddress = inputAddress.getAttribute("value");
//        System.out.println("Địa chỉ sau khi cập nhật: " + actualAddress);
//
//        // Kiểm tra địa chỉ đã được cập nhật đúng
//        Assert.assertEquals(actualAddress, expectedAddress, "Địa chỉ cập nhật không đúng!");
    }

    @Test(priority = 8)
    public void testSelectProvince() {
        WebElement provinceElement = driver.findElement(By.id("cityId"));
        Select provinceDropdown = new Select(provinceElement);
        String expectedProvince = "Hồ Chí Minh";
        provinceDropdown.selectByVisibleText(expectedProvince);
        sleep(5);

        String actualProvince = provinceDropdown.getFirstSelectedOption().getText();
        Assert.assertEquals(actualProvince, expectedProvince, "Lỗi chọn tỉnh");
    }

    @Test(priority = 9, dependsOnMethods = "testSelectProvince")
    public void testSelectDistrict() {
        WebElement districtElement = driver.findElement(By.id("districtId"));
        Select districtDropdown = new Select(districtElement);
        String expectedDistrict = "Quận 1";
        districtDropdown.selectByVisibleText(expectedDistrict);
        sleep(5);

        String actualDistrict = districtDropdown.getFirstSelectedOption().getText();
        Assert.assertEquals(actualDistrict, expectedDistrict, "Lỗi chọn quận");
    }

    @Test(priority = 10, dependsOnMethods = "testSelectDistrict")
    public void testSelectWardAndSubmit() {
        // Tìm lại phần tử phường sau khi quận đã được chọn
        WebElement wardElement = driver.findElement(By.id("wardId"));
        Select wardDropdown = new Select(wardElement);
        String expectedWard = "Phường Đa Kao";
        wardDropdown.selectByVisibleText(expectedWard);
        sleep(5);

        driver.findElement(By.xpath("//button[contains(text(),'Cập nhật')]")).submit();

        // Tìm lại dropdown sau khi đã submit để tránh stale element
        wardElement = driver.findElement(By.id("wardId"));
        wardDropdown = new Select(wardElement);
        String actualWard = wardDropdown.getFirstSelectedOption().getText();

        Assert.assertEquals(actualWard, expectedWard, "Lỗi chọn phường");
    }


    @AfterClass
    public void cleanupSuite() {
//        cleanupTest();
    }

}
