package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Objects;
import java.util.UUID;


public class Tools {

    private final WebDriver driver;

    public Tools(WebDriver driver){
        this.driver = driver;
    }

    public String getValue(WebElement element, String arr) {
//        return element.getAttribute(arr);
        return element.getDomAttribute(arr);
    }

    public String getText(WebElement element) {
        return element.getText();
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

    public WebElement getElementChildByXpath( WebElement parentElement,String xpath) {

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


}
