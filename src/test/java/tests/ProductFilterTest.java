package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.Tools;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductFilterTest extends BaseTest {
    private static Tools tools;
    public WebElement filterColorClass;
    public List<WebElement> filterColorOption;
    public List<WebElement> optionColorItems;
    private final String sortProductURL = baseURL + "/phu-kien-pc360511.html";
    private WebDriverWait wait;
    private Actions actions;
    private JavascriptExecutor js;



    @BeforeTest
    void setupClass() {
        driver = getDriver();

        driver.navigate().to(sortProductURL);
        tools = new Tools(driver);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Initialize wait with 10-second timeout
        sleep(4);
        actions = new Actions(driver);
        driver.navigate().refresh();
        js = (JavascriptExecutor) driver;

    }

    @BeforeMethod
    void setupMethod() {
        sleep(8);
        try {
            WebElement filterClass = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("/html[1]/body[1]/main[1]/div[2]/div[1]/div[2]/div[2]/div[2]/p[1]")));
            filterClass.click();

            filterColorClass = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[@class='filter-item-wrap filter-color']//ul")));
            js.executeScript("arguments[0].style.display = 'block';", filterColorClass);
            filterColorOption = filterColorClass.findElements(By.tagName("a"));
        } catch (Exception e) { // Broader exception to catch more issues
            System.out.println("Không tìm thấy bộ lọc màu hoặc không thể tương tác: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @AfterMethod
    void cleanMethod() {
        driver.navigate().to(sortProductURL);
    }

    @DataProvider(name = "singleColorFilter")
    public Object[][] createSingleFilterData() {
        return new Object[][]{
                {"1393450"}, {"1347096"}, {"1261970"}, {"1261969"},
                {"822715"}, {"817482"}, {"588112"}, {"587994"},
                {"587993"}, {"276852"}
        };
    }

    @Test(dataProvider = "singleColorFilter")
    public void testSelectSingleColorFilter(String expectedFilter) {
        optionColorItems = filterColorClass.findElements(By.tagName("li"));

        for (WebElement optionColorItem : optionColorItems) {
            String filterValue = optionColorItem.getAttribute("data-value");
            if (filterValue.equals(expectedFilter)) {
                WebElement filterLink = wait.until(ExpectedConditions.elementToBeClickable(
                        optionColorItem.findElement(By.tagName("a"))));
                js.executeScript("arguments[0].click();", filterLink);
                sleep(2); // Consider replacing with explicit wait for page update
                break;
            }
        }

        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = baseURL + "/phu-kien-pc360511.html?i4=" + expectedFilter;

        System.out.println("Expected URL: " + expectedUrl);
        System.out.println("Current URL: " + currentUrl);

        Assert.assertEquals(currentUrl, expectedUrl, "Bộ lọc màu không được áp dụng chính xác");
    }

    @Test
    public void testSelectMultipleColorFilters() {
        optionColorItems = filterColorClass.findElements(By.tagName("li"));
        List<String> selectedFilters = new ArrayList<>();

        for (int i = 0; i < Math.min(5, optionColorItems.size()); i++) {
            WebElement optionColorItem = optionColorItems.get(i);
            String filterValue = optionColorItem.getAttribute("data-value");

            if (!filterValue.isEmpty() && !selectedFilters.contains(filterValue)) {
                WebElement filterLink = wait.until(ExpectedConditions.elementToBeClickable(
                        optionColorItem.findElement(By.tagName("a"))));
                js.executeScript("arguments[0].click();", filterLink);
                selectedFilters.add(filterValue);
                sleep(1);
            }
        }

        selectedFilters = selectedFilters.stream().distinct().collect(Collectors.toList());

        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = baseURL + "/phu-kien-pc360511.html?i4=" + String.join(",", selectedFilters);

        System.out.println("Expected URL: " + expectedUrl);
        System.out.println("Current URL: " + currentUrl);

        Assert.assertEquals(currentUrl, expectedUrl, "Bộ lọc màu cộng dồn không chính xác");
    }


    @Test(priority = 2,dataProvider = "priceFilterData")
    public void testPriceFilter(int expectedMinPrice, int expectedMaxPrice) {

        // Lấy phần tử slider
        WebElement filterPriceClass = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@class='filter-item-wrap filter-price']//ul")));
        js.executeScript("arguments[0].style.display = 'block';", filterPriceClass);


        // Lấy thanh trượt và các tay cầm
        WebElement slider = driver.findElement(By.id("slider-range"));
        WebElement leftHandle = slider.findElement(By.cssSelector(".ui-slider-handle:nth-of-type(1)"));
        WebElement rightHandle = slider.findElement(By.cssSelector(".ui-slider-handle:nth-of-type(2)"));

//        // Tính toán chiều rộng thanh trượt để kéo thả chính xác
//        int sliderWidth = slider.getSize().getWidth();
//        int offsetLeft = (int) (sliderWidth * 0.2); // 20% chiều rộng -> khoảng 1,000,000đ
//        int offsetRight = (int) (sliderWidth * 0.8); // 80% chiều rộng -> khoảng 4,000,000đ

        // Tính toán chiều rộng thanh trượt
        int sliderWidth = slider.getSize().getWidth();
        int maxSliderValue = 5000000; // Giá trị tối đa từ HTML (5,000,000đ)

        // Tính offset dựa trên tỷ lệ giá trị mong muốn
        int offsetLeft = (int) ((expectedMinPrice / (float) maxSliderValue) * sliderWidth);
        int offsetRight = (int) ((expectedMaxPrice / (float) maxSliderValue) * sliderWidth);

        // Đảm bảo tay cầm có thể kéo thả
        wait.until(ExpectedConditions.elementToBeClickable(leftHandle));
        wait.until(ExpectedConditions.elementToBeClickable(rightHandle));

        // Kéo tay cầm trái đến 20%
        actions.clickAndHold(leftHandle)
                .moveByOffset(offsetLeft, 0) // Kéo sang phải 20% chiều rộng
                .release()
                .perform();

        // Kéo tay cầm phải về 80%
        actions.clickAndHold(rightHandle)
                .moveByOffset(-(sliderWidth - offsetRight), 0) // Kéo từ phải về 80%
                .release()
                .perform();
        // Click nút "Lọc"
        // Nhấp nút "Lọc"
        WebElement filterButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".btn-filter")));
        actions.moveToElement(filterButton).click().perform(); // Dùng Actions để nhấp

