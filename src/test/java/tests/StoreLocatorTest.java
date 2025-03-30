package tests;

import base.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.StoreLocatorPage;
import utils.Tools;

public class StoreLocatorTest extends BaseTest {
    private static Tools tools;
    private WebDriver driver;
    private StoreLocatorPage storeLocatorPage;

    @BeforeClass
    void setupClass() {
        driver = getDriver();
        tools = new Tools(driver,wait);
        storeLocatorPage = new StoreLocatorPage(driver,wait);
    }

    @BeforeMethod
    void setupMethod() {
        driver.get(baseURL);
        storeLocatorPage.navigateToStoreLocator();
    }

    @AfterClass
    void cleanupClass() {
        quitDriver();
    }

    @Test
    void testOpenStoreLocatorPage() {
        Assert.assertTrue(storeLocatorPage.isStoreListDisplayed(), 
            "Store locator page should be displayed");
    }

    @Test
    public void testAccessStoreLocatorPage() {
        storeLocatorPage.selectCity("Cần Thơ");
        Assert.assertTrue(storeLocatorPage.isCityInAddress("Cần Thơ"), 
            "Can Tho should be in the store address");
    }

    @Test
    public void testFindStoreLocationGoVap() {
        Assert.assertNotNull(storeLocatorPage.findDistrictElement("Quận Gò Vấp"), 
            "Go Vap district should be found");

        storeLocatorPage.clickViewMap("(//li[5]//div[1]//a[1])");
        sleep(2);

        String addressGoVap = storeLocatorPage.getStoreAddress(
            "(//p[contains(text(),'Số 55 Quang Trung, Phường 10, Quận Gò Vấp, TP HCM')])[1]");
        String mapAddress = storeLocatorPage.getStoreAddress(
            "(//p[contains(text(),'Số 55 Quang Trung, Phường 10, Quận Gò Vấp, TP HCM')])");

        Assert.assertEquals(addressGoVap, mapAddress, 
            "Store address should match map address for Go Vap location");
    }
}
