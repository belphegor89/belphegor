package utils;

public class PreExecutionFiles {

    public static String TEST_FILES_FOLDER = null;
    public static String RESOURCES_FOLDER = "src/main/resources";
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

}