//        WebElement filterButton = driver.findElement(By.cssSelector(".btn-filter"));
//        js.executeScript("arguments[0].click();", filterButton);

        // Chờ giá trị cập nhật
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("price_form")));

        // Kiểm tra giá trị sau khi lọc
//        WebElement minPrice = driver.findElement(By.id("price_form"));
//        WebElement maxPrice = driver.findElement(By.id("price_to"));
        WebElement minPriceElement = driver.findElement(By.id("price_form"));
        WebElement maxPriceElement = driver.findElement(By.id("price_to"));
//        // Kiểm tra giá trị sau khi lọc
//        WebElement minPrice = driver.findElement(By.id("price_form"));
//        WebElement maxPrice = driver.findElement(By.id("price_to"));

//        int minValue = Integer.parseInt(minPrice.getText().replace("đ", "").replace(",", "").trim());
//        int maxValue = Integer.parseInt(maxPrice.getText().replace("đ", "").replace(",", "").trim());
//
//        Assert.assertTrue(minValue >= 1000000, "Min price không đúng!");
//        Assert.assertTrue(maxValue <= 4000000, "Max price không đúng!");

//        int minValue = Integer.parseInt(minPrice.getText().replace("VNĐ", "").replace(",", "").trim());
//        int maxValue = Integer.parseInt(maxPrice.getText().replace("VNĐ", "").replace(",", "").trim());
//
//        // Kiểm tra kết quả
//        Assert.assertTrue(minValue >= 900000 && minValue <= 1100000, "Min price không đúng, giá trị thực tế: " + minValue);
//        Assert.assertTrue(maxValue >= 3900000 && maxValue <= 4100000, "Max price không đúng, giá trị thực tế: " + maxValue);

        int actualMinPrice = Integer.parseInt(minPriceElement.getText().replace("VNĐ", "").replace(",", "").trim());
        int actualMaxPrice = Integer.parseInt(maxPriceElement.getText().replace("VNĐ", "").replace(",", "").trim());

        // Kiểm tra với khoảng linh hoạt (±10% để tránh sai lệch nhỏ)
        int tolerance = (int) (maxSliderValue * 0.1); // 10% của 5,000,000đ = 500,000đ
        Assert.assertTrue(actualMinPrice >= expectedMinPrice - tolerance && actualMinPrice <= expectedMinPrice + tolerance,
                          "Min price không đúng! Thực tế: " + actualMinPrice + ", Mong đợi: " + expectedMinPrice);
        Assert.assertTrue(actualMaxPrice >= expectedMaxPrice - tolerance && actualMaxPrice <= expectedMaxPrice + tolerance,
                          "Max price không đúng! Thực tế: " + actualMaxPrice + ", Mong đợi: " + expectedMaxPrice);

        // In kết quả để kiểm tra
        System.out.println("Min Price - Thực tế: " + actualMinPrice + ", Mong đợi: " + expectedMinPrice);
        System.out.println("Max Price - Thực tế: " + actualMaxPrice + ", Mong đợi: " + expectedMaxPrice);
    }



    @DataProvider(name = "priceFilterData")
    public Object[][] createPriceFilterData() {
        return new Object[][]{
                {500000, 2000000},  // 500,000đ - 2,000,000đ
                {1000000, 4000000}, // 1,000,000đ - 4,000,000đ
                {200000, 1500000},  // 200,000đ - 1,500,000đ
                {1500000, 3000000}, // 1,500,000đ - 3,000,000đ
                {0, 5000000}        // 0đ - 5,000,000đ (toàn phạm vi)
        };
    }
}