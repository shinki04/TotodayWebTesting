package tests;

import config.DriverConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.*;

import utils.Tools;


public class SearchTest extends DriverConfig {
    private static Tools tools;
    private WebElement searchInput;
    private WebDriver driver;
    private WebElement messageNoProduct;
    private WebElement sectionProduct;
    private JavascriptExecutor js;


    @BeforeSuite
    void setupSuite() {
        WebDriverManager.chromedriver().setup();
        driver = getDriver();
        driver.get(baseURL);
        tools = new Tools(driver);
        js = (JavascriptExecutor) driver;
    }


    @AfterSuite
    void cleanupTest() {
        quitDriver();
    }


    @BeforeMethod
    void setupTMethod() {
        searchInput = driver.findElement(By.xpath("//input[@class='search-input']"));
    }


    @Test(priority = 0, testName = "")
    void testSearchSuccess() {
        String searchItem = "Quần Jean";
        searchInput.sendKeys(searchItem);
        searchInput.submit();
        if (driver.getCurrentUrl().contains(baseURL + "search?q=")) {
            System.out.println("Search input have change");
        }
        sleep(5);

        checkSearchSuccess();
        sleep(5);

    }


    @Test(priority = 1)
    void testSearchFailed() {
        String searchItem = "quần đùi";
        searchInput.sendKeys(searchItem);
        searchInput.submit();
        checkSearchFailed();
        sleep(5);
    }

    @Test(priority = 2)
    void testSearchWithItestSearchWithSpecialCharactersncorrectKeyword() {
        String searchItem = "@gmail@#$";
        searchInput.sendKeys(searchItem);
        searchInput.submit();

        checkSearchFailed();
        sleep(5);

    }

    @Test(priority = 3)
    void testSearchWithCaseInsensitiveKeyword() {
        String searchItem = "Áo kHoÁc";
        searchInput.sendKeys(searchItem);
        searchInput.submit();

        checkSearchSuccess();

        sleep(5);
    }

    //    ! Defect
    @Test(priority = 4)
    void testSearchWithLeadingTrailingSpaces() {
        String searchItem = "   Quần Jean  ";
        searchInput.sendKeys(searchItem);
        searchInput.submit();
        checkSearchSuccess();
        sleep(5);
    }

    @Test(priority = 5)
    void testSearchWithEmptyKeyword() {
        String searchItem = "";
        searchInput.sendKeys(searchItem);
        searchInput.submit();
        checkSearchSuccess();
        sleep(5);
    }

    @Test(priority = 6)
    void testSearchWithMaxLengthKeyword() {
        String searchItem = tools.generateRandomString(256);
        searchInput.sendKeys(searchItem);
        searchInput.submit();
        checkSearchFailed();
        sleep(5);
    }

    //    ! Defect
    @Test(priority = 7, dataProvider = "categoryData")
    void testSearchWhenClickingCategoryButton(String xpath, String expectedUrl) {
        searchInput.click();
        sleep(5);
        WebElement searchFolding = tools.getElementByXpath("//div[@class='searchFolding']");
        if (tools.checkElementIsDisplayed(searchFolding)) {
            js.executeScript("arguments[0].style.display = 'block'", searchFolding);
            System.out.println("Was change style");
        }
        tools.getElementByXpath(xpath).click();
        sleep(5);

        if (tools.checkElementIsDisplayed(searchFolding)) {
            js.executeScript("arguments[0].style.display = 'block'", searchFolding);
            System.out.println("Was change style");
        }

        Assert.assertTrue(baseURL.contentEquals(expectedUrl));
        checkSearchSuccess();
        sleep(5);
    }

    @DataProvider(name = "categoryData")
    private Object[][] categoryData() {
        return new Object[][]{
                //        Áo khoác
                {"//div[@class='searchFolding']//a[contains(text(),'ÁO KHOÁC')]", "https://totoday.vn/ao-khoac-pc72908.html"},
                //        Đồ nam
                {"//div[@class='searchFolding']//a[contains(text(),'ĐỒ NAM')]", "https://totoday.vn/do-nam-pc72882.html"},
                //        Đồ nữ
                {"//div[@class='searchFolding']//a[contains(text(),'ĐỒ NỮ')]", "https://totoday.vn/do-nu-pc72896.html"},
                //        Unisex
                {"//div[@class='searchFolding']//a[contains(text(),'UNISEX')]", "https://totoday.vn/unisex-pc72920.html"},
                //        Phụ kiện
                {"//div[@class='searchFolding']//a[contains(text(),'PHỤ KIỆN')]", "https://totoday.vn/phu-kien-pc360511.html"}
        };
    }

    void checkSearchFailed() {
        messageNoProduct = tools.getElementByXpath("//div[@class='no-product']");
        Assert.assertTrue(tools.checkElementIsDisplayed(messageNoProduct), "Category was found");
        sectionProduct = tools.getElementByXpath("//div[@class='section-product-wrap']");
        Assert.assertFalse(tools.checkElementIsDisplayed(sectionProduct), "Category not found");
    }

    void checkSearchSuccess() {
        messageNoProduct = tools.getElementByXpath("//div[@class='no-product']");
        Assert.assertFalse(tools.checkElementIsDisplayed(messageNoProduct), "No product displayed");
        sectionProduct = tools.getElementByXpath("//div[@class='section-product-wrap']");
        Assert.assertTrue(tools.checkElementIsDisplayed(sectionProduct), "No product displayed");
    }

}
