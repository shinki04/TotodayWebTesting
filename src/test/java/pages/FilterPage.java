package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Tools;

import java.util.List;

public class FilterPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Tools tools;
    private final JavascriptExecutor js;
    private final Actions actions;


    private final By filterClass = By.xpath("/html[1]/body[1]/main[1]/div[2]/div[1]/div[2]/div[2]/div[2]/p[1]");
    private final By filterColorBlock = By.xpath("//div[@class='filter-item-wrap filter-color']//p[contains(text(),'Màu sắc')]");
    private final By filterColorList = By.xpath("//div[@class='filter-item-wrap filter-color']//ul");
    // Price
    private final By filterPriceClass = By.xpath("//div[@class='filter-item-wrap filter-price']//p[normalize-space()='Giá']");
    private final By slider = By.id("slider-range");
    private final By leftHandle = By.cssSelector(".ui-slider-handle:nth-of-type(1)");
    private final By rightHandle = By.cssSelector(".ui-slider-handle:nth-of-type(2)");
    private final By filterButton = By.cssSelector(".btn-filter");
    private final By minPriceField = By.id("price_form");
    private final By maxPriceField = By.id("price_to");

    //        Constructor
    public FilterPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.tools = new Tools(driver, wait);
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);

    }


    public List<WebElement> getColorOption() {
        return wait.until(ExpectedConditions.elementToBeClickable(filterColorList)).findElements(By.tagName("li"));
    }

    public String getDataValue(WebElement element) {
        return element.getDomAttribute("data-value");
    }

    public int getMinPrice() {
        WebElement minPriceElement = wait.until(ExpectedConditions.presenceOfElementLocated(minPriceField));
        return Integer.parseInt(minPriceElement.getText().replace("VNĐ", "").replace(",", "").trim());
    }

    public int getMaxPrice() {
        WebElement maxPriceElement =wait.until(ExpectedConditions.presenceOfElementLocated(maxPriceField));
        return Integer.parseInt(maxPriceElement.getText().replace("VNĐ", "").replace(",", "").trim());
    }

//* ========================== Interactive Element ========================================

    public void clickFilterClass() {
        wait.until(ExpectedConditions.elementToBeClickable(filterClass)).click();
    }

    public void clickFilterColorClass() {
        WebElement filterColor = wait.until(ExpectedConditions.presenceOfElementLocated(filterColorList));
        filterColor.click();
        if (!filterColor.getCssValue("display").equals("block")) {
            js.executeScript("arguments[0].style.display = 'block';", filterColorList);
        }
    }

    public void clickFilterColorBlock() {
        wait.until(ExpectedConditions.elementToBeClickable(filterColorBlock)).click();

    }

    public void clickEachColor(WebElement colorElement) {
        wait.until(ExpectedConditions.elementToBeClickable(colorElement)).click();
    }

    public void clickPriceClass() {
        wait.until(ExpectedConditions.elementToBeClickable(filterPriceClass)).click();
    }

    public void setSlider(int expectedMinPrice, int expectedMaxPrice) {
        WebElement sliderElement = wait.until(ExpectedConditions.elementToBeClickable(slider));
        WebElement leftHandleElement = sliderElement.findElement(leftHandle);
        WebElement rightHandleElement = sliderElement.findElement(rightHandle);

        int sliderWidth = sliderElement.getSize().getWidth();
        int maxSliderValue = 5000000; // Giá trị tối đa từ HTML (5,000,000đ)

        // Tính offset dựa trên tỷ lệ giá trị mong muốn
        int offsetLeft = (int) ((expectedMinPrice / (float) maxSliderValue) * sliderWidth);
        int offsetRight = (int) ((expectedMaxPrice / (float) maxSliderValue) * sliderWidth);

        // Đảm bảo tay cầm có thể kéo thả
        wait.until(ExpectedConditions.elementToBeClickable(leftHandle));
        wait.until(ExpectedConditions.elementToBeClickable(rightHandle));

        // Kéo tay cầm trái đến 20%
        actions.clickAndHold(leftHandleElement)
                .moveByOffset(offsetLeft, 0) // Kéo sang phải 20% chiều rộng
                .release()
                .perform();

        // Kéo tay cầm phải về 80%
        actions.clickAndHold(rightHandleElement)
                .moveByOffset(-(sliderWidth - offsetRight), 0) // Kéo từ phải về 80%
                .release()
                .perform();


    }

    public void clickFilterButton() {
        wait.until(ExpectedConditions.elementToBeClickable(filterButton)).click();

    }
}
