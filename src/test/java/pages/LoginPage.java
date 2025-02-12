package pages;

import config.DriverConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class LoginPage implements DriverConfig {

    protected static String loginURL = baseURL+"user/signin";

    @BeforeSuite
    protected void Bs(){
        WebDriverManager.chromedriver().setup();
        driver.get(loginURL);
        chromeOptions.addArguments("--headless");
    }

    @AfterSuite
    protected void As(){
        driver.quit();
    }


}
