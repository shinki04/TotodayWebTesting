package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public interface DriverConfig {

    WebDriver driver = new ChromeDriver() ;
    String baseURL = "https://totoday.vn/";
    ChromeOptions chromeOptions = new ChromeOptions();



    static void quitDriver(){
        driver.quit();
    }

//    public static void sleep(int s ) throws InterruptedException {
//        Thread.sleep(s+1000);
//    }
}
