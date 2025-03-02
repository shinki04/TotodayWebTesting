package base;

import config.DriverConfig;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utils.Tools;



public class BaseTest extends DriverConfig {
    protected Tools tools;
    protected JavascriptExecutor js;
    protected Actions actions;
    @BeforeSuite
    protected void setupSuite() {
        driver = getDriver();
        driver.get(baseURL);
        tools = new Tools(driver);
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
    }

    @AfterSuite
    protected void cleanupTest() {
        quitDriver();
    }
}
