package base;

import config.DriverConfig;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utils.Tools;



public class BaseTest extends DriverConfig {
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
