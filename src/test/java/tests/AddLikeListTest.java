//package tests;
//
//import base.BaseTest;
//import org.openqa.selenium.By;
//import org.openqa.selenium.Dimension;
//import org.openqa.selenium.WebDriver;
//import org.testng.Assert;
//import org.testng.annotations.AfterSuite;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.BeforeSuite;
//import org.testng.annotations.Test;
//
//
//public class AddLikeListTest extends BaseTest {
//    private WebDriver driver;
//    private AddLikeListPage likeListPage;
//    private String baseURL = "https://totoday.vn/";
//    private String loginURL = "https://totoday.vn/user/signin";
//    private final String EMAIL = "innologic25.team@gmail.com";
//    private final String PASSWORD = "innologic2025";
//    private boolean isLoggedIn = false;
//
//    @BeforeSuite
//    public void setupSuite() {
//        driver = getDriver();
//        likeListPage = new AddLikeListPage(driver,wait);
//    }
//
//    @BeforeMethod
//    public void setupMethod() {
//
//        if (!isLoggedIn) {
//            driver.get(loginURL);
//            likeListPage.login(EMAIL, PASSWORD);
//            isLoggedIn = true;
//        }
//    }
//
//
//    @Test(priority = 1)
//    public void testAddToLikeListWithLogin() {
//
//        driver.get(baseURL);
//        driver.findElement(By.xpath("/html[1]/body[1]/main[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]")).click();
//        driver.findElement(By.xpath("/html[1]/body[1]/main[1]/div[2]/div[1]/div[2]/div[1]/a[1]")).click();
//        driver.findElement(By.xpath("//div[@class='col']")).click();
//        driver.manage().window().maximize();
//
//        String productNameOnDetailPage = likeListPage.getProductNameOnDetailPage();
//        System.out.println("Tên sản phẩm trên trang chi tiết: " + productNameOnDetailPage);
//
//        likeListPage.clickLikeButton();
//        likeListPage.openLikeList();
//
//        String expectedProductName = "ÁO HOODIE NAM - TOTODAY - GOOD MANNERS MATTERS";
//        String actualProductName = likeListPage.getProductNameInLikeList();
//        System.out.println("Tên sản phẩm trong danh sách yêu thích: " + actualProductName);
//
//        Assert.assertEquals(actualProductName, expectedProductName, "Sản phẩm trong danh sách yêu thích không đúng!");
//        likeListPage.logout();
//        driver.manage().window().setSize(new Dimension(1050, 820));
//        sleep(1);
//    }
//    @Test(priority = 2)
//    public void testAddToLikeListWithoutLogin() {
//        driver.get(baseURL);
//        driver.findElement(By.xpath("/html[1]/body[1]/main[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]")).click();
//        driver.findElement(By.xpath("/html[1]/body[1]/main[1]/div[2]/div[1]/div[2]/div[1]/a[1]")).click();
//        driver.findElement(By.xpath("//div[@class='col']")).click();
//
//
//        likeListPage.clickLikeButton();
//        Assert.assertTrue(likeListPage.isRedirectedToLoginPage(), "Chưa đăng nhập, cần chuyển đến trang đăng nhập https://totoday.vn/user/signin");
//    }
//    @AfterSuite
//    public void tearDown() {
//        driver.quit();
//    }
//}