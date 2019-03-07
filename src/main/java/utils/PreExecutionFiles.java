package utils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PreExecutionFiles {

    public static String TEST_FILES_FOLDER = null;
    public static String RESOURCES_FOLDER = "src/main/resources";
    public static String METADATA_AND_KEYWORDS_FILES_FOLDER_PATH = "src/main/resources/keywords.metadata";
    public static String TEST_EXECUTION_FILE_NAME = "Test.xlsx";

    PropertiesReader propertiesReader;

    public PreExecutionFiles() {
        this.propertiesReader = new PropertiesReader();
    }


    public void initPahs() {

        String rootFolder = System.getProperty("user.dir").replace("\\", "/");
        TEST_FILES_FOLDER = rootFolder + "/src/test/";

        if (!System.getProperty("testFile").equalsIgnoreCase("Test.xlsx")) {
            TEST_EXECUTION_FILE_NAME = System.getProperty("testFile");
        }

    }

    public Object[][] getExcel(String testCase) {
        String sheet = System.getProperty("testSheet");
        ExcelReader excelReader = new ExcelReader();
        try {
            excelReader.setExcel(TEST_FILES_FOLDER, TEST_EXECUTION_FILE_NAME, sheet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List rowsNo = excelReader.getRowContains(testCase, 0);
        return excelReader.getTableArray(rowsNo);
    }

}
