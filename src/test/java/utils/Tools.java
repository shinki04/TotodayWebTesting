package utils;

import org.openqa.selenium.WebElement;

public class Tools {

    public String getValue(WebElement element, String arr){
//        return element.getAttribute(arr);
        return element.getDomAttribute(arr);
    }

    public String getText(WebElement element){
        return element.getText();
    }


    public  void setCheckboxState(WebElement element, boolean state){
        boolean isActualChecked = element.isSelected();
        if (state != isActualChecked) {
            element.click();
        }
    }


    public boolean getCheckboxState(WebElement element){
        return element.isSelected();
    }

//    public  WebElement getChildElement(String fatherElementXpath){
//        List<WebElement> elements = driver.findElements(By.xpath(fatherElementXpath));
//        for (WebElement element : elements){
//            return element;
//        }
//        return null;
//    }


    public String addPlusToString(String text){
        return text == null ? "" : text.trim().replace(" ","+");
    }


}
