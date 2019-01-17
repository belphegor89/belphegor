package utils;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import pages.BasePage;

/**
 * Created by yzosin on 20-Sep-17.
 */
public class BaseUITest {

    public static Logger logger = Logger.getLogger(BaseUITest.class);

    Reporter reporter;

    @BeforeMethod
    public void before() {

        reporter = Reporter.Instance;
        Reporter.startTest(getClass().getName());
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
}
