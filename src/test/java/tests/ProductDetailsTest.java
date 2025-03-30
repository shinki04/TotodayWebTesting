//package tests;
//
//import config.DriverConfig;
//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.openqa.selenium.WebDriver;
//import org.testng.Assert;
//import org.testng.annotations.*;
//import pages.ProductDetailsPage;
//import utils.Tools;
//
//public class ProductDetailsTest extends DriverConfig {
//    private static Tools tools;
//    private WebDriver driver;
//    private ProductDetailsPage productDetailsPage;
//
//    @BeforeClass
//    void setupSuite() {
//        WebDriverManager.chromedriver().setup();
//        driver = getDriver();
//        driver.get(baseURL);
//        tools = new Tools(driver);
//        productDetailsPage = new ProductDetailsPage(driver);
//    }
//
//    @BeforeMethod
//    void setupMethod() {
//        driver.get(baseURL);
//        productDetailsPage.navigateToProduct();
//    }
//
//    @Test
//    public void testProductDetails() {
//        String expectedTitle = productDetailsPage.getProductTitleInList();
//        String actualTitle = productDetailsPage.getProductTitleOnPage();
//        Assert.assertEquals(actualTitle, expectedTitle, "Tên sản phẩm không trùng khớp!");
//        System.out.println("Test thành công: Tên sản phẩm trong danh sách và trang chi tiết giống nhau!");
//    }
//
//    @Test
//    public void testProductDescriptionDisplayed() {
//        Assert.assertTrue(productDetailsPage.isDescriptionDisplayed(),
//            "Mô tả sản phẩm không hiển thị trên trang!");
//
//        String actualDescription = productDetailsPage.getDescription();
//        Assert.assertTrue(actualDescription.contains("Chất liệu nỉ da cá dày dặn"),
//            "Mô tả sản phẩm không hiển thị đúng chất liệu!");
//        Assert.assertTrue(actualDescription.contains("Form Oversize thời thượng"),
//            "Mô tả sản phẩm không hiển thị đúng form!");
//        Assert.assertTrue(actualDescription.contains("Màu sắc được phối lạ mắt đầy thu hút"),
//            "Mô tả sản phẩm không hiển thị đúng màu sắc!");
//        Assert.assertTrue(actualDescription.contains("Bảo hành lên đến 90 ngày"),
//            "Mô tả sản phẩm không hiển thị chính sách bảo hành!");
//
//        System.out.println("Test thành công: Mô tả sản phẩm hiển thị đầy đủ và đúng nội dung!");
//    }
//
//    @Test
//    public void testProductTabsSwitching() {
//        productDetailsPage.switchToReviewTab();
//        Assert.assertTrue(productDetailsPage.isReviewContentDisplayed(),
//            "Nội dung tab Đánh giá không hiển thị!");
//
//        productDetailsPage.switchToDetailsTab();
//        Assert.assertTrue(productDetailsPage.isDetailsContentDisplayed(),
//            "Nội dung tab Chi tiết sản phẩm không hiển thị!");
//
//        System.out.println("Test thành công: Tab 'Chi tiết sản phẩm' và 'Đánh giá' hoạt động chính xác!");
//    }
//
//    @AfterClass
//    void cleanupTest() {
//        quitDriver();
//    }
//}