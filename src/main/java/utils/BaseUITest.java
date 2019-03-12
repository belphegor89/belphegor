package utils;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import pages.BasePage;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yzosin on 20-Sep-17.
 */
public class BaseUITest {

    public static Logger logger = Logger.getLogger(BaseUITest.class);

    Reporter reporter;
    public ArrayList<String> inputParameters = new ArrayList();

    @BeforeMethod
    public void before() {

        reporter = Reporter.Instance;
        Reporter.startTest(getClass().getName());
        MetaDataHandler.instantiate();
        KeywordsHandler.instantiate();

        try {
            logger.info("Creating driver for " + getClass().getName() + " test");
            BasePage.driver.set(DriverManager.getDriver());
        } catch (Exception e) {
            e.printStackTrace();
            Reporter.fail("Failed during driver creation");
            Reporter.saveAndQuit();
            Assert.fail();
        }
    }

    @AfterMethod
    public void endTest(ITestResult testResult) {
        Reporter.stopReporting(testResult);
        BasePage.driver().quit();
        DriverManager.closeDriver();
    }

    @AfterSuite(alwaysRun = true)
    public void flushReporter() {
        Reporter.saveAndQuit();
    }

    public void checkTestParameters (List parametersList) {
        String value = null;
        String inputParameter;
        for (int i =0; i<parametersList.size(); i++) {
            value = parametersList.get(i).toString();
            inputParameter = ParametersController.checkIfSpecialParameter(value);
            inputParameters.add(i, inputParameter);
        }
    }

    public Object[][] getTestParameters () {
        PreExecutionFiles preExecutionFiles = new PreExecutionFiles();
        preExecutionFiles.initPahs();
        return preExecutionFiles.getExcel(getClass().getName());
    }
}
