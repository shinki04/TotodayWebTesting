package tests;

import config.DriverConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utils.Tools;

import java.time.Duration;

public class ProductDetails extends DriverConfig {

    private static Tools tools;
    private WebDriver driver;
    private WebElement searchInput;
    private WebElement messageNoResult;

    @BeforeSuite
    void setupSuite() {
        WebDriverManager.chromedriver().setup();
        driver = getDriver();
        driver.get(baseURL);
        tools = new Tools(driver);
    }

    @AfterSuite
    void cleanupTest() {
        quitDriver();
    }

    @BeforeMethod
    void setupMethod() {
        driver.findElement(By.xpath("/html[1]/body[1]/main[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/a[1]")).click();
        driver.findElement(By.xpath("(//a[@class='cc-item'][normalize-space()='Áo Khoác Nam'])[1]")).click();
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
        WebElement productDescription = driver.findElement(By.xpath("/html[1]/body[1]/section[1]/div[1]/div[1]/div[2]/div[1]/div[1]/p[1]"));
        Assert.assertTrue(productDescription.isDisplayed(), "Mô tả sản phẩm không hiển thị trên trang!");

        String actualDescription = productDescription.getText();

        Assert.assertTrue(actualDescription.contains("Chất liệu nỉ da cá dày dặn"), "Mô tả sản phẩm không hiển thị đúng chất liệu!");
        Assert.assertTrue(actualDescription.contains("Form Oversize thời thượng"), "Mô tả sản phẩm không hiển thị đúng form!");
        Assert.assertTrue(actualDescription.contains("Màu sắc được phối lạ mắt đầy thu hút"), "Mô tả sản phẩm không hiển thị đúng màu sắc!");
        Assert.assertTrue(actualDescription.contains("Bảo hành lên đến 90 ngày"), "Mô tả sản phẩm không hiển thị chính sách bảo hành!");

        System.out.println("Test thành công: Mô tả sản phẩm hiển thị đầy đủ và đúng nội dung!");
    }
    @Test(priority = 3)
    public void testProductTabsSwitching() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // **Bước 1: Chuyển sang tab "Đánh giá"**
        WebElement reviewTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[contains(text(),'Đánh giá')])[1]")));
        try {
            reviewTab.click();
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].click();", reviewTab);
        }

        // **Chờ nội dung tab "Đánh giá" hiển thị**
        WebElement reviewContent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//p[contains(text(),'Chưa có đánh giá nào cho sản phẩm này')])[1]")));
        Assert.assertTrue(reviewContent.isDisplayed(), "Nội dung tab Đánh giá không hiển thị!");

        // **Bước 2: Chuyển lại tab "Chi tiết sản phẩm"**
        WebElement detailsTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[contains(text(),'Chi tiết sản phẩm')])[1]")));
        try {
            detailsTab.click();
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].click();", detailsTab);
        }

        // **Chờ nội dung tab "Chi tiết sản phẩm" hiển thị**
        WebElement detailsContent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/section[1]/div[1]/div[1]/div[2]/div[1]/div[1]")));
        Assert.assertTrue(detailsContent.isDisplayed(), "Nội dung tab Chi tiết sản phẩm không hiển thị!");

        System.out.println("✅ Test thành công: Tab 'Chi tiết sản phẩm' và 'Đánh giá' hoạt động chính xác!");
    }}