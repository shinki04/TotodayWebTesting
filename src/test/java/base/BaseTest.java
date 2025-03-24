package base;

import config.DriverConfig;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utils.ExcelReader;
import utils.Notification;
import utils.PopupHandler;
import utils.Tools;

import java.time.Duration;


public class BaseTest extends DriverConfig {
    protected static WebDriver driver;
    protected Tools tools;
    protected JavascriptExecutor js;
    protected Notification notification;
    protected ExcelReader excelReader;
    protected PopupHandler popupHandler;
    protected Actions actions;
    protected WebDriverWait wait;
    @BeforeSuite
    protected void setupSuite() {
        driver = getDriver();
        driver.get(baseURL);
        driver.manage().window().maximize();
        js = (JavascriptExecutor) driver;
        actions = new Actions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        tools = new Tools(driver, wait);
        notification = new Notification(driver);
        popupHandler = new PopupHandler(driver);
    }

    @AfterSuite
    protected void cleanupSuite() {
        quitDriver();
    }
}
