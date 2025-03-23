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

public class SortPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Tools tools;
    private final JavascriptExecutor js;
    private final Actions actions;


    private final By sortClass = By.xpath("//div[contains(@class,'filter-sort')]//div[contains(@class,'sort')]");
    private final By sortList = By.xpath("//ul[@class='filter-item-list']");

    //        Constructor
    public SortPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.tools = new Tools(driver, wait);
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);

    }

    public List<WebElement> getSortList() {
        WebElement sortListElement = wait.until(ExpectedConditions.presenceOfElementLocated(sortList));
        return sortListElement.findElements(By.tagName("li"));
    }

    // Click vào một option cụ thể theo text
    public boolean checkSelectSortOption(List<WebElement> sortOptions, String optionText) {

        for (WebElement option : sortOptions) {

            if (option.getText().equalsIgnoreCase(optionText)) {
                option.click();
                return isOptionSelected(option);
            }
        }
        return false;
    }

    // Kiểm tra xem option đã chọn có được tô màu xanh hay không
    public boolean isOptionSelected(WebElement option) {

        String className = option.getAttribute("class");
        assert className != null;
        return className.contains("selected") || className.contains("active");

    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }

//* ========================== Interactive Element ========================================

    public void clickSortClass() {
        wait.until(ExpectedConditions.elementToBeClickable(sortClass)).click();
    }

    public void clickSortList() {
        WebElement sortListElement = wait.until(ExpectedConditions.presenceOfElementLocated(sortList));
//        sortListElement.click();
        if (!sortListElement.getCssValue("display").equals("block")) {
            js.executeScript("arguments[0].style.display = 'block';", sortListElement);
        }
    }

    public void clickSortValue(String optionValue) {
        wait.until(ExpectedConditions.presenceOfElementLocated(sortList)).click();
    }


}
