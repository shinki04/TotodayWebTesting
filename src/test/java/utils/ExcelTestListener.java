package utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ExcelTestListener implements ITestListener {

    private static Map<String, Workbook> testWorkbooks = new HashMap<>();
    private static Map<String, Sheet> testSheets = new HashMap<>();
    private static Map<String, Integer> rowNums = new HashMap<>();
    private static Map<String, CellStyle> passStyleMap = new HashMap<>();
    private static Map<String, CellStyle> failStyleMap = new HashMap<>();

    @Override
    public void onStart(ITestContext context) {
        String testName = context.getName();
        if (!testWorkbooks.containsKey(testName)) {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(testName);
            testWorkbooks.put(testName, workbook);
            testSheets.put(testName, sheet);
            rowNums.put(testName, 1);

            // Tạo style cho PASS (màu xanh)
            CellStyle passStyle = workbook.createCellStyle();
            passStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            passStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            passStyleMap.put(testName, passStyle);

            // Tạo style cho FAIL (màu đỏ)
            CellStyle failStyle = workbook.createCellStyle();
            failStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
            failStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            failStyleMap.put(testName, failStyle);

            // Tạo header với cột STT
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("STT"); // Cột mới
            headerRow.createCell(1).setCellValue("Test Case Name");
            headerRow.createCell(2).setCellValue("Test Name");
            headerRow.createCell(3).setCellValue("Status");
            headerRow.createCell(4).setCellValue("Execution Time");
            headerRow.createCell(5).setCellValue("Failure Reason (if any)");
        }
        System.out.println("Bắt đầu chạy test: " + testName);
    }

    @Override
    public void onFinish(ITestContext context) {
        String testName = context.getName();
        saveExcelFile(testName);
        closeWorkbook(testName);
        System.out.println("Hoàn tất test: " + testName);
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Test case bắt đầu: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        recordTestResult(result, "PASSED", null);
        System.out.println("Đây là test case chạy thành công: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String failureReason = result.getThrowable() != null ? result.getThrowable().getMessage() : "N/A";
        recordTestResult(result, "FAILED", failureReason);
        System.out.println("Đây là test case bị fail: " + result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        recordTestResult(result, "SKIPPED", null);
        System.out.println("Đây là test case bị bỏ qua: " + result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        String failureReason = result.getThrowable() != null ? result.getThrowable().getMessage() : "N/A";
        recordTestResult(result, "FAILED_WITHIN_SUCCESS_PERCENTAGE", failureReason);
        System.out.println("Test case thất bại nhưng trong tỷ lệ thành công: " + result.getName());
    }

    private void recordTestResult(ITestResult result, String status, String failureReason) {
        String testName = result.getTestContext().getName();
        Sheet sheet = testSheets.get(testName);
        int rowNum = rowNums.get(testName);

        String testCaseName = result.getName();
        String testNameAttribute = getTestNameFromAnnotation(result);
        long executionTime = result.getEndMillis() - result.getStartMillis();

        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(rowNum); // Ghi STT
        row.createCell(1).setCellValue(testCaseName);
        row.createCell(2).setCellValue(testNameAttribute != null ? testNameAttribute : "");

        // Áp dụng màu cho cột Status
        Cell statusCell = row.createCell(3);
        statusCell.setCellValue(status);
        if ("PASSED".equals(status)) {
            statusCell.setCellStyle(passStyleMap.get(testName));
        } else if ("FAILED".equals(status)) {
            statusCell.setCellStyle(failStyleMap.get(testName));
        }

        row.createCell(4).setCellValue(executionTime + " ms");
        row.createCell(5).setCellValue(failureReason != null ? failureReason : "");

        for (int i = 0; i < 6; i++) { // Cập nhật số cột thành 6
            sheet.autoSizeColumn(i);
        }

        rowNums.put(testName, rowNum + 1);
    }

    // Phương thức để lấy testName từ annotation @Test
    private String getTestNameFromAnnotation(ITestResult result) {
        try {
            Method method = result.getMethod().getConstructorOrMethod().getMethod();
            Test testAnnotation = method.getAnnotation(Test.class);
            if (testAnnotation != null && !testAnnotation.testName().isEmpty()) {
                return testAnnotation.testName();
            }
        } catch (Exception e) {
            System.out.println("Không thể lấy testName từ annotation: " + e.getMessage());
        }
        return null;
    }

    private void saveExcelFile(String testName) {
        try {
            String fileName = "ExportExcel/" + testName + ".xlsx";
            FileOutputStream fileOut = new FileOutputStream(new File(fileName));
            Workbook workbook = testWorkbooks.get(testName);
            workbook.write(fileOut);
            fileOut.close();
            System.out.println("Kết quả đã được ghi vào file: " + fileName);
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file Excel: " + e.getMessage());
        }
    }

    private void closeWorkbook(String testName) {
        try {
            Workbook workbook = testWorkbooks.get(testName);
            workbook.close();
            testWorkbooks.remove(testName);
            testSheets.remove(testName);
            rowNums.remove(testName);
            passStyleMap.remove(testName);
            failStyleMap.remove(testName);
        } catch (IOException e) {
            System.out.println("Lỗi khi đóng workbook: " + e.getMessage());
        }
    }
}