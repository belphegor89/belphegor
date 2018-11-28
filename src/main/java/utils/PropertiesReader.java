package utils;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.*;
import java.util.Properties;

public class PropertiesReader {

    public static String getConfigProperty(String fieldName) {
        String result = null;

        try {
            File file = new File(PreExecutionFiles.RESOURCES_FOLDER + "/project.properties");
            FileInputStream fileInput = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(fileInput);
            fileInput.close();
            result = properties.getProperty(fieldName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getProjectVersion() {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = null;
        try {
            model = reader.read(new FileReader("pom.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return (model.getVersion());
    }

    /**
     * <p>It returns the property value specified in either environment variable or configuration.properties
     * It gives priority to the property specified in Java environment variable For e.g. -Ddriver_id=FIREFOX
     * @param key used to search for property
     * @return </p>
     */
    public static String determineEffectivePropertyValue(String key) {

        PropertiesReader propertiesReader = new PropertiesReader();

        if (null != System.getProperty(key)) {
            return System.getProperty(key);
        } else {
            return propertiesReader.getConfigProperty(key);
        }
    }
}
