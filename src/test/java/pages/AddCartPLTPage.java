package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class AddCartPLTPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By addCartBtn = By.xpath("//body[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[3]/a[1]");
    private final By confirmBtn = By.xpath("//button[contains(text(),'Hoàn tất đặt hàng')]");

    private final By nameInputField = By.xpath("//input[@name='Name']");
    private final By phoneInputField = By.xpath("//input[@name='Phone']");
    private final By emailInputField = By.xpath("//input[@name='Email']");
    private final By addressInputField = By.xpath("//input[@name='Address']");
    private final By noteInputField = By.xpath("//textarea[@name='Note']");

    private final By alertErrorDiv = By.xpath("//div[@class='alert alert-danger']");
    //    private final By nameErrorMessage = By.xpath("//div[@class='alert alert-danger']//li[1]");
//    private final By phoneErrorMessage = By.xpath("//div[@class='alert alert-danger']//li[2]");
    private final By errorMessages = By.xpath("//div[@class='alert alert-danger']//ul//li");

    // Constructor
    public AddCartPLTPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;

    }


    // Lấy danh sách các thông báo lỗi từ các thẻ <li>
    public List<String> getErrorMessages() {
        if (checkErrorDivIsDisplayed()) {
            List<WebElement> errorElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(errorMessages));
            List<String> errors = new ArrayList<>();
            for (WebElement element : errorElements) {
                errors.add(element.getText().trim());
            }
            return errors;
        }
//        Assert.fail("Not found error message elements");
        return new ArrayList<>();
    }

    //    public String getNameError(){
//        if(checkErrorDivIsDisplayed()){
//            return wait.until(ExpectedConditions.presenceOfElementLocated(nameErrorMessage)).getText();
//        }
//        Assert.fail("Not found element");
//        return "";
//    }
//    public String getPhoneError(){
//        if(checkErrorDivIsDisplayed()){
//            return  wait.until(ExpectedConditions.presenceOfElementLocated(phoneErrorMessage)).getText();
//        }
//        Assert.fail("Not found element");
//        return "";
//    }
//
    public boolean checkErrorDivIsDisplayed() {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(alertErrorDiv)).isDisplayed();
        } catch (TimeoutException timeoutException) {
            return false;
        }
    }

    public String getValidationEmail() {
        return wait.until(ExpectedConditions.presenceOfElementLocated(emailInputField)).getDomProperty("validationMessage");
    }

//* ========================== Interactive Element ========================================


    public void clickAddCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addCartBtn)).click();
    }

    public void clickConfirm() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmBtn)).click();
    }

    public void enterNameField(String value) {
        wait.until(ExpectedConditions.presenceOfElementLocated(nameInputField)).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE), value);
    }

    public void enterPhoneField(String value) {
        wait.until(ExpectedConditions.presenceOfElementLocated(phoneInputField)).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE), value);
    }

    public void enterEmailField(String value) {
        wait.until(ExpectedConditions.presenceOfElementLocated(emailInputField)).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE), value);
    }

    public void enterAddressField(String value) {
        wait.until(ExpectedConditions.presenceOfElementLocated(addressInputField)).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE), value);
    }

    public void enterNoteField(String value) {
        wait.until(ExpectedConditions.presenceOfElementLocated(noteInputField)).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.BACK_SPACE), value);
    }
}



