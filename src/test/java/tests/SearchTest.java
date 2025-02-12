package tests;

import config.DriverConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

public class SearchTest implements DriverConfig {

    private WebElement searchInput;

    @BeforeSuite
    void setupSuite(){
        WebDriverManager.chromedriver().setup();
        driver.get(baseURL);
    }

    @AfterSuite
    void cleanupSuite(){
        driver.quit();
    }

    @BeforeClass
    void setupClass(){
        searchInput = driver.findElement(By.xpath("//input[@class='search-input']"));
        if(searchInput.isDisplayed()){
            System.out.println("Xuất hiện");
        };
    }


    @Test(priority = 0)
    void searchSuccess(){
        Assert.assertTrue(searchInput.isDisplayed());
    }
}
