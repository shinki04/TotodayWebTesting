package tests;

import config.DriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pages.AddLikeListPage;


public class AddLikeListTest extends DriverConfig {
    private WebDriver driver;
    private AddLikeListPage likeListPage;
    private String baseURL = "https://totoday.vn/"; // Trang chủ
    private String loginURL = "https://totoday.vn/user/signin"; // Trang đăng nhập
    private final String EMAIL = "innologic25.team@gmail.com"; // Thông tin đăng nhập
    private final String PASSWORD = "innologic2025"; // Thông tin đăng nhập
    private boolean isLoggedIn = false; // Biến trạng thái để theo dõi đăng nhập

    @BeforeSuite
    public void setupSuite() {
        driver = getDriver();
        likeListPage = new AddLikeListPage(driver);
    }

    @BeforeMethod
    public void setupMethod() {
        // Chỉ điều hướng đến trang đăng nhập và đăng nhập nếu chưa đăng nhập
        if (!isLoggedIn) {
            driver.get(loginURL);
            likeListPage.login(EMAIL, PASSWORD);
            isLoggedIn = true;
        }
    }


    @Test(priority = 1)
    public void testAddToLikeListWithLogin() {
        // Điều hướng đến trang sản phẩm (cần đảm bảo đã đăng nhập)
        driver.get(baseURL);
        driver.findElement(By.xpath("/html[1]/body[1]/main[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]")).click();
        driver.findElement(By.xpath("/html[1]/body[1]/main[1]/div[2]/div[1]/div[2]/div[1]/a[1]")).click();
        driver.findElement(By.xpath("//div[@class='col']")).click();
        driver.manage().window().maximize();


        // Lấy tên sản phẩm từ trang chi tiết sản phẩm
        String productNameOnDetailPage = likeListPage.getProductNameOnDetailPage();
        System.out.println("Tên sản phẩm trên trang chi tiết: " + productNameOnDetailPage);

        // Thêm sản phẩm vào danh sách yêu thích
        likeListPage.clickLikeButton();

        // Mở danh sách yêu thích
        likeListPage.openLikeList();
        // Kiểm tra tên sản phẩm mong muốn có trong danh sách yêu thích
        String expectedProductName = "ÁO HOODIE NAM - TOTODAY - GOOD MANNERS MATTERS";
        String actualProductName = likeListPage.getProductNameInLikeList();
        System.out.println("Tên sản phẩm trong danh sách yêu thích: " + actualProductName);

        Assert.assertEquals(actualProductName, expectedProductName, "Sản phẩm trong danh sách yêu thích không đúng!");
        likeListPage.logout();
        driver.manage().window().setSize(new Dimension(1050, 820)); // Đặt về kích thước HD tiêu chuẩn
        sleep(1);
    }
    @Test(priority = 2)
    public void testAddToLikeListWithoutLogin() {
        driver.get(baseURL);
        driver.findElement(By.xpath("/html[1]/body[1]/main[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]")).click();
        driver.findElement(By.xpath("/html[1]/body[1]/main[1]/div[2]/div[1]/div[2]/div[1]/a[1]")).click();
        driver.findElement(By.xpath("//div[@class='col']")).click();

        // @BeforeMethod đã chạy trước, nên trang sản phẩm đã được mở
        likeListPage.clickLikeButton(); // Nhấn nút Like thay vì nút User
        Assert.assertTrue(likeListPage.isRedirectedToLoginPage(), "Chưa đăng nhập, cần chuyển đến trang đăng nhập https://totoday.vn/user/signin");
    }
    @AfterSuite
    public void tearDown() {
        driver.quit();
    }
}