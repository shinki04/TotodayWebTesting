package tests;

import config.DriverConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import utils.Tools;


public class SearchTest extends DriverConfig {
    private static Tools tools ;
    private WebElement searchInput;
    private WebDriver driver;
    private WebElement messageNoProduct;
    private WebElement sectionProduct;



    @BeforeTest
    void setupTest(){
        WebDriverManager.chromedriver().setup();
        driver = getDriver();
        driver.get(baseURL);
        tools = new Tools(driver);

    }


    @AfterSuite
    void cleanupTest(){
        quitDriver();
    }


    @BeforeMethod
    void setupTMethod(){
        searchInput = driver.findElement(By.xpath("//input[@class='search-input']"));
    }


    @Test(priority = 0)
    void testSearchSuccess(){
//        searchInput = driver.findElement(By.xpath("//input[@class='search-input']"));
        String searchItem = "Quần Jean";
        searchInput.sendKeys(searchItem);
        searchInput.submit();
        if (driver.getCurrentUrl().contains(baseURL+"search?q=")){
            System.out.println("Search input have change");
        }
//        Assert.assertNotEquals(driver.getCurrentUrl(),(baseURL+"search?q="+ tools.addPlusToString(searchItem)));
//        Assert.assertTrue(checkTestElementNotDisplayed(driver.findElement(By.xpath("//div[@class='no-product']"))));
        messageNoProduct = tools.getElementByXpath("//div[@class='no-product']");
        Assert.assertFalse(tools.checkElementIsDisplayed(messageNoProduct),"No product displayed");
        sectionProduct = tools.getElementByXpath("//div[@class='section-product-wrap']");
        Assert.assertTrue(tools.checkElementIsDisplayed(sectionProduct),"No product displayed");
        sleep(5);

    }


    @Test(priority = 1)
    void testSearchFailed(){
//        searchInput = driver.findElement(By.xpath("//input[@class='search-input']"));
        String searchItem = "quần đùi";
        searchInput.sendKeys(searchItem);
        searchInput.submit();

//        checkTestElementIsDisplayed(driver.findElement(By.xpath("//div[@class='no-product']")));

//        try {
//            messageNoProduct = driver.findElement(By.xpath("//div[@class='no-product']"));
//            if (messageNoProduct.isDisplayed()) {
//                System.out.println("No product message found: " + messageNoProduct.getText());
//                Assert.assertTrue(messageNoProduct.isDisplayed(),"Div category found");
//            }
//        } catch (NoSuchElementException e) {
//            messageNoProduct = null;
//        }
//
//
//
//        try {
//            categoryMainDiv = driver.findElement(By.xpath("//div[@class='section-product-wrap']"));
//            if (categoryMainDiv.isDisplayed()) {
//                Assert.assertFalse(categoryMainDiv.isDisplayed(),"Div category was found");
//            }
//        } catch (NoSuchElementException e) {
//            categoryMainDiv = null;
//        }
        messageNoProduct = tools.getElementByXpath("//div[@class='no-product']");
        Assert.assertTrue(tools.checkElementIsDisplayed(messageNoProduct),"Category was found");
        sectionProduct = tools.getElementByXpath("//div[@class='section-product-wrap']");
        Assert.assertFalse(tools.checkElementIsDisplayed(sectionProduct),"Category not found");

        sleep(5);
    }

    @Test(priority = 2)
    void testSearchWithItestSearchWithSpecialCharactersncorrectKeyword(){
//        searchInput = driver.findElement(By.xpath("//input[@class='search-input']"));
        String searchItem = "@gmail@#$";
        searchInput.sendKeys(searchItem);
        searchInput.submit();

        messageNoProduct = tools.getElementByXpath("//div[@class='no-product']");
        Assert.assertTrue(tools.checkElementIsDisplayed(messageNoProduct),"Category was found");
        sectionProduct = tools.getElementByXpath("//div[@class='section-product-wrap']");
        Assert.assertFalse(tools.checkElementIsDisplayed(sectionProduct),"Category not found");
        sleep(5);

    }

    @Test(priority = 3)
    void testSearchWithCaseInsensitiveKeyword(){
//        searchInput = driver.findElement(By.xpath("//input[@class='search-input']"));
        String searchItem = "Áo kHoÁc naM";
        searchInput.sendKeys(searchItem);
        searchInput.submit();

        messageNoProduct = tools.getElementByXpath("//div[@class='no-product']");
        Assert.assertTrue(tools.checkElementIsDisplayed(messageNoProduct),"Category was found");
        sectionProduct = tools.getElementByXpath("//div[@class='section-product-wrap']");
        Assert.assertFalse(tools.checkElementIsDisplayed(sectionProduct),"Category not found");

        sleep(5);
    }

    @Test(priority = 4)
    void testSearchWithLeadingTrailingSpaces(){
        String searchItem = "   Quần Jean  ";
        searchInput.sendKeys(searchItem);
        searchInput.submit();

        messageNoProduct = tools.getElementByXpath("//div[@class='no-product']");
        Assert.assertTrue(tools.checkElementIsDisplayed(messageNoProduct),"Category was found");
        sectionProduct = tools.getElementByXpath("//div[@class='section-product-wrap']");
        Assert.assertFalse(tools.checkElementIsDisplayed(sectionProduct),"Category not found");

        sleep(5);
    }

    @Test(priority = 5)
    void testSearchWithEmptyKeyword(){
        String searchItem = "";
        searchInput.sendKeys(searchItem);
        searchInput.submit();

        messageNoProduct = tools.getElementByXpath("//div[@class='no-product']");
        Assert.assertFalse(tools.checkElementIsDisplayed(messageNoProduct),"Category not found");
        sectionProduct = tools.getElementByXpath("//div[@class='section-product-wrap']");
        Assert.assertTrue(tools.checkElementIsDisplayed(sectionProduct),"Category was found");
        sleep(5);

    }

    @Test(priority = 6)
    void testSearchWithMaxLengthKeyword(){
        String searchItem = tools.generateRandomString(256);
        searchInput.sendKeys(searchItem);
        searchInput.submit();
        messageNoProduct = tools.getElementByXpath("//div[@class='no-product']");
        Assert.assertTrue(tools.checkElementIsDisplayed(messageNoProduct),"Category was found");
        sectionProduct = tools.getElementByXpath("//div[@class='section-product-wrap']");
        Assert.assertFalse(tools.checkElementIsDisplayed(sectionProduct),"Category not found");
        sleep(5);
    }









}
