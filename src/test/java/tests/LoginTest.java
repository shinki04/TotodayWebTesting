package tests;

import config.DriverConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class LoginTest implements DriverConfig {
    String loginURL = baseURL+"user/signin";

    @BeforeSuite
    void setupSuite(){
        WebDriverManager.chromedriver().setup();
        driver.get(loginURL);
        chromeOptions.addArguments("--headless");
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
