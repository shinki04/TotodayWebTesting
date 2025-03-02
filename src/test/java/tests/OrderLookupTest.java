package tests;

import config.DriverConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Tools;

import java.time.Duration;

public class OrderLookupTest extends DriverConfig {

    private static Tools tools;
    private WebDriver driver;
    private WebElement searchInput;
    private WebElement messageNoResult;

    @BeforeClass
    void setupSuite() {
        WebDriverManager.chromedriver().setup();
        driver = getDriver();
        driver.get(baseURL);
        tools = new Tools(driver);
    }

    @AfterClass
    void cleanupTest() {
        quitDriver();
    }
    @BeforeMethod
    void setupMethod() {
        driver.get(baseURL);
        driver.findElement(By.linkText("Tra cứu đơn hàng")).click();
    }
    public void searchOrder(String searchValue, String expectedMessage) {
        WebElement searchInput = driver.findElement(By.xpath("//input[@placeholder='Nhập số điện thoại hoặc mã đơn hàng']"));
        searchInput.clear();
        searchInput.sendKeys(searchValue);

        WebElement searchButton = driver.findElement(By.xpath("//button[contains(text(),'Tra cứu')]"));
        searchButton.click();

        WebElement resultMessage = driver.findElement(By.xpath("//div[@class='order-not-found']"));
        Assert.assertEquals(resultMessage.getText(), expectedMessage, "Thông báo không khớp!");
    }

    @Test
    public void TC_OrderLK_01_SearchByPhoneNotFound() {
        searchOrder("0708712413", "Không tìm thấy đơn hàng theo yêu cầu");
        System.out.println("Test TC_OrderLK_01 passed!");
    }

    @Test
    public void TC_OrderLK_02_SearchBySpecialCharacters() {
        searchOrder("@b$$^", "Không tìm thấy đơn hàng theo yêu cầu");
        System.out.println("Test TC_OrderLK_02 passed!");
    }

    @Test
    public void TC_OrderLK_03_SearchWithWhitespace() {
        searchOrder("     0708712413     ", "Không tìm thấy đơn hàng theo yêu cầu");
        System.out.println("Test TC_OrderLK_03 passed!");
    }

}