package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Tools;

import java.time.Duration;

public class AddLikeListPage {
    private final WebDriver driver;
    private static Tools tools;

    // Các locator (định vị phần tử) trên trang
    private final By likeButton = By.xpath("/html[1]/body[1]/section[1]/div[1]/div[1]/div[1]/div[1]/div[1]/button[1]");
    private final By userIcon = By.xpath("(//img[@alt='Tài khoản'])[1]");
    private final By likeListButton = By.xpath("(//a[@href='/wishlist'])[1]");
    private final By productTitle = By.xpath("//h1[contains(text(),'ÁO HOODIE NAM')]");
    private final By logoutButton = By.xpath("(//a[@href='/user/signout'])[1]");
    private final By emailField = By.xpath("(//input[@id='SignInEmail'])[1]"); // XPath cho trường email
    private final By passwordField = By.xpath("(//input[@id='password-field'])[1]"); // XPath cho trường mật khẩu
    private final By loginButton = By.xpath("(//button[@type='submit'][contains(text(),'Đăng nhập')])[1]"); // XPath cho nút đăng nhập
    // XPath mới cho biểu tượng trước khi thích (dựa trên querySelector của bạn)
    private final By notLikedIcon = By.xpath("//div[@class='swiper-container swiper-init swiper-container-initialized swiper-container-horizontal']//i[@class='far fa-heart']");
    // XPath cho biểu tượng sau khi thích (giả định là class đổi thành 'fas fa-heart')
    private final By likedIcon = By.xpath("//div[@class='swiper-container swiper-init swiper-container-initialized swiper-container-horizontal']//i[@class='fas fa-heart']");
    // XPath cho tên sản phẩm trên trang chi tiết sản phẩm
    private final By productNameOnDetailPage = By.xpath("(//h1[normalize-space()='ÁO HOODIE NAM - TOTODAY - GOOD MANNERS MATTERS'])[1]");
    // XPath cho tên sản phẩm trong danh sách yêu thích (giả định, cần cập nhật nếu khác)
    private final By productNameInLikeList = By.xpath("(//p[@class='name'])[1]");

    // Constructor
    public AddLikeListPage(WebDriver driver , WebDriverWait wait) {
        this.driver = driver;
        tools = new Tools(driver,wait);
    }

    // Phương thức đăng nhập
    public void login(String email, String password) {
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();

        // Kiểm tra nếu có alert xuất hiện
        if (isAlertPresent()) {
            String actualMessage = getAlertText();
            System.out.println("Thông báo từ hệ thống: " + actualMessage);
            acceptAlert(); // Đóng thông báo nếu có
        }
    }

    // Phương thức kiểm tra xem có alert xuất hiện hay không
    private boolean isAlertPresent() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.alertIsPresent());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Phương thức lấy nội dung của alert
    private String getAlertText() {
        Alert alert = driver.switchTo().alert();
        return alert.getText();
    }

    // Phương thức đóng alert
    private void acceptAlert() {
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    // Phương thức nhấn nút "Thích" (Like)
    public void clickLikeButton() {
        driver.findElement(likeButton).click();
    }

    // Phương thức nhấn biểu tượng người dùng (User Icon)
    public void clickUserIcon() {
        driver.findElement(userIcon).click();
    }

    // Phương thức mở danh sách yêu thích (Like List)
    public void openLikeList() {
        clickUserIcon(); // Nhấn vào User Icon để hiển thị menu
        driver.findElement(likeListButton).click(); // Sau đó nhấn vào nút Like List
    }

    // Phương thức kiểm tra sản phẩm có trong danh sách yêu thích hay không
    public boolean isProductInLikeList() {
        return driver.findElements(productTitle).size() > 0;
    }

    // Phương thức đăng xuất
    public void logout() {
        clickUserIcon();
        driver.findElement(logoutButton).click();
    }

    // Phương thức kiểm tra xem trang hiện tại có phải là trang đăng nhập không
    public boolean isOnLoginPage() {
        return driver.getCurrentUrl().contains("signin");
    }

    // Phương thức kiểm tra xem có chuyển hướng đến trang đăng nhập chính xác hay không
    public boolean isRedirectedToLoginPage() {
        return driver.getCurrentUrl().equals("https://totoday.vn/user/signin");
    }

    // Phương thức chờ biểu tượng "liked" xuất hiện
    public boolean waitForLikedIcon() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(likedIcon));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Phương thức kiểm tra biểu tượng "not liked" có tồn tại không
    public boolean isNotLikedIconPresent() {
        return driver.findElements(notLikedIcon).size() > 0;
    }

    // Phương thức lấy tên sản phẩm từ trang chi tiết sản phẩm
    public String getProductNameOnDetailPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement productNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(productNameOnDetailPage));
        return productNameElement.getText().trim();
    }

    // Phương thức lấy tên sản phẩm từ danh sách yêu thích
    public String getProductNameInLikeList() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement productNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@class='name']")));
        return productNameElement.getText().trim();
    }
}