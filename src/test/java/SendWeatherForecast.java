import org.apache.poi.ss.usermodel.Sheet;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.SendMail;
import pages.ForecastPage;
import utils.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yzosin on 20-Sep-17.
 */
public class SendWeatherForecast extends BaseUITest {


    @Test(dataProvider = "testData", testName = "")
    public void executeWeatherForecast(List data) {

        String URL = data.get(0).toString();
        String city = data.get(1).toString();
        ForecastPage forecast = ForecastPage.Instance;
        LoginPage loginPage = LoginPage.Instance;
        SendMail sendMail = SendMail.Instance;
        Reporter.log("Starting test for searching weather forecast");
        forecast.searchCity(URL, city);
        Reporter.log("A city: " + PropertiesReader.getConfigProperty("searchCity") + " entered into search field");
        forecast.takeScreenshotForecast();
        Reporter.log("Screenshot with forecast taken");
        Reporter.log("Starting next part for sending forecast via email");
        Reporter.log("Trying to log in to " + PropertiesReader.getConfigProperty("URL2"));
        loginPage.login();
        Reporter.log("User is logged in. Sending mail.");
        sendMail.sendMailWithFile();
        Assert.assertTrue(sendMail.validateMessageSent());
    }

    @DataProvider
    public Object[][] testData(Method method) {

        PreExecutionFiles preExecutionFiles = new PreExecutionFiles();
        preExecutionFiles.initPahs();
        ExcelReader excelReader = new ExcelReader();
        String sheet = "smoke";
        try {
            excelReader.setExcel(PreExecutionFiles.TEST_FILES_FOLDER, PreExecutionFiles.TEST_EXECUTION_FILE_NAME, sheet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List rowsNo = excelReader.getRowContains(getClass().getName(), 0);
        return excelReader.getTableArray(rowsNo);
    }
}