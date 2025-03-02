package utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Notification {
    private WebDriver driver;
    private WebDriverWait wait;

    public Notification(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Chờ tối đa 5 giây
    }

    // Kiểm tra xem alert có hiển thị không
    public boolean isAlertPresent() {
        try {
            wait.until(ExpectedConditions.alertIsPresent()); // Chờ alert xuất hiện
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Lấy nội dung của alert
    public String getAlertText() {
        if (isAlertPresent()) {
            Alert alert = driver.switchTo().alert();
            return alert.getText();
        }
        return null;
    }

    // Chấp nhận alert (Nhấn OK)
    public void acceptAlert() {
        if (isAlertPresent()) {
            driver.switchTo().alert().accept();
        }
    }

    // Từ chối alert (Nhấn Cancel nếu có)
//    public void dismissAlert() {
//        if (isAlertPresent()) {
//            driver.switchTo().alert().dismiss();
//        }
//    }
}
