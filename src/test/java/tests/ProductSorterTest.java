package tests;

import base.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.SortPage;

import java.util.List;

public class ProductSorterTest extends BaseTest {
    private String sortProductURL = baseURL + "/phu-kien-pc360511.html";
    private SortPage sortPage;
    public List<WebElement> sortOptionsList;

    @BeforeClass
    void setupClass() {
        sortPage = new SortPage(driver, wait);
    }

    @BeforeTest
    void setupTest() {
        driver.manage().window().maximize();
        driver.navigate().to(sortProductURL);
        driver.navigate().refresh();




    }

    @BeforeMethod()
    void setupMethod() {
//        sortPage.clickSortClass();
        sortPage.clickSortList();
        sortOptionsList = sortPage.getSortList();


    }

    @Test(dataProvider = "sortOptions")
    void testSorterBySingleCriteria(String optionItem, String optionURL)  {
        // Click vào option sắp xếp

        sleep(7);
        // Kiểm tra option có được highlight (active/selected) không
        Assert.assertTrue(sortPage.checkSelectSortOption(sortOptionsList,optionItem), "Sort option '" + optionItem + "' is NOT selected!");

        // Kiểm tra URL đã thay đổi chưa
        String actualUrl = sortPage.getCurrentURL();
        Assert.assertEquals(actualUrl,
                            sortProductURL + optionURL,
                            "Actual url :" + actualUrl + "\nExpect : " + sortProductURL + optionURL
        );


    }

    // Dữ liệu test: Danh sách các option cần kiểm tra và đường dẫn thay đổi
    @DataProvider(name = "sortOptions")
    public Object[][] sortOptions() {
        return new Object[][]{
                {"Bán chạy nhất", "?show=hot"},
                {"Mới nhất", "?show=new"},
                {"Giá: Thấp - Cao", "?show=priceAsc"},
                {"Giá: Cao - Thấp", "?show=priceDesc"}
        };
    }

    @AfterMethod
    private void cleanupMethod() {

    }

}
