package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.AlertNotification;
import utils.Tools;

import java.util.List;

public class UpdateCartPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Tools tools;
    private final AlertNotification alertnoty;

    //    Button
    private final By cartBtn = By.xpath("//div[@class='cart']//a");
    private final By closeBtn = By.className("close-modal");

    //    Field
    private final By modelContent = By.className("modal-content");
    private final By cartItems = By.xpath("//div[@class='cart-item row']");

    //    Notification
    public String outStuckMessage = "Bạn không thể đặt quá số lượng còn lại của sản phẩm !";
    public String minimumStockMessage = "Bạn phải đặt số lượng tối thiểu là 1 sản phẩm !";


    //        Constructor
    public UpdateCartPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.tools = new Tools(driver, wait);
        this.alertnoty = new AlertNotification(driver, wait);
    }

    private double parsePrice(String priceText) {
        priceText = priceText.replace("đ", "").replace(",", "").trim();
        return Double.parseDouble(priceText);
    }

    public WebElement getQuantityInputFieldByIndex(int index) {
        return wait.until(ExpectedConditions.presenceOfElementLocated((By.xpath("(//div[@class='qty-wrapper d-flex']//input[@class='cart-qty'])[" + index + "]"))));
    }

    public int getMinValue(int index) {
        return Integer.parseInt(getQuantityInputFieldByIndex(index).getDomAttribute("min"));
    }

    public int getMaxValue(int index) {
        return Integer.parseInt(getQuantityInputFieldByIndex(index).getDomAttribute("max"));
    }

    public boolean checkAlertPresent() {
        return alertnoty.checkAlertPresent();
    }

    public String getTextAlert() {
        return alertnoty.getAlertText();
    }

    public List<WebElement> getCartItems() {
        try {
            return driver.findElements(cartItems);
        } catch (NotFoundException e) {
            return null;
        }
    }

    public double getBasePrice(int index) {
        String priceText = driver.findElement(By.xpath("(//div[@class='cart-item row'][" + index + "]//p[@class='product-price--current tp_product_price'])[1]")).getText();
        return parsePrice(priceText);
    }

    public int getQuantity(int index) {
        String quantityText = driver.findElement(By.xpath("(//div[@class='qty-wrapper d-flex']//input[@class='cart-qty'])[" + index + "]")).getAttribute("value");
        return Integer.parseInt(quantityText);
    }

    public double getDisplayedTotalPrice() {
        String totalText = driver.findElement(By.xpath("//span[contains(text(),'Tổng cộng:')]//span")).getText();
        return parsePrice(totalText);
    }

    public void verifyTotalPrice() {
        // Lấy danh sách các sản phẩm trong giỏ hàng
        List<WebElement> cartItems = getCartItems();
        int itemCount = cartItems.size();
        double calculatedTotalPrice = 0.0;

        // Tính tổng giá tiền từ giá cơ sở và số lượng của từng sản phẩm
        for (int i = 1; i <= itemCount; i++) {
            double basePrice = getBasePrice(i); // Giá cơ sở của sản phẩm
            int quantity = getQuantity(i);       // Số lượng của sản phẩm
            calculatedTotalPrice += basePrice * quantity;
        }

        // Lấy tổng giá tiền hiển thị trên giao diện
        double displayedTotalPrice = getDisplayedTotalPrice();

        // So sánh hai giá trị
        Assert.assertEquals(calculatedTotalPrice, displayedTotalPrice, "Tổng giá tiền không khớp!");
        System.out.println("Tổng giá tiền tính toán: " + calculatedTotalPrice);
        System.out.println("Tổng giá tiền hiển thị: " + displayedTotalPrice);
    }
//* ========================== Interactive Element ========================================

    public void clickCartBtn() {
        wait.until(ExpectedConditions.elementToBeClickable(cartBtn)).click();
    }

    public void enterQuantity(int index, int qty) {
        getQuantityInputFieldByIndex(index).sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE, String.valueOf(qty));
    }

    public void clickDownQuantity(int index) {
        getQuantityInputFieldByIndex(index).findElement(By.xpath("(//button[contains(text(),'-')])"));
    }

    public void clickAddQuantity(int index) {
        getQuantityInputFieldByIndex(index).findElement(By.xpath("(//button[contains(text(),'+')])"));
    }

    public void acceptAlert() {
        alertnoty.acceptAlert();
    }
}
