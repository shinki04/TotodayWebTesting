package tests;

import config.DriverConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.Notification;
import utils.PopupHandler;
import utils.Tools;

public class OrderLookupTest extends DriverConfig {



    private static Tools tools;
    private WebElement orderSearchInput;
    private WebDriver driver;
    private WebElement messageNoOrder;

    @BeforeTest
    void setupTest() {


    }


    @AfterClass
    void cleanupClass() {
        quitDriver();
    }


    @BeforeMethod
    void setupMethod() {
        driver.findElement(By.linkText("Tra cứu đơn hàng")).click();
        orderSearchInput = driver.findElement(By.xpath("(//input[@placeholder='Nhập số điện thoại hoặc Mã số đơn hàng'])[1]"));
    }


    @Test()
    void testOrderSearchFailed() {
        String orderSearchItem = "0708712413";
        orderSearchInput.sendKeys(orderSearchItem);
        orderSearchInput.submit();
        if (driver.getCurrentUrl().contains(baseURL + "order/search?q=")) {
            System.out.println("Search input have change");
        }
        sleep(5);

        WebElement messageNoOrder = tools.getElementByXpath("/html[1]/body[1]/div[5]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/h5[1]");
        Assert.assertEquals(messageNoOrder.getText(),"Không tìm thấy đơn hàng theo yêu cầu");
        sleep(5);

    }



    @Test()
    void testOrderSearchWithItestOrderSearchWithSpecialCharactersncorrectKeyword() {
        String orderSearchItem = "\"@b$$^";
        orderSearchInput.sendKeys(orderSearchItem);
        orderSearchInput.submit();
        if (driver.getCurrentUrl().contains(baseURL + "order/search?q=")) {
            System.out.println("Search input have change");
        }
        sleep(5);

        WebElement messageNoOrder = tools.getElementByXpath("/html[1]/body[1]/div[5]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/h5[1]");
        Assert.assertEquals(messageNoOrder.getText(),"Không tìm thấy đơn hàng theo yêu cầu");
        sleep(5);


    }

    @Test()
    void testOrderSearchWithLeadingTrailingSpaces() {
        String orderSearchItem = "    0708712413     ";
        orderSearchInput.sendKeys(orderSearchItem);
        orderSearchInput.submit();
        if (driver.getCurrentUrl().contains(baseURL + "order/search?q=")) {
            System.out.println("Search input have change");
        }
        sleep(5);

        WebElement messageNoOrder = tools.getElementByXpath("/html[1]/body[1]/div[5]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/h5[1]");
        Assert.assertEquals(messageNoOrder.getText(),"Không tìm thấy đơn hàng theo yêu cầu");
        sleep(5);

    }

    @Test()
    void testOrderSearchWithEmptyKeyword() {
        String orderSearchItem = "";
        orderSearchInput.sendKeys(orderSearchItem);
        orderSearchInput.submit();
        if (driver.getCurrentUrl().contains(baseURL + "order/search?q=")) {
            System.out.println("Search input have change");
        }
        sleep(5);

        WebElement messageNoOrder = tools.getElementByXpath("/html[1]/body[1]/div[5]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/h5[1]");
        Assert.assertEquals(messageNoOrder.getText(),"Không tìm thấy đơn hàng theo yêu cầu");
        sleep(5);
    }

    @Test()
    void testOrderSearchWithMaxLengthKeyword() {
        String orderSearchItem = tools.generateRandomString(256);
        System.out.println(orderSearchItem);
        orderSearchInput.sendKeys(orderSearchItem);
        orderSearchInput.submit();
        if (driver.getCurrentUrl().contains(baseURL + "order/search?q=")) {
            System.out.println("Search input have change");
        }
        sleep(5);

        WebElement messageNoOrder = tools.getElementByXpath("/html[1]/body[1]/div[5]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/h5[1]");
        Assert.assertEquals(messageNoOrder.getText(),"Không tìm thấy đơn hàng theo yêu cầu");
        sleep(5);
    }


}
