package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.DeleteFavoriteListPage;

public class DeleteFavoriteListTest extends BaseTest {
    private DeleteFavoriteListPage deleteFavoriteListPage;
    private final String EMAIL = "innologic25.team@gmail.com";
    private final String PASSWORD = "innologic2025";
    private String firstProductName;
    private String secondProductName;

    @BeforeClass
    public void setupClass() {
        deleteFavoriteListPage = new DeleteFavoriteListPage(driver, baseURL, notification);
        deleteFavoriteListPage.loginToAccount(EMAIL, PASSWORD);
        deleteFavoriteListPage.addTwoProductsToWishlist();
        deleteFavoriteListPage.goToWishlist();

        // Kiểm tra điều hướng đến wishlist thành công
        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = baseURL + "/wishlist";
        Assert.assertEquals(currentUrl, expectedUrl, "Không điều hướng đúng đến trang wishlist");

        // Lấy tên 2 sản phẩm trước khi xóa
        firstProductName = deleteFavoriteListPage.getFirstProductName();
        deleteFavoriteListPage.deleteFromWishlist();

        secondProductName = deleteFavoriteListPage.getFirstProductName();
    }

    @Test(priority = 1)
    public void testDeleteFirstProduct() {
        boolean isFirstStillInWishlist = deleteFavoriteListPage.isProductInWishlist(firstProductName);
        if (!isFirstStillInWishlist) {
            System.out.println("Đã xóa thành công sản phẩm đầu tiên: " + firstProductName);
        } else {
            System.out.println("Xóa sản phẩm đầu tiên thất bại: " + firstProductName);
        }
        Assert.assertFalse(isFirstStillInWishlist, "Sản phẩm đầu tiên vẫn còn trong danh sách yêu thích sau khi xóa");
    }

    @Test(priority = 2)
    public void testDeleteSecondProductAndCheckEmptyWishlist() {
        deleteFavoriteListPage.deleteFromWishlist();

        boolean isSecondStillInWishlist = deleteFavoriteListPage.isProductInWishlist(secondProductName);
        if (!isSecondStillInWishlist) {
            System.out.println("Đã xóa thành công sản phẩm thứ hai: " + secondProductName);
        } else {
            System.out.println("Xóa sản phẩm thứ hai thất bại: " + secondProductName);
        }
        Assert.assertFalse(isSecondStillInWishlist, "Sản phẩm thứ hai vẫn còn trong danh sách yêu thích sau khi xóa");
        sleep(2);

        // Load lại trang sau khi xóa sản phẩm thứ hai
        driver.navigate().refresh();
        sleep(5);

        // Kiểm tra danh sách yêu thích có trống không
        boolean isWishlistEmpty = deleteFavoriteListPage.isWishlistEmpty();
        if (isWishlistEmpty) {
            System.out.println("Danh sách yêu thích trống sau khi xóa tất cả sản phẩm.");
        } else {
            System.out.println("Danh sách yêu thích vẫn còn sản phẩm sau khi xóa tất cả sản phẩm.");
        }
        Assert.assertTrue(isWishlistEmpty, "Danh sách yêu thích vẫn còn sản phẩm, không hiển thị thông báo trống!");

        // Kiểm tra thông báo hiển thị khi wishlist trống
        String actualMessage = deleteFavoriteListPage.getEmptyWishlistMessage();
        String expectedMessage = "Danh sách quan tâm đang trống! Xem thêm sản phẩm tại đây";
        Assert.assertEquals(actualMessage, expectedMessage, "Thông báo khi wishlist trống không đúng!");
    }

    @AfterMethod
    public void cleanupSuite() {
//        deleteFavoriteListPage.clickLogoutBtn();
    }

}
