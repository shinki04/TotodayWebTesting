package tests;

import base.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.OrderLookupPage;
import utils.Tools;

public class OrderLookupTest extends BaseTest {
    private static Tools tools;
    private WebDriver driver;
    private OrderLookupPage orderLookupPage;

    @BeforeClass
    void setupClass() {
        driver = getDriver();
        tools = new Tools(driver);
        orderLookupPage = new OrderLookupPage(driver);
    }

    @BeforeMethod
    void setupMethod() {
        driver.get(baseURL);
        orderLookupPage.navigateToOrderLookup();
    }

    @AfterClass
    void cleanupClass() {
        quitDriver();
    }

    @Test
    void testOrderSearchFailed() {
        String orderSearchItem = "0708712413";
        orderLookupPage.enterOrderSearch(orderSearchItem);
        orderLookupPage.submitOrderSearch();
        
        Assert.assertTrue(orderLookupPage.isUrlContainsSearchQuery(baseURL));
        sleep(5);
        Assert.assertEquals(orderLookupPage.getNoOrderMessage(), "Không tìm thấy đơn hàng theo yêu cầu");
    }

    @Test
    void testOrderSearchWithSpecialCharacters() {
        String orderSearchItem = "\"@b$$^";
        orderLookupPage.enterOrderSearch(orderSearchItem);
        orderLookupPage.submitOrderSearch();
        
        sleep(5);
        Assert.assertEquals(orderLookupPage.getNoOrderMessage(), "Không tìm thấy đơn hàng theo yêu cầu");
    }

    @Test
    void testOrderSearchWithEmptyKeyword() {
        String orderSearchItem = "";
        orderLookupPage.enterOrderSearch(orderSearchItem);
        orderLookupPage.submitOrderSearch();
        
        sleep(5);
        Assert.assertEquals(orderLookupPage.getNoOrderMessage(), "Không tìm thấy đơn hàng theo yêu cầu");
    }
}
