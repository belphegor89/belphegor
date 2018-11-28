package utils;

public class PreExecutionFiles {

    public static String  TEST_FILES_FOLDER = null;
    public static String RESOURCES_FOLDER = "src/main/resources";

    PropertiesReader propertiesReader;

    public PreExecutionFiles (){
        this.propertiesReader = new PropertiesReader();
    }


   public void initPahs() {

       String rootFolder = System.getProperty("user.dir").replace("\\", "/");
       TEST_FILES_FOLDER = rootFolder + "/src/test/inputData";

   }

}
