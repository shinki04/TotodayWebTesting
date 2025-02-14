package utils;
import org.openqa.selenium.WebDriver;

public class Notification {
    private WebDriver driver;

    public Notification(WebDriver driver) {
        this.driver = driver;
    }

    // Kiểm tra xem alert có hiển thị không
    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Lấy nội dung của alert
    public String getAlertText() {
        if (isAlertPresent()) {
            return driver.switchTo().alert().getText();
        }
        return null;
    }

    public void acceptAlert() {
        if (isAlertPresent()) {
            driver.switchTo().alert().accept();
        }
    }


}

