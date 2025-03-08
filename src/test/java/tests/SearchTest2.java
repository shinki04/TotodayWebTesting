package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.Tools;


public class SearchTest2 extends BaseTest {

    private static Tools tools;
    private WebElement searchInput;
    private WebElement messageNoProduct;
    private WebElement sectionProduct;
    private JavascriptExecutor js;


    @BeforeTest
    void setupClass() {
//        driver = getDriver();
//        tools = new Tools(driver);
//        js = (JavascriptExecutor) driver;
//        notification = new Notification(driver);
//        popupHandler = new PopupHandler(driver);
//        actions = new Actions(driver);

    }


    @BeforeMethod
    void setupMethod() {
        searchInput = driver.findElement(By.xpath("//input[@class='search-input']"));
    }


    @Test(priority = 0, testName = "TC_Search_01")
    void testSearchSuccess() {
        String searchItem = "Quần Jean";
        searchInput.sendKeys(searchItem);
        searchInput.submit();
        if (driver.getCurrentUrl().contains(baseURL + "search?q=")) {
            System.out.println("Search input have change");
        }
        sleep(5);
        checkSearchSuccess();
        Assert.assertTrue(checkContentEqualWithParentElementByXpath(sectionProduct, "/html[1]/body[1]/main[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/a[1]", searchItem), "Product Item not found or not display");
        sleep(5);

    }


    @Test(priority = 1, testName = "TC_Search_02")
    void testSearchFailed() {
        String searchItem = "quần đùi";
        searchInput.sendKeys(searchItem);
        searchInput.submit();
        checkSearchFailed();
        sleep(5);
    }

    @Test(priority = 2, testName = "TC_Search_03")
    void testSearchWithItestSearchWithSpecialCharactersncorrectKeyword() {
        String searchItem = "@gmail@#$";
        searchInput.sendKeys(searchItem);
        searchInput.submit();
        checkSearchFailed();
        sleep(5);

    }

    @Test(priority = 3, testName = "TC_Search_04")
    void testSearchWithCaseInsensitiveKeyword() {
        String searchItem = "Áo kHoÁc";
        searchInput.sendKeys(searchItem);
        searchInput.submit();

        checkSearchSuccess();
        Assert.assertTrue(checkContentEqualWithParentElementByXpath(sectionProduct, "/html[1]/body[1]/main[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/a[1]", searchItem), "Product Item not found or not display");

        sleep(5);
    }

    //    ! Defect
    @Test(priority = 4, testName = "TC_Search_05")
    void testSearchWithLeadingTrailingSpaces() {
        String searchItem = "   Quần Jean  ";
        searchInput.sendKeys(searchItem);
        searchInput.submit();
        checkSearchSuccess();
        Assert.assertTrue(checkContentEqualWithParentElementByXpath(sectionProduct, "/html[1]/body[1]/main[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/a[1]", searchItem), "Product Item not found or not display");
        sleep(5);
    }

    @Test(priority = 5, testName = "TC_Search_06")
    void testSearchWithEmptyKeyword() {
        String searchItem = "";
        searchInput.sendKeys(searchItem);
        searchInput.submit();
        checkSearchSuccess();
        Assert.assertTrue(checkContentEqualWithParentElementByXpath(
                                  sectionProduct,
                                  "/html[1]/body[1]/main[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/a[1]",
                                  searchItem),
                          "Product Item not found or not display");
        sleep(5);
    }

    @Test(priority = 6, testName = "TC_Search_07")
    void testSearchWithMaxLengthKeyword() {
        String searchItem = tools.generateRandomString(256);
        System.out.println(searchItem);
        searchInput.sendKeys(searchItem);
        searchInput.submit();
        checkSearchFailed();
        sleep(5);
    }

    //    ! Defect
    @Test(priority = 7, testName = "TC_Search_08", dataProvider = "categoryData")
    void testSearchWhenClickingCategoryButton(String xpath, String expectedUrl, String valueItem) {
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
        Assert.assertTrue(checkContentEqualWithParentElementByXpath(sectionProduct, "/html[1]/body[1]/main[1]/div[2]/div[1]/div[3]/div[1]/div[1]/div[1]/div[2]/a[1]", valueItem), "Product Item not found or not display");

        checkSearchSuccess();
        sleep(5);
    }

    @DataProvider(name = "categoryData")
    private Object[][] categoryData() {
        return new Object[][]{
                //        Áo khoác
                {"//div[@class='searchFolding']//a[contains(text(),'ÁO KHOÁC')]", "https://totoday.vn/ao-khoac-pc72908.html", "Áo khoác"},
                //        Đồ nam
                {"//div[@class='searchFolding']//a[contains(text(),'ĐỒ NAM')]", "https://totoday.vn/do-nam-pc72882.html", "Đồ Nam"},
                //        Đồ nữ
                {"//div[@class='searchFolding']//a[contains(text(),'ĐỒ NỮ')]", "https://totoday.vn/do-nu-pc72896.html", "Đồ nữ"},
                //        Unisex
                {"//div[@class='searchFolding']//a[contains(text(),'UNISEX')]", "https://totoday.vn/unisex-pc72920.html", "Unisex"},
                //        Phụ kiện
                {"//div[@class='searchFolding']//a[contains(text(),'PHỤ KIỆN')]", "https://totoday.vn/phu-kien-pc360511.html", "Phụ kiện"}
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

    boolean checkContentEqualWithParentElementByXpath(WebElement parentElement, String xpathChild, String value) {
        WebElement childElement = tools.getElementChildByXpath(parentElement, xpathChild);
        return tools.checkElementIsDisplayed(childElement) && tools.getText(childElement).trim().toLowerCase().contains(value.toLowerCase());
    }

}
