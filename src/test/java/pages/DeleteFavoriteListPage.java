package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.Notification;

import java.time.Duration;
import java.util.List;

import static config.DriverConfig.sleep;

public class DeleteFavoriteListPage {
    private final WebDriver driver;
    private final String baseURL;
    private final Notification notification;
    private final String loginURL;
    private final String addFavoriteListURL_1;
    private final String addFavoriteListURL_2;

    // Locators
    private final By signInEmailField = By.id("SignInEmail");
    private final By passwordField = By.id("password-field");
    private final By loginButton = By.xpath("//button[@type='submit'][contains(text(),'Đăng nhập')]");
    private final By addToWishlistButton = By.xpath("(//button[@class='wishlist-btn'])[1]");
    private final By deleteWishlistButton = By.xpath("//div[@class='col-md-8']//div[2]//div[2]//span[1]//i[1]");
    private final By productNameLocator = By.xpath("//p[@class='name']");
    private final By emptyWishlistMessage = By.xpath("//div[@class='wrap-right']");

    private By userButton = By.xpath("//img[@alt='Tài khoản']");
    private By logoutButton = By.xpath("//a[@href='/user/signout']");

    public DeleteFavoriteListPage(WebDriver driver, String baseURL, Notification notification) {
        this.driver = driver;
        this.baseURL = baseURL;
        this.notification = notification;
        this.loginURL = baseURL + "/user/signin";
        this.addFavoriteListURL_1 = baseURL + "/ao-khoac-du-nu-totoday-tri-color-block-p37882045.html";
        this.addFavoriteListURL_2 = baseURL + "/ao-hoodie-nam-totoday-good-manners-matters-p37881436.html";
    }

    private WebElement getElement(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (Exception e) {
            System.out.println("Không tìm thấy phần tử với locator: " + locator + ". Lỗi: " + e.getMessage());
            return null;
        }
    }

    public void navigateTo(String url) {
        try {
            driver.get(url);
            System.out.println("Đã điều hướng đến URL: " + url);
        } catch (Exception e) {
            System.out.println("Không thể điều hướng đến URL: " + url + ". Lỗi: " + e.getMessage());
        }
    }

    public void loginToAccount(String email, String password) {
        navigateTo(loginURL);
        WebElement emailField = getElement(signInEmailField);
        WebElement passField = getElement(passwordField);
        WebElement loginBtn = getElement(loginButton);

        if (emailField != null) emailField.sendKeys(email);
        if (passField != null) passField.sendKeys(password);
        if (loginBtn != null) loginBtn.click();

        if (notification.isAlertPresent()) {
            String alertText = notification.getAlertText();
            System.out.println("Thông báo sau đăng nhập: " + alertText);
            notification.acceptAlert();
        }
    }

    public void clickLogoutBtn() {
        sleep(2);
        driver.findElement(userButton).click();
        sleep(2);
        driver.findElement(logoutButton).click();
    }

    public void addToWishlist(String productUrl) {
        navigateTo(productUrl);
        WebElement addToWishlistButtonElement = getElement(addToWishlistButton);
        if (addToWishlistButtonElement != null) {
            addToWishlistButtonElement.click();
            System.out.println("Đã thêm sản phẩm vào wishlist từ URL: " + productUrl);
        }
    }

    public void addTwoProductsToWishlist() {
        addToWishlist(addFavoriteListURL_1);
        addToWishlist(addFavoriteListURL_2);
    }

    public void goToWishlist() {
        WebElement iconUser = getElement(By.xpath("//img[@alt='Tài khoản']"));
        if (iconUser != null) iconUser.click();

        WebElement iconWishlist = getElement(By.xpath("//div[@class='user']//li[5]//a[1]"));
        if (iconWishlist != null) iconWishlist.click();
    }

    public String getFirstProductName() {
        WebElement productElement = getElement(productNameLocator);
        return (productElement != null) ? productElement.getText().trim() : null;
    }

    public void deleteFromWishlist() {
        List<WebElement> deleteButtons = driver.findElements(deleteWishlistButton);
        if (!deleteButtons.isEmpty()) {
            String firstProductName = getFirstProductName();
            deleteButtons.get(0).click();
            System.out.println("Xoá sản phẩm" + firstProductName);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            driver.navigate().refresh();
        }
    }

    public boolean isProductInWishlist(String productName) {
        List<WebElement> remainingProducts = driver.findElements(productNameLocator);
        return remainingProducts.stream().anyMatch(e -> e.getText().trim().equals(productName));
    }

    public boolean isWishlistEmpty() {
        WebElement emptyMessageElement = getElement(emptyWishlistMessage);
        return emptyMessageElement != null && emptyMessageElement.getText().contains("Danh sách quan tâm đang trống!");
    }

    public String getEmptyWishlistMessage() {
        WebElement emptyMessageElement = getElement(emptyWishlistMessage);
        return (emptyMessageElement != null) ? emptyMessageElement.getText().trim() : null;
    }
}
