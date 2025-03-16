package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.SearchPage;
import utils.FileReader;

import java.util.Map;

import static utils.FileReader.readDataFromExcel;

public class SearchTest extends BaseTest {

    //    private WebDriver driver;
    private SearchPage searchPage;

    @BeforeClass
    private void setupClass() {
        searchPage = new SearchPage(driver);
    }

    @AfterMethod
    private void cleanupTest(){
        driver.get(baseURL);
    }

    @DataProvider(name = "searchData")
    public Object[][] loginDataProvider() {
        return readDataFromExcel("src/test/resources/search.xlsx", "SearchData");
    }

    @Test(dataProvider = "searchData",priority = 0)
    public void testLogin(Map<String, String> data) {
        String searchValue = data.get("searchValue");
        boolean expectedResult = Boolean.parseBoolean(data.get("expectedResult"));
        System.out.println("Test case: " + searchValue + " | searchValue: " + searchValue + ", expectedResult: " + expectedResult);
        // Thực hiện tìm kiếm
        searchPage.enterSearch(searchValue);
        searchPage.submitSearch();

        // Kiểm tra kết quả
        if (expectedResult) {
            // Kỳ vọng có sản phẩm
            Assert.assertTrue(searchPage.isFirstProductDisplayed(), "First product should be displayed for search: " + searchValue);
            Assert.assertFalse(searchPage.isNoProductMessageDisplayed(), "No product message should not be displayed for search: " + searchValue);
            Assert.assertTrue(searchPage.getFirstProductName().toLowerCase().contains(searchValue.toLowerCase()), "First product name should contain search value: " + searchValue);
        } else {
            // Kỳ vọng không có sản phẩm
            Assert.assertFalse(searchPage.isFirstProductDisplayed(), "First product should not be displayed for search: " + searchValue);
            Assert.assertTrue(searchPage.isNoProductMessageDisplayed(), "No product message should be displayed for search: " + searchValue);
        }
    }

    @DataProvider(name = "categoryData")
    private Object[][] categoryData() {
        return readDataFromExcel("src/test/resources/search.xlsx", "CategoryData");
    }

    //!Defect
    @Test(priority = 1, testName = "TC_Search_08", dataProvider = "categoryData")
    void testSearchWhenClickingCategoryButton(Map<String, String> data) {
        String searchFolding = data.get("valueItem");
        String expectedUrl = data.get("expectedUrl");
        try {
            searchPage.clickSearch();
            searchPage.getSearchFolding(searchFolding).click();
            sleep(5);
            Assert.assertEquals(driver.getCurrentUrl(), expectedUrl);
            sleep(5);
        } catch (Exception ex) {
            Assert.fail("Error");
        }

    }

}


