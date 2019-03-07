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
import java.util.*;

/**
 * Created by yzosin on 20-Sep-17.
 */
public class SendWeatherForecast extends BaseUITest {

    String URL;
    String city;
    String URL2;
    String username;
    String password;
    String recipient;
    public ArrayList<String> inputParameters = new ArrayList();


    @Test(dataProvider = "testData")
    public void executeWeatherForecast(List data) {


        //checkTestParameters(data);

        String value = null;
        String inputParameter;
        for (int i =0; i<data.size(); i++) {
            value = data.get(i).toString();
            inputParameter = ParametersController.checkIfSpecialParameter(value);
            inputParameters.add(i, inputParameter);
        }

        URL = inputParameters.get(0);
        city = inputParameters.get(1);
        URL2 = inputParameters.get(2);
        username = inputParameters.get(3);
        password = inputParameters.get(4);
        recipient = inputParameters.get(5);

        ForecastPage forecast = ForecastPage.Instance;
        LoginPage loginPage = LoginPage.Instance;
        SendMail sendMail = SendMail.Instance;
        Reporter.log("Starting test for searching weather forecast");
        forecast.searchCity(URL, city);
        Reporter.log("A city: " + city + " entered into search field");
        forecast.takeScreenshotForecast();
        Reporter.log("Screenshot with forecast taken");
        Reporter.log("Starting next part for sending forecast via email");
        Reporter.log("Trying to log in to " + PropertiesReader.getConfigProperty("URL2"));
        loginPage.login(URL2, username, password);
        Reporter.log("User is logged in. Sending mail.");
        sendMail.sendMailWithFile(recipient);
        Assert.assertTrue(sendMail.validateMessageSent());
    }

    @DataProvider
    public Object[][] testData(Method method) {

        PreExecutionFiles preExecutionFiles = new PreExecutionFiles();
        preExecutionFiles.initPahs();
        return preExecutionFiles.getExcel(getClass().getName());
    }
}