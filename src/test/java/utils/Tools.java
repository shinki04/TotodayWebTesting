package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;


public class Tools {

    private final WebDriver driver;
    private final JavascriptExecutor js;
    private final WebDriverWait wait;

    public Tools(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        js = (JavascriptExecutor) this.driver;

    }

    public String getValue(WebElement element, String arr) {
//        return element.getAttribute(arr);
        return element.getDomAttribute(arr);
    }

    public String getText(WebElement element) {
        return element.getText();
    }

    public WebElement getElement(By byElement) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(byElement));
    }

    public void setCheckboxState(WebElement element, boolean state) {
        boolean isActualChecked = element.isSelected();
        if (state != isActualChecked) {
            element.click();
        }
    }


    public boolean getCheckboxState(WebElement element) {
        return element.isSelected();
    }

//    public  WebElement getChildElement(String fatherElementXpath){
//        List<WebElement> elements = driver.findElements(By.xpath(fatherElementXpath));
//        for (WebElement element : elements){
//            return element;
//        }
//        return null;
//    }


    public String addPlusToString(String text) {
        return Objects.equals(text, "") ? "" : text.trim().replace(" ", "+");
    }

    public WebElement getElementByXpath(String xpath) {

        try {
            return driver.findElement(By.xpath(xpath));
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public WebElement getElementChildByXpath(WebElement parentElement, String xpath) {
        try {
            return parentElement.findElement(By.xpath(xpath));
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public boolean checkElementIsDisplayed(WebElement element) {
        if (element == null) {
            return false;
        } else {
            return element.isDisplayed();
        }
    }


    public boolean addDisplayBlockCSS(WebElement element) {
        if (checkElementIsDisplayed(element)) {
            js.executeScript("arguments[0].style.display = 'block'", element);
            return true;
        }
        return false;
    }

    public boolean checkOptionSelectedByClass(WebElement element) {
        try {
            String className = element.getAttribute("class");
            return className.contains("selected") || className.contains("active");

        } catch (NoSuchElementException e) {
            return false;
        }

    }


//    public WebElement checkTestElementNotDisplayed(WebElement element){
//        try {
//            if (!element.isDisplayed()) {
//                return element;
//            }
//        } catch (NoSuchElementException e) {
//            return null;
//        }
//        return null;
//    }

    public String generateRandomString(int length) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", ""); // Loại bỏ dấu "-"
        return uuid.substring(0, Math.min(length, uuid.length()));
    }

    public String generateRandomNumber(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10)); // ngẫu nhiên 0-9
        }
        return sb.toString();
    }

    public String rgbaToHex(String rgba) {
        rgba = rgba.replace("rgba(", "").replace(")", "");
        String[] values = rgba.split(", ");

        int r = Integer.parseInt(values[0]);
        int g = Integer.parseInt(values[1]);
        int b = Integer.parseInt(values[2]);

        return String.format("#%02x%02x%02x", r, g, b).toUpperCase();
    }

    // Chuyển từ HEX sang RGB
    public String hexToRgb(String hex) {
        hex = hex.replace("#", "");
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);

        return String.format("rgb(%d, %d, %d)", r, g, b);
    }


    public void checkErrorMessage(WebElement errorElement, String errorMessage) {
        System.out.println("==========================================");
        System.out.println("Check Error : ");
        System.out.println("Actual : " + errorElement.getText());
        System.out.println("Expect : " + errorMessage);
        Assert.assertEquals(errorElement.getText(), errorMessage, "Error Message not equal");
        System.out.println("==========================================");
    }


}
