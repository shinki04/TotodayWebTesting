package  tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.FileReader; // Import the FileReader utils
import java.util.Map;

public class ContactFormTest {

    private WebDriver driver;

    @DataProvider(name = "contactFormData")
    public Object[][] contactFormData() {
        // Use FileReader to read the Excel file
        return FileReader.readDataFromExcel("src/test/resources/contact_data.xlsx", "Sheet1");
    }

    @BeforeMethod
    public void setUp() {
        // Set up WebDriver (adjust path to your ChromeDriver)
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
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

        // Navigate to the contact page
        driver.get("your_contact_page_url");

        // Fill the form using the IDs from the image
        WebElement nameField = driver.findElement(By.id("name"));
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement numberField = driver.findElement(By.id("number"));
        WebElement messageField = driver.findElement(By.id("message"));
        WebElement submitButton = driver.findElement(By.xpath("//button[text()='GỬI']"));

        nameField.sendKeys(name);
        emailField.sendKeys(email);
        numberField.sendKeys(number);
        messageField.sendKeys(message);

        // Submit the form
        submitButton.click();

        // Validate based on the test case
        if (testCase.equals("Gửi thông tin thành công")) {
            // Check for a success message (adjust XPath as needed)
            Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(), 'Gửi thành công')]")).isDisplayed(),
                    "Success message should be displayed for: " + name);
        } else {
            // Check for absence of success message in failure case
            Assert.assertFalse(driver.findElement(By.xpath("//div[contains(text(), 'Gửi thành công')]")).isDisplayed(),
                    "Success message should not be displayed for failed case: " + name);
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}