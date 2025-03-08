package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import utils.Tools;

public class SearchPage {

    private static Tools tools;
    private final WebDriver driver;

    //    Field
    private final By searchField = By.xpath("//input[@class='search-input']");
    private final By sectionProduct = By.xpath("//div[@class='section-product-wrap']");
    private final By productNameDetail = By.xpath("//div[@class='section-product-wrap']//a[@class='product-name'][1]");

    //    Message Error
    private final By messageNoProduct = By.xpath("//div[@class='no-product']");


    //    Constructor
    public SearchPage(WebDriver driver) {
        this.driver = driver;
        tools = new Tools(driver);
    }


    private WebElement getElement(By element) {
        try {
            return driver.findElement(element);
        } catch (NullPointerException nullEx) {
            Assert.fail("Element not found");
            return null;
        }
    }

    public void enterSearch(String searchValue) {
        getElement(searchField).sendKeys(searchValue);
    }

    public void submitSearch() {
        getElement(searchField).submit();
    }


    public void checkSearchFailed() {
        Assert.assertTrue(getElement(messageNoProduct).isDisplayed(), "Category was found");
        Assert.assertFalse(getElement(sectionProduct).isDisplayed(), "Category not found");
    }

    public void checkSearchSuccess() {
        Assert.assertFalse(getElement(messageNoProduct).isDisplayed(), "No product displayed");
        Assert.assertTrue(getElement(sectionProduct).isDisplayed(), "No product displayed");
    }

    public boolean checkContentEqualWithParentElementByXpath(By  parentElement, By childElement, String value) {
        WebElement child = getElement(parentElement).findElement(childElement);
        return child.isDisplayed() && child.getText().trim().toLowerCase().contains(value.toLowerCase());
    }

    public void searchProductMultiCase(String searchValue){
        enterSearch(searchValue);
        submitSearch();
    }


}
