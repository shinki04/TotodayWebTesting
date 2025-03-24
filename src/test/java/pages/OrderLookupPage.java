package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.Tools;

public class OrderLookupPage {
    private final WebDriver driver;
    private final Tools tools;

    // Locators
    private final By orderLookupLink = By.linkText("Tra cứu đơn hàng");
    private final By orderSearchInput = By.xpath("(//input[@placeholder='Nhập số điện thoại hoặc Mã số đơn hàng'])[1]");
    private final By messageNoOrder = By.xpath("/html[1]/body[1]/div[5]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/h5[1]");

    public OrderLookupPage(WebDriver driver) {
        this.driver = driver;
        this.tools = new Tools(driver);
    }

    public void navigateToOrderLookup() {
        driver.findElement(orderLookupLink).click();
    }

    public void enterOrderSearch(String searchValue) {
        driver.findElement(orderSearchInput).sendKeys(searchValue);
    }

    public void submitOrderSearch() {
        driver.findElement(orderSearchInput).submit();
    }

    public String getNoOrderMessage() {
        return driver.findElement(messageNoOrder).getText();
    }

    public boolean isUrlContainsSearchQuery(String baseURL) {
        return driver.getCurrentUrl().contains(baseURL + "order/search?q=");
    }
}