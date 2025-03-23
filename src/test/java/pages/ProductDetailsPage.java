package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ProductDetailsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;

    // Locators
    private final By productTitle = By.xpath("//span[normalize-space()='ÁO HOODIE NAM - TOTODAY - GOOD MANNERS MATTERS']");
    private final By productDescription = By.xpath("(//p)[61]");
    private final By reviewTab = By.xpath("(//button[contains(text(),'Đánh giá')])[1]");
    private final By detailsTab = By.xpath("(//button[contains(text(),'Chi tiết sản phẩm')])[1]");
    private final By reviewContent = By.xpath("(//p[contains(text(),'Chưa có đánh giá nào cho sản phẩm này')])[1]");
    private final By detailsContent = By.xpath("/html[1]/body[1]/section[1]/div[1]/div[1]/div[2]/div[1]/div[1]");

    public ProductDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
    }

    public void navigateToProduct() {
        driver.findElement(By.xpath("/html[1]/body[1]/main[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]")).click();
        driver.findElement(By.xpath("/html[1]/body[1]/main[1]/div[2]/div[1]/div[2]/div[1]/a[1]")).click();
        driver.findElement(By.xpath("//div[@class='col']")).click();
        sleep(2);
    }

    public String getProductTitleInList() {
        WebElement product = wait.until(ExpectedConditions.presenceOfElementLocated(productTitle));
        return product.getText();
    }

    public String getProductTitleOnPage() {
        WebElement productTitleElement = wait.until(ExpectedConditions.presenceOfElementLocated(productTitle));
        return productTitleElement.getText();
    }

    public boolean isDescriptionDisplayed() {
        WebElement descElement = wait.until(ExpectedConditions.presenceOfElementLocated(productDescription));
        js.executeScript("arguments[0].scrollIntoView(true);", descElement);
        sleep(1);
        return descElement.isDisplayed();
    }

    public String getDescription() {
        return driver.findElement(productDescription).getText();
    }

    public void switchToReviewTab() {
        WebElement reviewTabElement = wait.until(ExpectedConditions.elementToBeClickable(reviewTab));
        js.executeScript("arguments[0].scrollIntoView(true);", reviewTabElement);
        sleep(1);
        try {
            reviewTabElement.click();
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].click();", reviewTabElement);
        }
    }

    public void switchToDetailsTab() {
        WebElement detailsTabElement = wait.until(ExpectedConditions.elementToBeClickable(detailsTab));
        try {
            detailsTabElement.click();
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].click();", detailsTabElement);
        }
    }

    public boolean isReviewContentDisplayed() {
        WebElement content = wait.until(ExpectedConditions.visibilityOfElementLocated(reviewContent));
        return content.isDisplayed();
    }

    public boolean isDetailsContentDisplayed() {
        WebElement content = wait.until(ExpectedConditions.visibilityOfElementLocated(detailsContent));
        return content.isDisplayed();
    }

    private void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}