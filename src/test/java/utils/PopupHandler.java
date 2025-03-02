package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PopupHandler {
    private WebDriver driver;

    public PopupHandler(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Kiểm tra xem popup có xuất hiện không.
     *
     * @param className Tên class của popup (ví dụ: "formErrorContent")
     * @return true nếu popup xuất hiện, ngược lại false.
     */
    public boolean isPopupPresent(String className) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(className)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Lấy nội dung của popup nếu có.
     *
     * @param className Tên class của popup (ví dụ: "formErrorContent")
     * @return Nội dung thông báo nếu popup tồn tại, ngược lại trả về null.
     */
    public String getPopupMessage(String className) {
        if (isPopupPresent(className)) {
            return driver.findElement(By.className(className)).getText();
        }
        return null;
    }
}
