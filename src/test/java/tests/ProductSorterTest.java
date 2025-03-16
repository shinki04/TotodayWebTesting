package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.Tools;

import java.util.List;

public class ProductSorterTest extends BaseTest {
    public WebElement sortClass;
    public WebElement sortList;
    public List<WebElement> sortOptionsList;
    private Tools tools;
    private JavascriptExecutor js;

    private String sortProductURL = baseURL + "/phu-kien-pc360511.html";

    @BeforeTest
    void setupTest() {


        driver.navigate().to(sortProductURL);

    }

    @BeforeClass
    void setupClass() {

    }

    @BeforeMethod()
    void setupMethod() {
        sortClass = tools.getElementByXpath("//div[contains(@class,'filter-sort')]//div[contains(@class,'sort')]");
        sortClass.click();

        sleep(8);
        try {
            sortList = sortClass.findElement(By.xpath("//ul[@class='filter-item-list']"));

            js.executeScript("arguments[0].style.display = 'block'", sortList);

            sortOptionsList = sortList.findElements(By.tagName("li"));
        } catch (NullPointerException e) {
            System.out.println("Can't not found " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test(dataProvider = "sortOptions")
    void testSorterBySingleCriteria(String optionItem, String optionURL) {

        // Click vào option sắp xếp
        selectSortOption(optionItem);

        sleep(7);
        // Kiểm tra option có được highlight (active/selected) không
        Assert.assertTrue(isOptionSelected(optionItem), "Sort option '" + optionItem + "' is NOT selected!");

        // Kiểm tra URL đã thay đổi chưa
        String actualUrl = getCurrentURL();
        Assert.assertEquals(actualUrl, sortProductURL + optionURL);

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

    // Click vào một option cụ thể theo text
    public void selectSortOption(String optionText) {

        for (WebElement option : sortOptionsList) {

//            js.executeScript("arguments[0].style.display = 'block'", sortList);
//            sortClass.click();
            sleep(7);
            System.out.println(option.getText());
            if (option.getText().equalsIgnoreCase(optionText)) {
                option.click();
                break;
            }
        }
    }

    // Kiểm tra xem option đã chọn có được tô màu xanh hay không
    public boolean isOptionSelected(String optionText) {
        for (WebElement option : sortOptionsList) {
            if (option.getText().equalsIgnoreCase(optionText)) {
                String className = option.getAttribute("class");
                return className.contains("selected") || className.contains("active");
            }
        }
        return false;
    }

    // Kiểm tra URL sau khi click
    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }

    @AfterTest
    void cleanTest() {
//        driver.quit();
    }
}
