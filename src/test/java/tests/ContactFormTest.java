package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.FileReader;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.util.Map;

public class ContactFormTest {

    private WebDriver driver;

    // Định nghĩa URL thực tế của trang liên hệ
    private static final String CONTACT_PAGE_URL = "https://www.xaydungphanmem.com/lien-he";

    // Phương thức khởi tạo WebDriver
    private void initializeWebDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @DataProvider(name = "contactFormData")
    public Object[][] contactFormData() {
        return FileReader.readDataFromExcel("src/test/resources/contact_data.xlsx", "Sheet1");
    }

    @BeforeMethod
    public void setUp() {
        initializeWebDriver();
        driver.manage().window().maximize();
    }

    @Test(dataProvider = "contactFormData", priority = 0)
    public void testContactForm(Map<String, String> data) {
        // Extract data from the Map
        String name = data.get("Name");
        String email = data.get("Email");
        String number = data.get("Number");
        String message = data.get("Message");
        String testCase = data.get("TestCase");

        // Print test data for debugging
        System.out.println("Test case: " + testCase);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Number: " + number);
        System.out.println("Message: " + message);

        // Navigate to the contact page with error handling
        try {
            driver.get(CONTACT_PAGE_URL);
            // Đợi trang tải xong
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.urlToBe(CONTACT_PAGE_URL));
            Thread.sleep(2000); // Thêm sleep 2 giây sau khi trang tải xong
        } catch (Exception e) {
            Assert.fail("Không thể truy cập trang liên hệ: " + CONTACT_PAGE_URL + ". Lỗi: " + e.getMessage());
            return; // Dừng test nếu không truy cập được URL
        }

        // Wait for page elements to be ready
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Find and fill the form
            WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
            WebElement numberField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("number")));
            WebElement messageField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='GỬI']")));

            nameField.clear();
            nameField.sendKeys(name);
            emailField.clear();
            emailField.sendKeys(email);
            numberField.clear();
            numberField.sendKeys(number);
            messageField.clear();
            messageField.sendKeys(message);

            // Thêm sleep trước khi gửi form để đảm bảo dữ liệu được điền đầy đủ
            Thread.sleep(1000); // Sleep 1 giây trước khi nhấn nút Gửi

            // Submit the form
            submitButton.click();

            // Thêm sleep sau khi gửi form để đợi phản hồi từ server
            Thread.sleep(2000); // Sleep 2 giây sau khi gửi form

            // Validate based on the test case
            if (testCase.equals("Gửi thông tin thành công")) {
                WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//div[contains(text(), 'Gửi thành công')]")));
                Assert.assertTrue(successMessage.isDisplayed(), "Success message should be displayed for: " + name);
            } else {
                // Thêm sleep trước khi kiểm tra thông báo thất bại
                Thread.sleep(1000); // Sleep 1 giây trước khi kiểm tra thông báo
                boolean successMessageAbsent;
                try {
                    driver.findElement(By.xpath("//div[contains(text(), 'Gửi thành công')]"));
                    successMessageAbsent = false;
                } catch (Exception e) {
                    successMessageAbsent = true;
                }
                Assert.assertTrue(successMessageAbsent, "Success message should not be displayed for failed case: " + name);
            }
        } catch (Exception e) {
            Assert.fail("Test failed due to an exception: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}