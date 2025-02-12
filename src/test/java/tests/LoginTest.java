package tests;

import config.DriverConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class LoginTest extends DriverConfig {
    String loginURL = baseURL+"user/signin";
    private final WebDriver driver = getDriver();

    @BeforeSuite
    void setupSuite(){
        WebDriverManager.chromedriver().setup();
        driver.get(loginURL);
    }

    @AfterSuite
    void cleanupSuite(){
        driver.quit();
    }

    @Test
    void compareTitle(){
        String actualTitle = driver.getTitle();
        String expectedTitle = "Đăng nhập";
        Assert.assertEquals(actualTitle,expectedTitle);
    }

}
