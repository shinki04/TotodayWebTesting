package tests;

import base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.FilterPage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static utils.FileReader.readDataFromExcel;

public class ProductFilterTest extends BaseTest {

    private final String sortProductURL = baseURL + "/phu-kien-pc360511.html";
    public List<WebElement> filterColorOption;
    public List<WebElement> optionColorItems;
    private FilterPage filterPage;

    @BeforeClass
    void setupClass() {
        if (wait == null) {
            System.out.println("Warning: wait is null in setupClass");
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }
        filterPage = new FilterPage(driver, wait);
    }

    @BeforeTest
    void setupTest() {

        driver.manage().window().maximize();
        driver.navigate().to(sortProductURL);
        driver.navigate().refresh();
    }

    @BeforeMethod
    void setupMethod() {
//        sleep(8);
        try {
            filterPage.clickFilterClass();
            filterPage.clickFilterColorBlock();
            filterPage.clickFilterColorClass();
        } catch (Exception e) { // Broader exception to catch more issues
            System.out.println("Không tìm thấy bộ lọc màu hoặc không thể tương tác: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @DataProvider(name = "singleColorFilter")
    public Object[][] createSingleFilterData() {
        return readDataFromExcel("src/test/resources/FilterData.xlsx", "ColorData");

    }

    @Test(priority = 0, dataProvider = "singleColorFilter")
    public void testSelectSingleColorFilter(Map<String, String> data) {
        optionColorItems = filterPage.getColorOption();
        String expectedFilter = data.get("expectedFilter");
        for (WebElement optionColorItem : optionColorItems) {
            String filterValue = filterPage.getDataValue(optionColorItem);
            if (filterValue.equals(expectedFilter)) {
                filterPage.clickEachColor(optionColorItem);

//                sleep(2); // Consider replacing with explicit wait for page update
                break;
            }
        }

        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = baseURL + "/phu-kien-pc360511.html?i4=" + expectedFilter;
        System.out.println("==========================================");

        System.out.println("Expected URL: " + expectedUrl);
        System.out.println("Current URL: " + currentUrl);

        Assert.assertEquals(currentUrl, expectedUrl, "Bộ lọc màu không được áp dụng chính xác");
        System.out.println("==========================================");


    }

    @Test(priority = 1)
    public void testSelectMultipleColorFilters() {
        optionColorItems = filterPage.getColorOption();
        List<String> selectedFilters = new ArrayList<>();

        for (WebElement optionColorItem : optionColorItems) {
            String filterValue = filterPage.getDataValue(optionColorItem);

            if (!filterValue.isEmpty() && !selectedFilters.contains(filterValue)) {
                filterPage.clickEachColor(optionColorItem);
                selectedFilters.add(filterValue);
                sleep(1);
            }
        }

//        selectedFilters = selectedFilters.stream().distinct().collect(Collectors.toList());

        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = baseURL + "/phu-kien-pc360511.html?i4=" + String.join(",", selectedFilters);
        System.out.println("==========================================");

        System.out.println("Expected URL: " + expectedUrl);
        System.out.println("Current URL: " + currentUrl);

        Assert.assertEquals(currentUrl, expectedUrl, "Bộ lọc màu cộng dồn không chính xác");
        System.out.println("==========================================");

    }

    @DataProvider(name = "priceFilterData")
    public Object[][] createPriceFilterData() {
        return readDataFromExcel("src/test/resources/FilterData.xlsx", "PriceData");
    }

    @Test(priority = 2, dataProvider = "priceFilterData")
    public void testPriceFilter(Map<String, String> data) {
        filterPage.clickPriceClass();
        int expectedMinPrice = Integer.parseInt(data.get("expectedMinPrice"));
        int expectedMaxPrice = Integer.parseInt(data.get("expectedMaxPrice"));
        filterPage.setSlider(expectedMinPrice, expectedMaxPrice);
        filterPage.clickFilterButton();
        sleep(10);
        int actualMinPrice = filterPage.getMinPrice();
        int actualMaxPrice = filterPage.getMaxPrice();
        // In kết quả để kiểm tra
        System.out.println("==========================================");
        System.out.println("Min Price - Thực tế: " + actualMinPrice + ", Mong đợi: " + expectedMinPrice);
        System.out.println("Max Price - Thực tế: " + actualMaxPrice + ", Mong đợi: " + expectedMaxPrice);

        // Kiểm tra với khoảng linh hoạt (±10%)
        int maxSliderValue = 5000000;
        int tolerance = (int) (maxSliderValue * 0.1); // 10% của 5,000,000đ = 500,000đ

        Assert.assertTrue(actualMinPrice >= expectedMinPrice - tolerance && actualMinPrice <= expectedMinPrice + tolerance,
                          "Min price không đúng! Thực tế: " + actualMinPrice + ", Mong đợi: " + expectedMinPrice);
        Assert.assertTrue(actualMaxPrice >= expectedMaxPrice - tolerance && actualMaxPrice <= expectedMaxPrice + tolerance,
                          "Max price không đúng! Thực tế: " + actualMaxPrice + ", Mong đợi: " + expectedMaxPrice);

        System.out.println("==========================================");
    }


    @AfterMethod
    void cleanMethod() {
        driver.navigate().to(sortProductURL);
    }
}
