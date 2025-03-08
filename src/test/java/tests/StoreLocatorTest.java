package tests;

import config.DriverConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.Tools;

import java.time.Duration;

public class StoreLocatorTest extends DriverConfig {

    private static Tools tools;
    private WebDriver driver;
    private WebElement searchInput;
    private WebElement messageNoResult;

    @BeforeClass
    void setupClass() {
        driver = getDriver();

    }

    @AfterClass
    void cleanupClass() {
//        quitDriver();
    }

    @BeforeMethod
    void setupMethod() {
        driver.findElement(By.linkText("Hệ thống cửa hàng")).click();
        searchInput = driver.findElement(By.id("locations"));
    }

    @Test()
    void testOpenStoreLocatorPage() {
        // Bước 1: Nhấn vào nút "Hệ thống cửa hàng"
        WebElement storeLocatorButton = driver.findElement(By.linkText("Hệ thống cửa hàng"));
        storeLocatorButton.click();
        sleep(1);
        WebElement storeListContainer = driver.findElement(By.xpath("(//h1[contains(text(),'Hệ thống cửa hàng')])[1]"));
        Assert.assertTrue(storeListContainer.isDisplayed(), "Trang hệ thống cửa hàng không hiển thị!");
    }

    @Test()
    //Tìm kiếm cửa hàng cần chọn tỉnh thành
    public void testAccessStoreLocatorPage() {
        WebElement dropdownCity = driver.findElement(By.xpath("(//select[@name='change-tinh'])[1]"));
        dropdownCity.click();

        Select selectCity = new Select(dropdownCity);
        selectCity.selectByVisibleText("Cần Thơ");
        String storeAddress = dropdownCity.getText().trim();
        System.out.println(" Địa chỉ kiểm tra: " + storeAddress);

        boolean containsCanTho = storeAddress.contains("Cần Thơ");

        if (containsCanTho) {
            System.out.println(" Có chi nhánh cửa hàng ở Cần thơ");
        } else {
            System.out.println("Không có chi nhánh cửa hàng ở Cần thơ");
        }

        // Assert để test pass hoặc fail
        Assert.assertTrue(containsCanTho, "Không có chi nhánh ở Cần Thơ");
    }

    @Test()
    //TÌm vị trí cửa hàng không cần tỉnh thành
    public void testFindStoreLocationGoVap() {

        WebElement goVapText = driver.findElement(By.xpath("(//b[contains(text(),'Quận Gò Vấp')])[1]"));
        Assert.assertNotNull(goVapText, "Không tìm thấy Quận Gò Vấp trên trang");

        WebElement xemBanDo = driver.findElement(By.xpath("(//li[5]//div[1]//a[1])"));
        try {
            xemBanDo.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", xemBanDo);
        }
        sleep(2);


        WebElement diaChiGoVap = driver.findElement(By.xpath("(//p[contains(text(),'Số 55 Quang Trung, Phường 10, Quận Gò Vấp, TP HCM')])[1]"));
        String addressGoVapText = diaChiGoVap.getText();

        WebElement mapAddress = driver.findElement(By.xpath("(//p[contains(text(),'Số 55 Quang Trung, Phường 10, Quận Gò Vấp, TP HCM')])"));
        String mapAddressText = mapAddress.getText();

        Assert.assertEquals(addressGoVapText, mapAddressText, "Địa chỉ trên bản đồ không khớp với địa chỉ Gò Vấp!");

        System.out.println("Test thành công: Địa chỉ cửa hàng Gò Vấp trùng khớp trên bản đồ!");
    }
    @Test
    public void testFindCityWithDropdown() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement dropdownCity = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@name='change-tinh']")));
        dropdownCity.click();

        Select selectCity = new Select(dropdownCity);
        selectCity.selectByValue("255");


        sleep(2);
        WebElement textThuDuc = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//b[contains(text(),'Quận Thủ Đức')]")));
        Assert.assertNotNull(textThuDuc, "Không tìm thấy Quận Thủ Đức trên trang");

        WebElement xemBanDo = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[1]//div[1]//a[1]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", xemBanDo);
        try {
            xemBanDo.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", xemBanDo);
        }
        sleep(2);
        WebElement diaChiThuDuc = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//p[contains(text(),'Số 170A Võ Văn Ngân, Phường Bình Thọ, Thủ Đức, TP ')]")));
        String addressThuDucText = diaChiThuDuc.getText();

        WebElement mapAddress1 = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//p[contains(text(),'Số 170A Võ Văn Ngân, Phường Bình Thọ, Thủ Đức, TP ')]")));
        String mapAddressText1 = mapAddress1.getText();

        Assert.assertEquals(addressThuDucText, mapAddressText1, "Địa chỉ trên bản đồ không khớp với địa chỉ Thủ Đức!");

        System.out.println("Test thành công: Địa chỉ cửa hàng Thủ Đức trùng khớp trên bản đồ!");
    }
}
