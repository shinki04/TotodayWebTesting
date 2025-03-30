package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import pages.AddCartPLTPage;
import utils.FileReader;
import utils.Tools;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static config.DriverConfig.getDriver;

public class AddCartPLTTest {
    private static WebDriver driver;
    private final List<String> nameErrorMessage = Arrays.asList("Tên không được quá 50 kí tự", "Xin hãy nhập đầy đủ tên.");
    private final List<String> phoneErrorMessage = Arrays.asList("SDT không được quá 12 kí tự", "Xin hãy nhập số điện thoại.", "SDT phải là số");
    private AddCartPLTPage addCartPLTPage;
    private String addCartURL = "https://pltpro.net/";

//    private String nameMaxLengthMessage = "Tên không được quá 50 kí tự";
//    private String phoneMaxLengthMessage = "SDT không được quá 12 kí tự";
//    private String nameMinLengthMessage = "Xin hãy nhập đầy đủ tên.";
//    private String phoneMinLengthMessage = "Xin hãy nhập số điện thoại.";
    private WebDriverWait wait;
    private Tools tools;


    @BeforeTest
    void setupTest() {
        driver = getDriver();
        tools = new Tools(driver, wait);
        WebDriverManager.chromedriver().setup();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        addCartPLTPage = new AddCartPLTPage(driver, wait);
        driver.manage().window().maximize();
        driver.navigate().refresh();
    }
    @BeforeMethod
    void setupMethod(){
        driver.get(addCartURL);
        addCartPLTPage.clickAddCart();
//        tools.checkEqualMessage(driver.getCurrentUrl(), "https://pltpro.net/gio-hang");
    }
    @DataProvider(name = "Information")
    private Object[][] InformationData() {
        return FileReader.readDataFromExcel("src/test/resources/AddCartPLT.xlsx", "InforData");
    }

    @Test(dataProvider = "Information")
    void test01(Map<String, String> data) {
        addCartPLTPage.enterNameField(data.get("name"));
        addCartPLTPage.enterPhoneField(data.get("phone"));
        addCartPLTPage.enterEmailField(data.get("email"));
        addCartPLTPage.enterAddressField(data.get("address"));
        addCartPLTPage.enterNoteField(data.get("note"));
        addCartPLTPage.clickConfirm();
        // Lấy danh sách các thông báo lỗi
        List<String> errorMessages = addCartPLTPage.getErrorMessages();

        // Kiểm tra lỗi liên quan đến name
        if (data.get("name").isEmpty()) {
            tools.checkContainsMessageList(errorMessages, "Xin hãy nhập đầy đủ tên.");
        } else if (data.get("name").length() > 50) {
            tools.checkContainsMessageList(errorMessages, "Tên không được quá 50 kí tự");
        }

        // Kiểm tra lỗi liên quan đến phone
        if (data.get("phone").isEmpty()) {
            tools.checkContainsMessageList(errorMessages, "Xin hãy nhập số điện thoại.");
        } else if (data.get("phone").length() > 12) {
            tools.checkContainsMessageList(errorMessages, "SDT không được quá 12 kí tự");
        } else if (!data.get("phone").matches("\\d+")) {
            tools.checkContainsMessageList(errorMessages, "SDT phải là số");
        }

        // Kiểm tra lỗi liên quan đến email
        if (!data.get("email").contains("@")) {
            tools.checkContainsMessage(addCartPLTPage.getValidationEmail(), "Please include an '@' in the email address.");
        }
    }



}
