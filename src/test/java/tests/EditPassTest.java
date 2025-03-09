package tests;

import base.BaseTest;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.EditPassPage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditPassTest extends BaseTest {
    private String loginURL = baseURL + "/user/signin";
    private String changePasswordURL = baseURL + "/profile/changepassword";
    private EditPassPage editPassPage;

    @BeforeMethod
    public void setupTest() {
        editPassPage = new EditPassPage(driver, notification);
    }

    @DataProvider(name = "passwordChangeData")
    public Object[][] passwordChangeData() {
        List<Object[]> data = new ArrayList<>();
        String excelFilePath = "TotodayWebTesting/src/test/resources/edit_password.xlsx";
        File file = new File(excelFilePath);

        if (!file.exists()) {
            System.err.println("File not found: " + excelFilePath);
            return new Object[][]{{"default@example.com", "defaultPass", "newPass123", "newPass123"}};
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            Workbook workbook = WorkbookFactory.create(fis);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Bỏ qua header

                String account = row.getCell(0) != null ? row.getCell(0).toString() : "";
                String currentPassword = row.getCell(1) != null ? row.getCell(1).toString() : "";
                String newPassword = row.getCell(2) != null ? row.getCell(2).toString() : "";
                String confirmPassword = row.getCell(3) != null ? row.getCell(3).toString() : "";

                data.add(new Object[]{account, currentPassword, newPassword, confirmPassword});
            }
            workbook.close();
        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + e.getMessage());
            e.printStackTrace();
            return new Object[][]{{"default@example.com", "defaultPass", "newPass123", "newPass123"}};
        }

        if (data.isEmpty()) {
            System.err.println("No data found in Excel file: " + excelFilePath);
            return new Object[][]{{"default@example.com", "defaultPass", "newPass123", "newPass123"}};
        }

        System.out.println("Loaded " + data.size() + " test cases from Excel file.");
        return data.toArray(new Object[0][]);
    }

    @Test(dataProvider = "passwordChangeData")
    public void testChangePassword(String account, String currentPassword, String newPassword, String confirmPassword) {
        editPassPage.changePassword(loginURL, changePasswordURL, account, currentPassword, newPassword, confirmPassword);
    }
}