package tests;

import base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AddCartPage;
import pages.UpdateCartPage;

import java.time.Duration;
import java.util.List;

public class UpdateCartTest extends BaseTest {
    private AddCartPage addCartPage;
    private UpdateCartPage updateCartPage;

    @BeforeClass
    private void setupClass() {
        if (wait == null) {
            System.out.println("Warning: wait is null in setupClass");
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }
        addCartPage = new AddCartPage(driver, wait);
        updateCartPage = new UpdateCartPage(driver, wait);
        driver.get("https://totoday.vn/ao-khoac-du-unisex-totoday-active-windbreaker-jacket-p37885995.html");
    }

    @BeforeMethod
    private void setupMethod() {
        driver.navigate().refresh();
    }


    @DataProvider(name = "productData")
    public Object[][] productData() {
        return new Object[][]{
                // Trường hợp vượt quá số lượng còn lại : true
                // Trường hợp không vượt quá số lượng còn lại : false
                // color, size, stock
                {"BE", "276819", false},
                {"BE", "276821", false},
                {"BL", "276818", false},
                {"BL", "276821", false},
                {"BU", "276819", false},
        };
    }

    @DataProvider(name = "productUpdateData")
    public Object[][] productUpdateData() {
        return new Object[][]{
                // Trường hợp vượt quá số lượng còn lại : true
                // Trường hợp không vượt quá số lượng còn lại : false
                {true},
                {false},
                {true},

        };
    }
    //TODO Chạy trước để thêm sản phẩm vào giỏ hàng, chuẩn bị cho testUpdateCard
    @Test(dataProvider = "productData", priority = 0)
    public void testAddToCart(String color, String size, boolean isExceed) {
        // Chọn màu sắc
        addCartPage.clickColorByTitle(color);
        sleep(10);

        // Chọn kích cỡ
        addCartPage.clickSizeByDataValue(size);
        sleep(10);

        int qtymin = addCartPage.getMinQtyByDom();
        int qtymax = addCartPage.getMaxQtyByDom();
        System.out.println(qtymax + " " + qtymin);

        int qtyToEnter = isExceed ? qtymax + 1 : qtymin; // Nếu isExceed = true, nhập số lượng lớn hơn
        addCartPage.enterQuantity(qtyToEnter);
        if (isExceed) {
            // Mong đợi alert xuất hiện
            addCartPage.checkAlertPresent();
            String alertText = addCartPage.getAlertText();
            System.out.println();
            Assert.assertTrue(alertText.contains("Bạn không thể đặt quá số lượng còn lại của sản phẩm !"));
            addCartPage.acceptAlert(); // Đóng alert
        } else {
            // Mong đợi không có alert và hiển thị modal thành công
            if (addCartPage.checkAlertPresent()) {
                addCartPage.acceptAlert();
                Assert.fail("Không mong đợi alert xuất hiện");
            } else {
                // Không có alert, kiểm tra modal thành công
                // Nhấn nút "Thêm vào giỏ hàng" bằng Actions
                addCartPage.clickAddBtn();

                sleep(10);
                Assert.assertTrue(addCartPage.checkNotificationAddSuccessDisplayed(), "The product not add to cart successfully");
                Assert.assertTrue(addCartPage.getNotificationAddSuccessMessage().contains("vào giỏ hàng"));
            }
        }
    }

    @Test(priority = 1, dataProvider = "productUpdateData")
    private void testUpdateCard(boolean isExceed) {
        updateCartPage.clickCartBtn();
        sleep(10);

        // Đếm số lượng sản phẩm trong giỏ hàng
        List<WebElement> cartItems = updateCartPage.getCartItems();
        int itemCount = cartItems.size();
        sleep(10);

        System.out.println("Số sản phẩm trong giỏ hàng: " + itemCount);

        boolean alertAppeared = false;
        for (int i = 1; i <= itemCount; i++) {
            int min = updateCartPage.getMinValue(i);
            int max = updateCartPage.getMaxValue(i);
            int targetQty = isExceed ? (max + 1) : (max - 1);
            sleep(5);
            updateCartPage.enterQuantity(i, targetQty);

            if (isExceed) {
                if (updateCartPage.checkAlertPresent()) {
                    alertAppeared = true; // Đánh dấu alert đã xuất hiện
                    String actualAlertText = updateCartPage.getTextAlert().trim();
                    String expectedAlertText = updateCartPage.outStuckMessage;
                    System.out.println("Alert nhận được: " + actualAlertText); // Debug
                    Assert.assertEquals(actualAlertText, expectedAlertText, "Thông báo không khớp!");
                    updateCartPage.acceptAlert();
                }
            } else {
                sleep(5);
                updateCartPage.verifyTotalPrice();
            }
        }

        if (isExceed && !alertAppeared) {
            Assert.fail("Không có alert xuất hiện khi vượt quá số lượng!");
        }
    }



}
