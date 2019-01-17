package utils;


import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {
    private XSSFSheet excelSheet;
    private XSSFWorkbook excelWorkbook;
    private XSSFCell cell;

    public Sheet setExcel(String filePath, String fileName, String sheetName) throws IOException {

        // Create a object of File class to open xlsx file
        File file = new File(filePath, fileName);

        // Create an object of FileInputStream class to read excel file
        FileInputStream inputStream = new FileInputStream(file);
        excelWorkbook = new XSSFWorkbook(inputStream);
        inputStream.close();

        // Read sheet inside the workbook by its name
        excelSheet = excelWorkbook.getSheet(sheetName);
        return excelSheet;
    }

    public List getRowContains(String testCaseName, int colNum) {
        List list = new ArrayList<>();
        int rowCount = getRowUsed();
        for (int i = 1; i <= rowCount; i++) {
            String cellData = getCellData(i, colNum);
            if (cellData.equalsIgnoreCase(testCaseName)) {
                list.add(i);
            }
        }
        return list;
    }

    public int getRowUsed() {
        return excelSheet.getLastRowNum();
    }

    public String getCellData(int rowNum, int colNum) {
        DataFormatter formatter = new DataFormatter();
        cell = excelSheet.getRow(rowNum).getCell(colNum);
        String result = formatter.formatCellValue(cell);
        return result;
        //return cell.getStringCellValue();
    }

    public List[] getRowData(int rowNo) {
        List[] arr = new List[1];
        List list = new ArrayList();
        int startCol = 1;
        int totalCols = excelSheet.getRow(rowNo)
                .getPhysicalNumberOfCells();
        for (int i = startCol; i < totalCols; i++) {
            String cellData = getCellData(rowNo, i);
            list.add(cellData);
        }
        arr[0] = list;
        return arr;
    }

    public Object[][] getTableArray(List<Integer> rowsNo) {
        Object[][] tabArray = new Object[rowsNo.size()][];
        for (int i = 0; i < rowsNo.size(); i++) {
            tabArray[i] = getRowData(rowsNo.get(i));
        }
        return tabArray;
    }
}
