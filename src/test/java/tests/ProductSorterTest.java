package tests;

import base.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.SortPage;

import java.util.List;
import java.util.Map;

import static utils.FileReader.readDataFromExcel;

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
    void testSorterBySingleCriteria(Map<String, String> data)  {
        sleep(7);
        String optionItem = data.get("optionItem");
        String optionURL = data.get("optionURL");
        // Kiểm tra option có được highlight (active/selected) không
        Assert.assertTrue(sortPage.checkSelectSortOption(sortOptionsList,optionItem), "Sort option '" + optionItem + "' is NOT selected!");

        // Kiểm tra URL đã thay đổi chưa
        String actualUrl = sortPage.getCurrentURL();


        System.out.println("==========================================");

        System.out.println("Expected URL: " + sortProductURL + optionURL);
        System.out.println("Current URL: " + actualUrl);
        Assert.assertEquals(actualUrl,
                            sortProductURL + optionURL,
                            "Actual url :" + actualUrl + "\nExpect : " + sortProductURL + optionURL
        );
        System.out.println("==========================================");
    }

    // Dữ liệu test: Danh sách các option cần kiểm tra và đường dẫn thay đổi
    @DataProvider(name = "sortOptions")
    public Object[][] sortOptions() {
        return readDataFromExcel("src/test/resources/SortData.xlsx","SortData");
    }

    @AfterMethod
    private void cleanupMethod() {

    }

}
