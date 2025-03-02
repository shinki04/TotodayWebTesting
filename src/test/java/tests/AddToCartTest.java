package tests;

import base.BaseTest;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class AddToCartTest extends BaseTest {
    private WebDriverWait wait;

//    @BeforeClass
//    public void setupClass() {
////        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
////        driver.navigate().to("https://totoday.vn/tui-xach-totoday-02508-p37887460.html");
//        driver.navigate().to("https://totoday.vn/quan-short-kaki-nam-totoday-basic-chinos-short-p37881600.html");
//    }
    @BeforeMethod
    private void setupMethod(){
        driver = getDriver();
        actions = new Actions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://totoday.vn/ao-khoac-du-unisex-totoday-active-windbreaker-jacket-p37885995.html");

    }

//    @Test
//    public void testAddToCartFromDetailPage() {
//        // Chọn màu sắc (giả sử màu đen đã được chọn mặc định, data-pids="37887461")
//        WebElement colorOption = driver.findElement(By.cssSelector("a[data-pids='37887461']"));
//        if (!colorOption.getAttribute("class").contains("active")) {
//            colorOption.click();
//            wait.until(ExpectedConditions.attributeContains(colorOption, "class", "active"));
//        }
//
//        // Nhập số lượng
//        WebElement quantityInput = driver.findElement(By.id("qty"));
//        quantityInput.clear();
//        quantityInput.sendKeys("2");
//        sleep(5);
//        Alert alertNotEnough = driver.switchTo().alert();
//        System.out.println("Allert" + alertNotEnough.getText());
//
//        // Click nút "Thêm giỏ hàng"
//        WebElement addToCartButton = driver.findElement(By.id("addToCart"));
//        addToCartButton.click();
//
//        // Chuyển đến trang giỏ hàng (giả sử có link đến giỏ hàng, cần tùy chỉnh theo thực tế)
//        WebElement cartLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("cart-link")));
//        cartLink.click();
//
//        // Kiểm tra sản phẩm trong giỏ hàng
//        WebElement cartItem = wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.cssSelector("div.cart-item[data-pid='37887460']")
//        ));
//        String quantityInCart = cartItem.findElement(By.cssSelector("input.qty")).getAttribute("value");
//
//        Assert.assertNotNull(cartItem, "Sản phẩm không được thêm vào giỏ hàng!");
//        Assert.assertEquals(quantityInCart, "2", "Số lượng trong giỏ hàng không đúng!");
//    }


//    @Test
//    public void testExceedStockAlert() {
//        // Hiển thị block .btn-showroom nếu nó bị ẩn (giả sử cần click để hiển thị)
//        WebElement showroomBlock = driver.findElement(By.xpath("//div[@class='btn-showroom']"));
////        if (!showroomBlock.isDisplayed()) {
////            // Giả định có một nút hoặc hành động để hiển thị block này
////            driver.findElement(By.cssSelector(".pview-store")).click(); // Điều chỉnh locator nếu cần
////            wait.until(ExpectedConditions.visibilityOf(showroomBlock));
////        }
//
//        // Lấy phần tử <li> đầu tiên trong .btn-showroom
//        WebElement firstStoreItem = driver.findElement(By.cssSelector(".btn-showroom ul li:nth-child(1)"));
//        WebElement stockElement = firstStoreItem.findElement(By.className("quarity-active"));
//
//        // Lấy số lượng tồn kho từ thẻ <b> trong .quarity-active
//        String stockText = stockElement.findElement(By.tagName("b")).getText();
//        int stockQuantity = Integer.parseInt(stockText);
//
//
//        // Nhập số lượng vượt quá tồn kho
//        WebElement quantityInput = driver.findElement(By.id("qty"));
//        quantityInput.clear();
//        int exceedQuantity = stockQuantity + 1; // Ví dụ: nếu tồn kho là 2, nhập 3
//        quantityInput.sendKeys(String.valueOf(exceedQuantity));
//        // Click nút "Thêm giỏ hàng"
////        WebElement addToCartButton = driver.findElement(By.id("addToCart"));
////        addToCartButton.click();
////        sleep(10);
//
//        // Chờ và kiểm tra alert
//        try {
//            wait.until(ExpectedConditions.alertIsPresent());
//            String alertText = driver.switchTo().alert().getText();
//            driver.switchTo().alert().accept(); // Đóng alert
//
//            // Kiểm tra nội dung alert (giả định thông báo là "Số lượng vượt quá tồn kho")
//            Assert.assertTrue(alertText.contains("Bạn không thể đặt quá số lượng còn lại của sản phẩm !"),
//                              "Alert không hiển thị thông báo lỗi về tồn kho!");
//        } catch (Exception e) {
//            Assert.fail("Không có alert xuất hiện khi vượt quá số lượng tồn kho!");
//        }
//    }


    @DataProvider(name = "productData")
    public Object[][] productData() {
        return new Object[][] {
                {"BE", "276818", true},  // Trường hợp vượt quá số lượng còn lại
                {"BE", "276820", false}, // Trường hợp không vượt quá số lượng còn lại
                {"BL", "276821", false}
        };
    }

    @Test(dataProvider = "productData")
    public void testAddToCart(String color, String size, boolean isExceed) {
        // Chọn màu sắc
        WebElement colorElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("div.colorPicker.clearfix a[title='" + color + "']")));
        actions.moveToElement(colorElement).click().perform();
        sleep(10);

        // Chọn kích cỡ
        WebElement sizeElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("div.sizePicker.clearfix a[data-value='" + size + "']")));
        sleep(10);

