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

    String URL;
    String city;
    String URL2;
    String username;
    String password;
    String recipient;

    //@Test(dataProvider = "smoke", dataProviderClass = DataProviders.class, testName = "SendWeatherForecast")
    @Test(dataProvider = "testData")
    public void executeWeatherForecast(List data) {

        URL = data.get(0).toString();
        city = data.get(1).toString();
        URL2 = data.get(2).toString();
        username = data.get(3).toString();
        password = data.get(4).toString();
        recipient = data.get(5).toString();

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