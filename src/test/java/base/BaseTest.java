package base;

import config.DriverConfig;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utils.ExcelReader;
import utils.Notification;
import utils.PopupHandler;
import utils.Tools;



public class BaseTest extends DriverConfig {
    protected static WebDriver driver;
    protected Tools tools;
    protected JavascriptExecutor js;
    protected Notification notification;
    protected ExcelReader excelReader;
    protected PopupHandler popupHandler;
    protected Actions actions;

    @BeforeSuite
    protected void setupSuite() {
        driver = getDriver();
        driver.get(baseURL);
//        driver.manage().window().maximize();
        tools = new Tools(driver);
        js = (JavascriptExecutor) driver;
        notification = new Notification(driver);
        popupHandler = new PopupHandler(driver);
        actions = new Actions(driver);
    }

    @AfterSuite
    protected void cleanupSuite() {
        quitDriver();
    }
}
