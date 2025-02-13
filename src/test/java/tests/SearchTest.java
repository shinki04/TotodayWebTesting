package tests;

import config.DriverConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import utils.Tools;


public class SearchTest extends DriverConfig {
    private static final Tools tools = new Tools();
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
    void setupTest(){
        searchInput = driver.findElement(By.xpath("//input[@class='search-input']"));
    }

    @AfterTest
    void clearTest(){
        quitDriver();
    }



    @Test(priority = 0)
    void testSearchSuccess(){
        searchInput = driver.findElement(By.xpath("//input[@class='search-input']"));
        String searchItem = "Quần Jean";
        searchInput.sendKeys(searchItem);
        searchInput.submit();
        Assert.assertNotEquals(driver.getCurrentUrl(),(baseURL+"search?q="+ tools.addPlusToString(searchItem)));
    }

    @Test(priority = 1)
    void testSearchWithIncorrectKeyword(){
        searchInput = driver.findElement(By.xpath("//input[@class='search-input']"));
        String searchItem = "quần đùi";
        searchInput.sendKeys(searchItem);
        searchInput.submit();
        Assert.assertNotEquals(driver.getCurrentUrl(),(baseURL+"search?q="+ tools.addPlusToString(searchItem)));
        WebElement messageNoProduct = driver.findElement(By.xpath("//div[@class='no-product']"));
        Assert.assertTrue(messageNoProduct.isDisplayed());
    }

    @Test(priority = 2)
    void testSearchWithItestSearchWithSpecialCharactersncorrectKeyword(){
        searchInput = driver.findElement(By.xpath("//input[@class='search-input']"));
        String searchItem = "@gmail@#$";
        searchInput.sendKeys(searchItem);
        searchInput.submit();
        Assert.assertNotEquals(driver.getCurrentUrl(),(baseURL+"search?q="+ tools.addPlusToString(searchItem)));
        WebElement messageNoProduct = driver.findElement(By.xpath("//div[@class='no-product']"));
        Assert.assertTrue(messageNoProduct.isDisplayed());

    }






}
