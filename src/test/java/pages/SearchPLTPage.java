package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchPLTPage {
    private final WebDriver driver;

    // XPaths được định nghĩa từ yêu cầu
    private final By searchInput = By.xpath("(//input[@id='filter_name'])[1]");
    private final By searchButton = By.xpath("(//button[@type='submit'])[1]");
    private final By searchKeywordResult = By.xpath("(//h1[contains(text(),'Tìm kiếm từ khóa:')])[1]");
    private final By productsCategory = By.xpath("(//div[@class='row products-category'])[1]");
    private final By error404Message = By.xpath("(//h1[@class='title'])[1]");

    // Constructor
    public SearchPLTPage(WebDriver driver) {
        this.driver = driver;
    }

    // Nhập từ khóa vào ô tìm kiếm
    public void enterSearchKeyword(String keyword) {
        WebElement searchField = driver.findElement(searchInput);
        searchField.clear();
        searchField.sendKeys(keyword);
    }

    // Nhấn nút tìm kiếm
    public void clickSearchButton() {
        driver.findElement(searchButton).click();
    }

    // Lấy văn bản từ tiêu đề kết quả tìm kiếm
    public String getSearchKeywordResultText() {
        return driver.findElement(searchKeywordResult).getText();
    }

    // Lấy văn bản từ danh sách sản phẩm
    public String getProductsCategoryText() {
        return driver.findElement(productsCategory).getText();
    }

    // Lấy văn bản từ thông báo lỗi 404
    public String getError404MessageText() {
        return driver.findElement(error404Message).getText();
    }
}
