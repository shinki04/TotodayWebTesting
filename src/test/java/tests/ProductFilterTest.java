package tests;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class ProductFilterTest extends BasePage {
    private static Tools tools;
    public WebElement filterColorClass;
    public List<WebElement> filterColorOption;

    private final String sortProductURL = baseURL + "/phu-kien-pc360511.html";

    @BeforeTest
    void setupTest() {

    }

    @BeforeClass
    void setupClass() {
        driver.navigate().to(sortProductURL);
        tools = new Tools(driver);
        driver.manage().window().maximize();
        sleep(4);
        driver.navigate().refresh();
        WebElement filterClass = tools.getElementByXpath("/html[1]/body[1]/main[1]/div[2]/div[1]/div[2]/div[2]/div[2]/p[1]");
        filterClass.click();
    }

    @AfterClass
    void cleanClass() {
    }

    @BeforeMethod
    void setupMethod() {
        sleep(8);
        try {
            filterColorClass = driver.findElement(By.xpath("//div[@class='filter-item-wrap filter-color']//ul"));
            js.executeScript("arguments[0].style.display = 'block'", filterColorClass);
            filterColorOption = filterColorClass.findElements(By.tagName("a"));
        } catch (NullPointerException e) {
            System.out.println("Không tìm thấy bộ lọc màu: " + e.getMessage());
            e.printStackTrace();
        }
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
        driver.navigate().refresh();
        List<WebElement> optionColorItems = filterColorClass.findElements(By.tagName("li"));

        for (WebElement optionColorItem : optionColorItems) {
            String filterValue = optionColorItem.getAttribute("data-filter");

            if (filterValue.equals(expectedFilter)) {
                WebElement filterLink = optionColorItem.findElement(By.tagName("a"));
                filterLink.click();
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
        List<WebElement> optionColorItems = filterColorClass.findElements(By.tagName("li"));
        List<String> selectedFilters = new ArrayList<>();

        for (int i = 0; i < 5; i++) { // Chọn 5 bộ lọc đầu tiên
            WebElement optionColorItem = optionColorItems.get(i);
            String filterValue = optionColorItem.getAttribute("data-filter");

            if (!filterValue.isEmpty()) {
                WebElement filterLink = optionColorItem.findElement(By.tagName("a"));
                filterLink.click();
                selectedFilters.add(filterValue);
            }
        }

        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = baseURL + "/phu-kien-pc360511.html?i4=" + String.join(",", selectedFilters);

        System.out.println("Expected URL: " + expectedUrl);
        System.out.println("Current URL: " + currentUrl);

//        Assert.assertEquals(currentUrl, expectedUrl, "Bộ lọc màu cộng dồn không chính xác");
        Assert.assertEquals(currentUrl, expectedUrl.replace("/phu-kien-pc360511.html?i4=", "").replace("?", "").replace("=", ""));

    }
}
