package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.ZoomPage;

import java.awt.*;
import java.time.Duration;

public class ZoomTest extends BaseTest {
    private ZoomPage zoomPage;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    @BeforeClass
    void setupClass() throws AWTException {
        driver = getDriver();
        zoomPage = new ZoomPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
    }

    @AfterClass
    void cleanupClass() {
        quitDriver();
    }

    @BeforeMethod
    void setupMethod() {
        driver.get(baseURL);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/main[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/main[1]/div[2]/div[1]/div[2]/div[1]/a[1]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='col']"))).click();
        zoomPage.resetZoom();
    }

    @Test
    public void testZoomIn() {
        zoomPage.zoomIn();
        System.out.println("Zoom In toàn trang thực hiện thành công!");
        zoomPage.resetZoom();
    }

    @Test
    public void testZoomOut() {
        zoomPage.zoomOut();
        System.out.println("Zoom Out toàn trang thực hiện thành công!");
        zoomPage.resetZoom();
    }

    @Test
    public void testResetZoom() {
        zoomPage.resetZoom();
        System.out.println("Reset Zoom toàn trang thực hiện thành công!");
    }

}