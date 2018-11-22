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
public class BaseTest {

    public static Logger logger = Logger.getLogger(BaseTest.class);

    Reporter reporter;


    @BeforeMethod
    public void before() {

        reporter = Reporter.Instance;

        Reporter.startTest(getClass().getName().toString());
        try {
            logger.info("Creating driver for " + getClass().getName().toString() + " test");
            //BasePage.getInstance().getDriver();
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
//        BasePage.getInstance().getDriver().quit();
//        BasePage.getInstance().closeBrowser();
//        BasePage.closeInstance();
        BasePage.driver().quit();
        DriverManager.closeDriver();
    }

    @AfterSuite(alwaysRun = true)
    public void flushReporter() {
        Reporter.saveAndQuit();
    }
}