//        wait.until(ExpectedConditions.attributeToBe(By.cssSelector("div.sizePicker.clearfix a[data-value='" + size + "']"), "class", "deactive"));
//        System.out.println(sizeElement.getAttribute("class"));
        actions.moveToElement(sizeElement).click().perform();
        sleep(10);
//        // Lấy số lượng còn lại
//        WebElement stockElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//div[@class='blockShip tp_product_detail_depot']//span[@class='quarity-active']/b[1]")));
//        int stock = Integer.parseInt(stockElement.getText());

        // Nhập số lượng
        WebElement qtyInput = driver.findElement(By.id("qty"));
        String qtymin = qtyInput.getDomAttribute("min");
        int qtymax = Integer.parseInt(qtyInput.getDomAttribute("max"));
        System.out.println(qtymax + qtymin);
        int qtyToEnter = isExceed ? qtymax + 1 : qtymax - 1; // Nếu isExceed = true, nhập số lượng lớn hơn
        qtyInput.clear();
        qtyInput.sendKeys(String.valueOf(qtyToEnter));

        sleep(50);
        // Kiểm tra kết quả
        if (isExceed) {
            // Mong đợi alert xuất hiện
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            Assert.assertTrue(alertText.contentEquals( "Bạn không thể đặt quá số lượng còn lại của sản phẩm !"));
            alert.accept(); // Đóng alert
        } else {
            // Mong đợi không có alert và hiển thị modal thành công
            try {
                wait.until(ExpectedConditions.alertIsPresent());
                Assert.fail("Không mong đợi alert xuất hiện");
            } catch (TimeoutException e) {
                // Không có alert, kiểm tra modal thành công
                // Nhấn nút "Thêm vào giỏ hàng" bằng Actions
                WebElement addToCartBtn = driver.findElement(By.id("addToCart"));
                addToCartBtn.click();
                WebElement modelAddSuccess = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector(".modal-add-success.active")));
                sleep(10);
                Assert.assertTrue(modelAddSuccess.isDisplayed(),"Add to cart not done");
            }

        }
    }
    @AfterClass
    public void cleanupClass(){
        if (driver != null) quitDriver();
    }

//    @AfterMethod
//    public void cleanupMethod(){
//        if (driver != null) quitDriver();
//
//    }


}
