package tests;

import base.BaseTest;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.AddCartPage;
import utils.FileReader;

import java.time.Duration;
import java.util.Map;

public class AddCartTest extends BaseTest {
    private AddCartPage addCartPage;

    @BeforeClass
    public void setupClass() {
        if (wait == null) {
            System.out.println("Warning: wait is null in setupClass");
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }
        addCartPage = new AddCartPage(driver, wait);
        driver.get("https://totoday.vn/ao-khoac-du-unisex-totoday-active-windbreaker-jacket-p37885995.html");
    }

    @BeforeMethod
    private void setupMethod() {
        driver.navigate().refresh();
    }

    @DataProvider(name = "productData")
    public Object[][] productData() {
                // Trường hợp vượt quá số lượng còn lại : true
                // Trường hợp không vượt quá số lượng còn lại : false
                // color, size, stock
               return FileReader.readDataFromExcel("src/test/resources/AddCartData.xlsx", "productData");

    }

    @Test(dataProvider = "productData")
    public void testAddToCart(Map<String,String> data) {

        String color = data.get("color");
        String size = data.get("size");
        boolean isExceed = Boolean.parseBoolean(data.get("isExceed"));
        // Chọn màu sắc
        addCartPage.clickColorByTitle(color);
        sleep(10);

        // Chọn kích cỡ
        addCartPage.clickSizeByDataValue(size);
        sleep(10);



        // Kiểm tra số lượng
        int qtymin = addCartPage.getMinQtyByDom();
        int qtymax = addCartPage.getMaxQtyByDom();
        System.out.println(qtymax + " " + qtymin);

        int qtyToEnter = isExceed ? qtymax + 1 : qtymin; // Nếu isExceed = true, nhập số lượng lớn hơn
        // Nhập số lượng
        addCartPage.enterQuantity(qtyToEnter);

//        sleep(50);
        // Kiểm tra kết quả
        if (isExceed) {
            // Mong đợi alert xuất hiện
            addCartPage.checkAlertPresent();
            String alertText = addCartPage.getAlertText();
            if (alertText.contains("Bạn chưa chọn màu sắc hoặc size hoặc sản phẩm tạm thời đang hết hàng ")) {
                tools.checkContainsMessage(alertText,"Bạn chưa chọn màu sắc hoặc size hoặc sản phẩm tạm thời đang hết hàng ");
            }
            tools.checkContainsMessage(alertText,"Bạn không thể đặt quá số lượng còn lại của sản phẩm !");
//            Assert.assertTrue(alertText.contains("Bạn không thể đặt quá số lượng còn lại của sản phẩm !"));
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

    @AfterClass
    public void cleanupClass() {
//        if (driver != null) quitDriver();
    }

//    @AfterMethod
//    public void cleanupMethod(){
//        if (driver != null) quitDriver();
//
//    }


}
