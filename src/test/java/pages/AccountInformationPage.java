package pages;

import base.BaseTest;
import config.DriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ExcelReader;
import utils.Notification;
import utils.PopupHandler;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Page Object đại diện cho trang thông tin tài khoản
 */
public class AccountInformationPage extends BaseTest {

    // Khai báo biến và locator
    private WebDriver driver;
    private ExcelReader excelReader;
    private List<String[]> excelData;
    private String loginURL = DriverConfig.baseURL + "/user/signin";
    private String profileURL = DriverConfig.baseURL + "/profile";

    // Các locator của các element Field
    private By signInEmailField = By.id("SignInEmail");
    private By passwordField = By.id("password-field");
    private By fullNameField = By.id("floatingInput");
    private By birthdayField = By.id("birthday");
    private By phoneField = By.xpath("//input[@placeholder='Số điện thoại']");
    private By emailField = By.xpath("//input[@id='fEmail']");
    private By maleRadio = By.xpath("(//input[@id='gender'])[1]");
    private By femaleRadio = By.xpath("(//input[@id='gender'])[2]");
    private By addressField = By.id("address");
    private By provinceDropdown = By.id("cityId");
    private By districtDropdown = By.id("districtId");
    private By wardDropdown = By.id("wardId");

    // Các locator của các element Button
    private By loginButton = By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]");
    private By updateButton = By.xpath("//button[contains(text(),'Cập nhật')]");
    private By userButton = By.xpath("//img[@alt='Tài khoản']");
    private By logoutButton = By.xpath("//a[@href='/user/signout']");

    // Constructor - Khởi tạo đối tượng trang
    public AccountInformationPage(WebDriver driver) throws IOException {
        this.driver = driver;
        this.excelReader = new ExcelReader("./src/test/resources/accountln_information.xlsx");
        this.excelData = excelReader.readExcelData(0);
        this.notification = new Notification(driver);
        this.popupHandler = new PopupHandler(driver);
    }

    /**
     * Các phương thức chính xử lý luồng nghiệp vụ
     */
    // Đăng nhập vào hệ thống
    public void loginToAccount() {
        driver.get(loginURL);
        driver.manage().window().maximize();
        driver.findElement(signInEmailField).sendKeys("innologic25.team@gmail.com");
        driver.findElement(passwordField).sendKeys("innologic2025");
        WebElement loginBtn = waitForElement(loginButton);
        loginBtn.click();
        notification.acceptAlert();
    }

    // Điều hướng đến trang profile
    public void navigateToProfile() {
        driver.get(profileURL);
    }

    // Đăng xuất khỏi hệ thống
    public void clickLogoutBtn() {
        driver.findElement(userButton).click();
        driver.findElement(logoutButton).click();
    }

    /**
     * Các phương thức cập nhật thông tin và kiểm tra kết quả
     */
    // Cập nhật họ tên từ Excel
    public String updateFullNameFromExcel(int rowIndex) {
        String expectedFullName = excelData.get(rowIndex)[0];
        enterFullName(expectedFullName);
        clickUpdateButton();
        String actualFullName = getFullName();
        printResult(actualFullName, expectedFullName);
        return actualFullName;
    }

    // Cập nhật ngày sinh
    public String updateBirthday(String newBirthday) {
        setBirthday("1995-08-15");
        clickUpdateButton();
        String actualBirthday = getBirthday();
        LocalDate formattedDate = LocalDate.parse(actualBirthday, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String formattedBirthday = formattedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        printResult(formattedBirthday, newBirthday);
        return formattedBirthday;
    }

    // Thử cập nhật số điện thoại từ Excel
    public String tryUpdatePhoneNumber() throws InterruptedException {
        String originalPhone = getPhoneNumber();
        clickUpdateButton();
        sleep(5);
        String finalPhone = getPhoneNumber();
        printResult(finalPhone, originalPhone);
        return finalPhone;
    }

    // Cập nhật email
    public String updateEmail(String randomEmail) throws InterruptedException {
        String expectedEmail = "innologic25.team@gmail.com";
        enterEmail(randomEmail);
        clickUpdateButton();
        sleep(5);
        String actualEmail = getEmail();
        printResult(actualEmail, expectedEmail);
        return actualEmail;
    }

    // Chọn và cập nhật giới tính nam
    public boolean selectAndUpdateMaleGender() throws InterruptedException {
        selectMaleGender();
        DriverConfig.sleep(2);
        clickUpdateButton();
        boolean actualSelected = isMaleSelected();
        printResult(String.valueOf(actualSelected), "true");
        return actualSelected;
    }

    // Chọn và cập nhật giới tính nữ
    public boolean selectAndUpdateFemaleGender() throws InterruptedException {
        selectFemaleGender();
        DriverConfig.sleep(2);
        clickUpdateButton();
        boolean actualSelected = isFemaleSelected();
        printResult(String.valueOf(actualSelected), "true");
        return actualSelected;
    }

    // Cập nhật địa chỉ từ Excel
    public String updateAddressFromExcel() throws InterruptedException {
        String expectedAddress = excelData.get(0)[4];
        enterAddress();
        clickUpdateButton();
        DriverConfig.sleep(1);
        String actualAddress = getAddress();
        printResult(actualAddress, expectedAddress);
        return actualAddress;
    }

    // Chọn và cập nhật tỉnh
    public String selectAndUpdateProvince(String province) throws InterruptedException {
        selectProvince(province);
        DriverConfig.sleep(5);
        clickUpdateButton();
        String actualProvince = getSelectedProvince();
        printResult(actualProvince, province);
        return actualProvince;
    }

    // Chọn và cập nhật quận
    public String selectAndUpdateDistrict(String district) throws InterruptedException {
        selectDistrict(district);
        DriverConfig.sleep(5);
        clickUpdateButton();
        String actualDistrict = getSelectedDistrict();
        printResult(actualDistrict, district);
        return actualDistrict;
    }

    // Chọn và cập nhật phường
    public String selectAndUpdateWard(String ward) throws InterruptedException {
        selectWard(ward);
        DriverConfig.sleep(5);
        clickUpdateButton();
        String actualWard = getSelectedWard();
        printResult(actualWard, ward);
        return actualWard;
    }

    /**
     * Các phương thức hỗ trợ nhập liệu
     */
    private void enterFullName(String fullName) {
        WebElement element = waitForElement(fullNameField);
        element.clear();
        element.sendKeys(fullName);
    }

    private void setBirthday(String birthday) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = arguments[1];", driver.findElement(birthdayField), birthday);
    }

    private void enterEmail(String email) {
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page source snippet: " + driver.getPageSource().substring(0, Math.min(500, driver.getPageSource().length())));
        WebElement element;
        try {
            element = waitForElement(emailField);
        } catch (org.openqa.selenium.TimeoutException e) {
            System.err.println("Không tìm thấy phần tử emailField (id=fEmail) sau 10 giây.");
            throw e;
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].removeAttribute('disabled');", element);
        element.clear();
        element.sendKeys(email);
    }

    private void selectMaleGender() {
        driver.findElement(maleRadio).click();
    }

    private void selectFemaleGender() {
        driver.findElement(femaleRadio).click();
    }

    private void enterAddress() {
        WebElement element = waitForElement(addressField);
        element.clear();
        String address = excelData.get(0)[4];
        element.sendKeys(address);
    }

    private void selectProvince(String province) {
        Select dropdown = new Select(waitForElement(provinceDropdown));
        dropdown.selectByVisibleText(province);
    }

    private void selectDistrict(String district) {
        Select dropdown = new Select(waitForElement(districtDropdown));
        dropdown.selectByVisibleText(district);
    }

    private void selectWard(String ward) {
        Select dropdown = new Select(waitForElement(wardDropdown));
        dropdown.selectByVisibleText(ward);
    }

    private void clickUpdateButton() {
        WebElement updateBtn = waitForElement(updateButton);
        updateBtn.submit();
    }

    /**
     * Các phương thức lấy giá trị từ giao diện
     */
    public String getFullName() {
        return driver.findElement(fullNameField).getAttribute("value");
    }

    public String getBirthday() {
        return driver.findElement(birthdayField).getAttribute("value");
    }

    public String getPhoneNumber() {
        return driver.findElement(phoneField).getAttribute("value");
    }

    public String getEmail() {
        return driver.findElement(emailField).getAttribute("value");
    }

    public boolean isMaleSelected() {
        return driver.findElement(maleRadio).isSelected();
    }

    public boolean isFemaleSelected() {
        return driver.findElement(femaleRadio).isSelected();
    }

    public String getAddress() {
        return driver.findElement(addressField).getAttribute("value");
    }

    public String getSelectedProvince() {
        Select dropdown = new Select(driver.findElement(provinceDropdown));
        return dropdown.getFirstSelectedOption().getText();
    }

    public String getSelectedDistrict() {
        Select dropdown = new Select(driver.findElement(districtDropdown));
        return dropdown.getFirstSelectedOption().getText();
    }

    public String getSelectedWard() {
        Select dropdown = new Select(driver.findElement(wardDropdown));
        return dropdown.getFirstSelectedOption().getText();
    }

    /**
     * Các phương thức lấy dữ liệu kỳ vọng từ Excel
     */
    public String getExpectedFullName(int rowIndex) {
        return excelData.get(rowIndex)[0];
    }

    public String getExpectedPhoneNumber() {
        return excelData.get(0)[2];
    }

    public String getExpectedAddress() {
        return excelData.get(0)[4];
    }

    /**
     * Các phương thức tiện ích
     */
    private void printResult(String actual, String expected) {
        System.out.println("==========================================");
        System.out.println("Actual : " + actual);
        System.out.println("Expect : " + expected);
        System.out.println("==========================================");
    }

    private WebElement waitForElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
}