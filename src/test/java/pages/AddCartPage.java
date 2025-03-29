package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.Tools;

public class AddCartPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Tools tools;


    //    Input Field
    private final By qtyInputField = By.id("qty");
    //    Button
    private final By addToCartBtn = By.id("addToCart");
    //    Message Success
    private final By modelAddSuccess = By.cssSelector(".modal-add-success.active");


    public AddCartPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.tools = new Tools(driver, wait);
    }

    private WebElement getQuantityInputField() {
        return tools.getElement(qtyInputField);
    }

    public int getMinQtyByDom() {
        return Integer.parseInt(getQuantityInputField().getDomAttribute("min"));
    }

    public int getMaxQtyByDom() {
        return Integer.parseInt(getQuantityInputField().getDomAttribute("max"));
    }

    private Alert getAlert() {
        try {
            return wait.until(ExpectedConditions.alertIsPresent());
        } catch (TimeoutException timeoutException) {
            return null;
        }

    }


    public String getAlertText() {
        if (checkAlertPresent()) {
            return getAlert().getText();
        }
        return null;
    }


    public boolean checkAlertPresent() {
        return getAlert() != null;
    }

    public boolean checkNotificationAddSuccessDisplayed() {
        return tools.getElement(modelAddSuccess).isDisplayed();
    }

    public String getNotificationAddSuccessMessage(){
        return tools.getElement(modelAddSuccess).getText();
    }


//* ========================== Interactive Element ========================================

    public void clickColorByTitle(String title) {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("div.colorPicker.clearfix a[title='" + title + "']"))).click();
    }

    public void clickSizeByDataValue(String dataValue) {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("div.sizePicker.clearfix a[data-value='" + dataValue + "']"))).click();
    }

    public void enterQuantity(int qty) {
        getQuantityInputField().sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE, String.valueOf(qty));
    }

    public void acceptAlert() {
        if (checkAlertPresent()) {
            driver.switchTo().alert().accept();
        }
    }

    public void clickAddBtn(){
        try {
            wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();
        } catch (TimeoutException timeoutException){
            Assert.fail("Button add to cart not found");
        }
    }


}
