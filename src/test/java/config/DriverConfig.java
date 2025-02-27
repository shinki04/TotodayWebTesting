package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Map;

public class DriverConfig {


    public static String baseURL = "https://totoday.vn";
    public static WebDriver driver;

    public static WebDriver getDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions chromeOptions = new ChromeOptions();
//            chromeOptions.addArguments("--headless=new");
//            chromeOptions.setExperimentalOption("prefs", Map.of(
//                    "profile.default_content_setting_values.notifications", 1,
//                    "profile.default_content_setting_values.popups", 1
//            ));
//            chromeOptions.addArguments("--disable-popup-blocking");

            driver = new ChromeDriver(chromeOptions);

        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public static void sleep(int second) {
        try {
            Thread.sleep(second + 1000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
