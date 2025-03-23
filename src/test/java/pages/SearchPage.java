package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchPage {
    private final WebDriver driver;

    // Locators
    private final By searchField = By.xpath("//input[@class='search-input']");
    private final By sectionProduct = By.xpath("//div[@class='section-product-wrap']");
    private final By firstProductNameDetail = By.xpath("//div[@class='section-product-wrap']//a[@class='product-name'][1]");
    private final By messageNoProduct = By.xpath("//div[@class='no-product']");


    // Constructor
    public SearchPage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Helper method to get element
     */
    private WebElement getElement(By element) {
        try {
            return driver.findElement(element);
        } catch (Exception e) {
            return null;
        }
    }

    public WebElement getSearchFolding(String valueFolding) {
        return driver.findElement(By.xpath("//div[@class='searchFolding']//a[contains(text(),'" + valueFolding + "')]"));
    }

    // Actions
    public void enterSearch(String searchValue) {
        WebElement searchInput = getElement(searchField);
        if (searchInput != null) {
            searchInput.sendKeys(searchValue);
        }
    }

    public void submitSearch() {
        getElement(searchField).submit();

    }

    public void clickSearch() {
        getElement(searchField).click();
    }

    /**
     * Query Method
     *
     * @return boolean
     */
    public boolean isFirstProductDisplayed() {
        WebElement firstProduct = getElement(firstProductNameDetail);
        return firstProduct != null && firstProduct.isDisplayed();
    }




    public boolean isNoProductMessageDisplayed() {
        WebElement noProductMsg = getElement(messageNoProduct);
        return noProductMsg != null && noProductMsg.isDisplayed();
    }

    public boolean isProductSectionDisplayed() {
        WebElement productSection = getElement(sectionProduct);
        return productSection != null && productSection.isDisplayed();
    }

    public String getFirstProductName() {
        WebElement firstProduct = getElement(firstProductNameDetail);
        return (firstProduct != null) ? firstProduct.getText().trim() : "";
    }

}


