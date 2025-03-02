package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    private String filePath;

    public ExcelReader(String filePath) {
        this.filePath = filePath;
    }

    public List<String[]> readExcelData(int sheetIndex) throws IOException {
        List<String[]> data = new ArrayList<>();
        FileInputStream fis = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(sheetIndex);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Bỏ qua tiêu đề nếu có
            Row row = sheet.getRow(i);
            if (row == null) continue;

            String[] rowData = new String[row.getLastCellNum()];
            for (int j = 0; j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j);
                rowData[j] = (cell != null) ? cell.toString() : "";
            }
            data.add(rowData);
        }

        workbook.close();
        fis.close();
        return data;
    }
}
