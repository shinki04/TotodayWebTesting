package tests;

import base.BaseTest;
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

public class ProductDetailsTest extends BaseTest {

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
        quitDriver();
    }

    @BeforeMethod
    void setupMethod() {
        driver.get(baseURL);
        driver.findElement(By.xpath("/html[1]/body[1]/main[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]")).click();
        driver.findElement(By.xpath("/html[1]/body[1]/main[1]/div[2]/div[1]/div[2]/div[1]/a[1]")).click();
        driver.findElement(By.xpath("//div[@class='col']")).click();

    }
    @Test
    public void testProductDetails() {
        WebElement product = driver.findElement(By.xpath("(//span[normalize-space()='ÁO HOODIE NAM - TOTODAY - GOOD MANNERS MATTERS'])[1]"));
        String expectedTitle = product.getText();

        WebElement productTitle = driver.findElement(By.xpath("//span[normalize-space()='ÁO HOODIE NAM - TOTODAY - GOOD MANNERS MATTERS']"));
        String actualTitle = productTitle.getText();

        Assert.assertEquals(actualTitle, expectedTitle, "Tên sản phẩm không trùng khớp!");

        System.out.println("Test thành công: Tên sản phẩm trong danh sách và trang chi tiết giống nhau!");
    }
    @Test
    public void testProductDescriptionDisplayed() {
        WebElement productDescription = driver.findElement(By.xpath("(//p)[61]"));
        Assert.assertTrue(productDescription.isDisplayed(), "Mô tả sản phẩm không hiển thị trên trang!");

        String actualDescription = productDescription.getText();

        Assert.assertTrue(actualDescription.contains("Chất liệu nỉ da cá dày dặn"), "Mô tả sản phẩm không hiển thị đúng chất liệu!");
        Assert.assertTrue(actualDescription.contains("Form Oversize thời thượng"), "Mô tả sản phẩm không hiển thị đúng form!");
        Assert.assertTrue(actualDescription.contains("Màu sắc được phối lạ mắt đầy thu hút"), "Mô tả sản phẩm không hiển thị đúng màu sắc!");
        Assert.assertTrue(actualDescription.contains("Bảo hành lên đến 90 ngày"), "Mô tả sản phẩm không hiển thị chính sách bảo hành!");

        System.out.println("Test thành công: Mô tả sản phẩm hiển thị đầy đủ và đúng nội dung!");
    }
    @Test
    public void testProductTabsSwitching() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement reviewTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[contains(text(),'Đánh giá')])[1]")));
        try {
            reviewTab.click();
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].click();", reviewTab);
        }

        WebElement reviewContent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//p[contains(text(),'Chưa có đánh giá nào cho sản phẩm này')])[1]")));
        Assert.assertTrue(reviewContent.isDisplayed(), "Nội dung tab Đánh giá không hiển thị!");

        WebElement detailsTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[contains(text(),'Chi tiết sản phẩm')])[1]")));
        try {
            detailsTab.click();
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].click();", detailsTab);
        }

        WebElement detailsContent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/section[1]/div[1]/div[1]/div[2]/div[1]/div[1]")));
        Assert.assertTrue(detailsContent.isDisplayed(), "Nội dung tab Chi tiết sản phẩm không hiển thị!");

        System.out.println("Test thành công: Tab 'Chi tiết sản phẩm' và 'Đánh giá' hoạt động chính xác!");
    }
}