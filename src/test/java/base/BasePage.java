package base;

import config.DriverConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utils.Tools;

import java.util.Map;

public class BasePage extends DriverConfig {
    protected Tools tools;
    protected JavascriptExecutor js;

    @BeforeSuite
    protected void setupSuite() {
        driver = getDriver();
        driver.get(baseURL);
        tools = new Tools(driver);
        js = (JavascriptExecutor) driver;
    }

    @AfterSuite
    protected void cleanupTest() {
        quitDriver();
    }
}
