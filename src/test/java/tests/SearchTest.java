package tests;

import config.DriverConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import utils.tools;


public class SearchTest extends DriverConfig {
    private static tools tols = new tools();
    private WebElement searchInput;
    private WebElement messageNoProduct;
    private final WebDriver driver = getDriver();

    @BeforeSuite
    void setupSuite(){
        WebDriverManager.chromedriver().setup();
        driver.get(baseURL);

    }

    @AfterSuite
    void cleanupSuite(){
        driver.quit();
    }

    @BeforeTest
    void setupClass(){
    }




    @Test(priority = 0)
    void searchSuccess(){
        searchInput = driver.findElement(By.xpath("//input[@class='search-input']"));
        String searchItem = "Quần Jean";
        searchInput.sendKeys(searchItem);
        searchInput.submit();
        Assert.assertNotEquals(driver.getCurrentUrl(),(baseURL+"search?q="+tols.addPlusToString(searchItem)));
    }

    @Test(priority = 1)
    void searchFailed(){
        searchInput = driver.findElement(By.xpath("//input[@class='search-input']"));
        String searchItem = "kahwfv";
        searchInput.sendKeys(searchItem);
        searchInput.submit();
        Assert.assertNotEquals(driver.getCurrentUrl(),(baseURL+"search?q="+tols.addPlusToString(searchItem)));
        messageNoProduct = driver.findElement(By.xpath("//div[@class='no-product']"));
        Assert.assertFalse(messageNoProduct.isDisplayed());

    }




}
