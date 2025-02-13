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
    private final WebDriver driver = getDriver();
    private DriverConfig driverConfig;


    @BeforeSuite
    void setupSuite(){
        WebDriverManager.chromedriver().setup();
        driver.get(baseURL);

    }

    @AfterSuite
    void cleanupSuite(){
        quitDriver();
    }


    @BeforeTest
    void setupClass(){
    }




    @Test(priority = 0)
    void testSearchSuccess(){
        searchInput = driver.findElement(By.xpath("//input[@class='search-input']"));
        String searchItem = "Quần Jean";
        searchInput.sendKeys(searchItem);
        searchInput.submit();
        Assert.assertNotEquals(driver.getCurrentUrl(),(baseURL+"search?q="+tols.addPlusToString(searchItem)));
    }

    @Test(priority = 1)
    void testSearchFailed(){
        searchInput = driver.findElement(By.xpath("//input[@class='search-input']"));
        String searchItem = "quần đùi";
        searchInput.sendKeys(searchItem);
        searchInput.submit();
        Assert.assertNotEquals(driver.getCurrentUrl(),(baseURL+"search?q="+tols.addPlusToString(searchItem)));
        WebElement messageNoProduct = driver.findElement(By.xpath("//div[@class='no-product']"));
        Assert.assertTrue(messageNoProduct.isDisplayed());
    }

    @Test(priority = 2)
    void testSearchWithIncorrectKeyword(){

    }






}
