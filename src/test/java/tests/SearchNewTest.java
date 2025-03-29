package tests;


import pages.SearchNewPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SearchNewTest {
    private WebDriver driver;
    private SearchNewPage searchPage;
    private String baseUrl = "https://pltpro.net/";

    @BeforeMethod
    public void setUp() {

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);
        searchPage = new SearchNewPage(driver);
    }

    // Test Case 1: Tìm kiếm thành công sản phẩm "Lenovo"
    @Test
    public void testSuccessfulSearchLenovo() {
        String keyword = "Lenovo";
        searchPage.enterSearchKeyword(keyword);
        searchPage.clickSearchButton();

        // Kiểm tra tiêu đề kết quả tìm kiếm chứa từ khóa "Lenovo"
        String resultText = searchPage.getSearchKeywordResultText();
        Assert.assertTrue(resultText.contains(keyword),
                "Kết quả tìm kiếm không chứa từ khóa '" + keyword + "'!");
    }

    // Test Case 2: Tìm kiếm với ký tự đặc biệt "@#!"
    @Test
    public void testSearchWithSpecialCharacters() {
        String specialKeyword = "@#!";
        searchPage.enterSearchKeyword(specialKeyword);
        searchPage.clickSearchButton();

        // Kiểm tra thông báo "Chưa có mặt hàng nào trong thương hiệu này!"
        String productsText = searchPage.getProductsCategoryText();
        Assert.assertTrue(productsText.contains("Chưa có mặt hàng nào trong thương hiệu này!"),
                "Không hiển thị thông báo 'Chưa có mặt hàng nào trong thương hiệu này!' khi tìm kiếm ký tự đặc biệt!");
    }

    // Test Case 3: Không nhập gì và nhấn tìm kiếm
    @Test
    public void testSearchWithEmptyInput() {
        searchPage.enterSearchKeyword(""); // Không nhập gì
        searchPage.clickSearchButton();

        // Kiểm tra thông báo lỗi "Lỗi 404 "
        String errorText = searchPage.getError404MessageText();
        Assert.assertTrue(errorText.contains("Lỗi 404"),
                "Không hiển thị thông báo 'Lỗi 404 Không tìm thấy' khi không nhập từ khóa!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

