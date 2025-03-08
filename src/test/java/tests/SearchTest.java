package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest {
    @BeforeClass
    private void setupClass(){
        driver.get("https://totoday.vn/search?q=Qu%E1%BA%A7n+");
    }

    @DataProvider(name = "categoryData")
    private Object[][] categoryData() {
        return new Object[][]{
                //        Áo khoác
                {"//div[@class='searchFolding']//a[contains(text(),'ÁO KHOÁC')]", "https://totoday.vn/ao-khoac-pc72908.html", "Áo khoác"},
                //        Đồ nam
                {"//div[@class='searchFolding']//a[contains(text(),'ĐỒ NAM')]", "https://totoday.vn/do-nam-pc72882.html", "Đồ Nam"},
                //        Đồ nữ
                {"//div[@class='searchFolding']//a[contains(text(),'ĐỒ NỮ')]", "https://totoday.vn/do-nu-pc72896.html", "Đồ nữ"},
                //        Unisex
                {"//div[@class='searchFolding']//a[contains(text(),'UNISEX')]", "https://totoday.vn/unisex-pc72920.html", "Unisex"},
                //        Phụ kiện
                {"//div[@class='searchFolding']//a[contains(text(),'PHỤ KIỆN')]", "https://totoday.vn/phu-kien-pc360511.html", "Phụ kiện"}
        };
    }

    @Test
    private void lmao() {
        System.out.println(driver.findElement(By.xpath("//div[@class='section-product-wrap']//a[@class='product-name'][1]")).getText());

    }


}
