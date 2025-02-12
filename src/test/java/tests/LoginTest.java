package tests;

import config.DriverConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends LoginPage {

    @Test
    static void compareTitle(){
        String actualTitle = driver.getTitle();
        String expectedTitle = "Đăng nhập";
        Assert.assertEquals(actualTitle,expectedTitle);
    }

}
