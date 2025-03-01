package base;

import config.DriverConfig;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utils.ExcelReader;
import utils.Notification;
import utils.PopupHandler;
import utils.Tools;



public class BaseTest extends DriverConfig {
    protected Tools tools;
    protected JavascriptExecutor js;
    protected Notification notification;
    protected ExcelReader excelReader;
    protected PopupHandler popupHandler;


    @BeforeSuite
    protected void setupSuite() {
        driver = getDriver();
        driver.get(baseURL);
        tools = new Tools(driver);
        js = (JavascriptExecutor) driver;
        notification = new Notification(driver);
        popupHandler = new PopupHandler(driver);
    }

    @AfterSuite
    protected void cleanupTest() {
        quitDriver();
    }
}
