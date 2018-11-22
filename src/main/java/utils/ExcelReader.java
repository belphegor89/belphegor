package utils;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelReader {

    public Sheet readExcel(String filePath, String fileName, String sheetName) throws IOException {
        File file = new File(filePath, fileName);
        FileInputStream inputStream = new FileInputStream(file);
        Workbook excelWorkbook;
        excelWorkbook = new XSSFWorkbook(inputStream);
        inputStream.close();
        return excelWorkbook.getSheet(sheetName);
    }

    public ArrayList<String> getAllSheets(String filePath, String fileName) throws IOException {
        File file = new File(filePath, fileName);
        FileInputStream inputStream = new FileInputStream(file);
        Workbook excelWorkbook;
        excelWorkbook = new XSSFWorkbook(inputStream);
        inputStream.close();

        ArrayList<String> sheets = new ArrayList<>();
        for (int sheetindex = 0; sheetindex < excelWorkbook.getNumberOfSheets(); sheetindex++) {
            sheets.add(excelWorkbook.getSheetAt(sheetindex).getSheetName());
        }

        return sheets;
    }
}
