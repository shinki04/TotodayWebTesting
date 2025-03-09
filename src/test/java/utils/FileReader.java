package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileReader {

    // Đọc dữ liệu từ Excel, dùng hàng 0 (header) làm key cho Map
    public static Object[][] readDataFromExcel(String filePath, String sheetName) {
        List<Object[]> data = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(new File(filePath));
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Row headerRow = sheet.getRow(0); // Hàng 0 làm header
            int columnCount = headerRow.getLastCellNum();

            // Đọc từ dòng 1 (dữ liệu)
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    Map<String, String> rowData = new HashMap<>();
                    for (int j = 0; j < columnCount; j++) {
                        String header = headerRow.getCell(j).toString(); // Tên cột từ header
                        String value = row.getCell(j) != null ? row.getCell(j).toString() : "";
                        rowData.put(header, value);
                    }
                    data.add(new Object[]{rowData}); // Mỗi dòng: [Map]
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read Excel file: " + filePath);
        }
        return data.toArray(new Object[0][0]);
    }

    // Phương thức JSON giữ nguyên từ trước
    public static Object[][] readDataFromJson(String filePath) {
        List<Object[]> data = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readTree(new File(filePath));
            if (rootNode.isArray()) {
                for (JsonNode node : rootNode) {
                    if (node.has("key")) {
                        String key = node.get("key").asText();
                        Map<String, String> rowData = new HashMap<>();
                        node.fields().forEachRemaining(entry -> {
                            if (!entry.getKey().equals("key")) {
                                rowData.put(entry.getKey(), entry.getValue().asText());
                            }
                        });
                        data.add(new Object[]{key, rowData});
                    } else {
                        throw new IllegalArgumentException("Each JSON object must have a 'key' field");
                    }
                }
            } else {
                throw new IllegalArgumentException("JSON must be an array");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read JSON file: " + filePath);
        }
        return data.toArray(new Object[0][0]);
    }
}

