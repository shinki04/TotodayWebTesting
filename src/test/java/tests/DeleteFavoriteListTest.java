package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.DeleteFavoriteListPage;

import java.time.Duration;

public class DeleteFavoriteListTest extends BaseTest {
    private DeleteFavoriteListPage deleteFavoriteListPage;
    private final String loginURL = baseURL + "/user/signin";
    private final String PRODUCT_PAGE_URL = "https://totoday.vn/product?show=new"; // Trang sản phẩm mới
    private final String wishlistURL = baseURL + "/wishlist"; // Trang wishlist
    private final String PRODUCT_ID = "37886237"; // ID sản phẩm để kiểm tra
    private final String PSID = "37886237"; // PSID của sản phẩm cần thêm và xóa
    private final String EMAIL = "innologic25.team@gmail.com";
    private final String PASSWORD = "innologic2025";
    private final By PRODUCT_WITH_PSID = By.cssSelector("[psid='" + PSID + "']"); // Tìm phần tử với psid
    private final By ADD_TO_FAVORITE_ICON = By.cssSelector(".far.fa-heart"); // Icon thêm yêu thích
    private final By REMOVE_FROM_WISHLIST_ICON = By.cssSelector("[data-id='" + PRODUCT_ID + "'] .far.fa-heart"); // Icon xóa từ wishlist
    private final By WISHLIST_ITEM = By.cssSelector("[data-id='" + PRODUCT_ID + "']"); // Kiểm tra sản phẩm trong wishlist

    @BeforeClass
    private void setupClass() {
        deleteFavoriteListPage = new DeleteFavoriteListPage(driver, baseURL, notification);
    }

    @AfterMethod
    private void cleanupTest() {
        // Không tự động quay lại baseURL hoặc đóng trình duyệt
    }

    @Test(priority = 0)
    public void testAddAndDeleteFavoriteItem() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Chờ tối đa 10 giây

        // Đăng nhập
        deleteFavoriteListPage.loginToAccount(loginURL, EMAIL, PASSWORD);
        System.out.println("Đăng nhập thành công");

        // Chuyển đến trang sản phẩm mới
        deleteFavoriteListPage.navigateTo(PRODUCT_PAGE_URL);
        wait.until(ExpectedConditions.urlContains("product?show=new")); // Chờ trang tải
        System.out.println("Đã chuyển đến trang sản phẩm mới");

        // Tìm phần tử với psid="37886237" và click vào thẻ <i class="far fa-heart">
        try {
            WebElement productElement = wait.until(ExpectedConditions.presenceOfElementLocated(PRODUCT_WITH_PSID));
            if (productElement != null) {
                WebElement addToFavoriteIcon = productElement.findElement(ADD_TO_FAVORITE_ICON);
                if (addToFavoriteIcon != null && addToFavoriteIcon.isDisplayed()) {
                    addToFavoriteIcon.click();
                    System.out.println("Đã thêm sản phẩm với psid=" + PSID + " vào danh sách yêu thích");
                } else {
                    System.out.println("Không tìm thấy icon <i class='far fa-heart'> trong sản phẩm với psid=" + PSID);
                    Assert.fail("Không thể click nút thêm vào danh sách yêu thích");
                }
            } else {
                System.out.println("Không tìm thấy sản phẩm với psid=" + PSID);
                Assert.fail("Không tìm thấy sản phẩm để thêm vào danh sách yêu thích");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Lỗi: Không tìm thấy phần tử với psid=" + PSID + " hoặc icon <i class='far fa-heart'>. Chi tiết lỗi: " + e.getMessage());
            Assert.fail("Không thể thêm sản phẩm vào danh sách yêu thích do không tìm thấy phần tử");
        }
        // Chuyển hướng đến wishlist
        deleteFavoriteListPage.navigateTo(wishlistURL);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".wishlist-wrap"))); // Chờ wishlist tải
        System.out.println("Đã chuyển đến wishlist");

        // Kiểm tra sản phẩm có tồn tại trước khi xóa
        boolean productExistsBefore = deleteFavoriteListPage.isProductExist(PRODUCT_ID);
        if (!productExistsBefore) {
            System.out.println("Sản phẩm với data-id " + PRODUCT_ID + " không tồn tại trong wishlist");
            Assert.fail("Không tìm thấy sản phẩm để xóa sau khi thêm");
            return;
        }

        // Tìm phần tử với data-id="37886237" và click vào thẻ <i class="far fa-heart"> để xóa
        try {
            WebElement wishlistItem = wait.until(ExpectedConditions.presenceOfElementLocated(WISHLIST_ITEM));
            if (wishlistItem != null) {
                WebElement removeFromWishlistIcon = wishlistItem.findElement(REMOVE_FROM_WISHLIST_ICON);
                if (removeFromWishlistIcon != null && removeFromWishlistIcon.isDisplayed()) {
                    removeFromWishlistIcon.click();
                    System.out.println("Đã xóa sản phẩm với data-id=" + PRODUCT_ID + " khỏi danh sách yêu thích");
                } else {
                    System.out.println("Không tìm thấy icon <i class='far fa-heart'> trong sản phẩm với data-id=" + PRODUCT_ID);
                    Assert.fail("Không thể click nút xóa khỏi danh sách yêu thích");
                }
            } else {
                System.out.println("Không tìm thấy sản phẩm với data-id=" + PRODUCT_ID + " trong wishlist");
                Assert.fail("Không tìm thấy sản phẩm để xóa");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Lỗi: Không tìm thấy phần tử với data-id=" + PRODUCT_ID + " hoặc icon <i class='far fa-heart'>. Chi tiết lỗi: " + e.getMessage());
            Assert.fail("Không thể xóa sản phẩm khỏi danh sách yêu thích do không tìm thấy phần tử");
        }
        // Kiểm tra kết quả
        boolean productExistsAfter = deleteFavoriteListPage.isProductExist(PRODUCT_ID);
        String successMessage = deleteFavoriteListPage.getSuccessMessageText();

        if (!productExistsAfter) {
            System.out.println("Xóa sản phẩm thành công: data-id " + PRODUCT_ID + " không còn tồn tại");
            Assert.assertTrue(deleteFavoriteListPage.isSuccessMessageDisplayed(),
                    "Thông báo thành công không hiển thị");
            Assert.assertEquals(successMessage, "Xóa sản phẩm khỏi danh sách yêu thích thành công",
                    "Thông báo thành công không khớp");
        } else {
            System.out.println("Xóa sản phẩm thất bại: data-id " + PRODUCT_ID + " vẫn còn tồn tại");
            Assert.fail("Xóa sản phẩm thất bại");
        }
    }
}