package utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AlertNotification {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public AlertNotification(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void acceptAlert() {
        if (checkAlertPresent()) {
            driver.switchTo().alert().accept();
        }
    }

    public Alert getAlert() {
        try {
            return wait.until(ExpectedConditions.alertIsPresent());
        } catch (TimeoutException timeoutException) {
            return null;
        }
    }

    public boolean checkAlertPresent() {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getAlertText() {
        if (checkAlertPresent()) {
            return wait.until(ExpectedConditions.alertIsPresent()).getText();
        } else {
            return "";
        }
    }
}
