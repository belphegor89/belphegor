package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestResult;
import org.testng.log4testng.Logger;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by owner on 04-Sep-17.
 */
public class Reporter {

    private static Logger logger = Logger.getLogger(Reporter.class);
    private static String root = System.getProperty("user.dir");
    private static String filePath = "extentreport.html";
    private static ExtentReports extent;
    private static ExtentHtmlReporter htmlReporter;
    private static Path reportPath;
    private static Path screenshotFolder;
    private static boolean buildStatus = true;
    private static ArrayList failuresBucket = new ArrayList<String>();
    private static ConcurrentHashMap<Long, ExtentTest> testStorage = new ConcurrentHashMap<>();

    private static Reporter instance;
    public static Reporter Instance = (instance != null) ? instance : new Reporter();

    private Reporter() {

        logger.info("Creating the Reporter");

        try {

            Path rootPath = getNewReportPath();
            // create directory if not exists
            if (Files.notExists(rootPath)) {
                reportPath = Files.createDirectories(rootPath);
            } else {
                reportPath = rootPath;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (null == extent)
            extent = new ExtentReports();

        htmlReporter = new ExtentHtmlReporter(Paths.get(reportPath.toString(), filePath).toAbsolutePath().toFile());
        htmlReporter.config().setChartVisibilityOnOpen(false);
        htmlReporter.config().setDocumentTitle("BELPHEGOR");
        htmlReporter.config().setReportName("Web Automation");
        htmlReporter.config().setTheme(Theme.DARK);

        extent.attachReporter(htmlReporter);

        logger.info("The ReportManger has been successfully created.");
    }

    public static synchronized ExtentTest addTest(String testName) {
        ExtentTest testCase = extent.createTest(testName);
        testStorage.put(Thread.currentThread().getId(), testCase);
        return testStorage.get(Thread.currentThread().getId());
    }

    public static synchronized void startTest(String testName) {
        ExtentTest testCase = extent.createTest(testName);
        testStorage.put(Thread.currentThread().getId(), testCase);
    }

    public static void saveAndQuit() {
        extent.flush();
    }

    private static Path getNewReportPath() {
        LocalDateTime dateTime = LocalDateTime.now();
        String reportName = "report" + "_" + dateTime.toLocalDate() + "_" + dateTime.toLocalTime().getHour() + "_"
                + dateTime.toLocalTime().getMinute();

        return Paths.get(root, "report", reportName);
    }

    public static synchronized void pass(String log) {
        testStorage.get(Thread.currentThread().getId()).pass(log);
    }

    public static synchronized void log(String log) {
        testStorage.get(Thread.currentThread().getId()).info(log);
    }

    public static void fail(String log) {

        try {
            String screenshotPath = takeScreenshot();
            screenshotPath = screenshotPath.substring(screenshotPath.indexOf("screenshots"));
            testStorage.get(Thread.currentThread().getId()).fail(log, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
            buildStatus = false;
            failuresBucket.add(log);
        } catch (Exception e) {
            testStorage.get(Thread.currentThread().getId()).fail(log);
        }
    }

    public static synchronized String takeScreenshot() {

        String testCaseName = testStorage.get(Thread.currentThread().getId()).getModel().getName();
        try {
            screenshotFolder = Paths.get(reportPath.toString(), "screenshots");

            if (Files.notExists(screenshotFolder))
                Files.createDirectory(screenshotFolder);

            String fileName = testCaseName.replace(" ", "_");
            Path screenshotPath = Paths.get(screenshotFolder.toString(), fileName + ".png");
            Screenshot screenshot = null;
            screenshot = new AShot()
                    .takeScreenshot(DriverManager.getDriver());
            ImageIO.write(screenshot.getImage(), "PNG", new File(screenshotPath.toString()));

            return screenshotPath.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void setSystemInfo(Map<String, String> info) {

        for (Map.Entry<String, String> entry : info.entrySet()) {
            extent.setSystemInfo(entry.getKey(), entry.getValue());
        }
        extent.flush();
    }

    public static synchronized void stopReporting(ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE)
            fail("Test failed because of: " + result.getThrowable().getMessage().toString());
        else if (result.getStatus() == ITestResult.SKIP)
            log("Test: " + testStorage.get(Thread.currentThread().getId()).toString() + " skipped");
        else
            pass("Test passed!");

        saveAndQuit();
    }

    public static synchronized void stopReportingAPI(ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE)
            failNoScreenshot("Test failed because of: " + result.getThrowable().getMessage().toString());
        else if (result.getStatus() == ITestResult.SKIP)
            log("Test: " + testStorage.get(Thread.currentThread().getId()).toString() + " skipped");
        else
            pass("Test passed!");

        saveAndQuit();
    }

    public static void failNoScreenshot(String log) {
        try {
            testStorage.get(Thread.currentThread().getId()).fail(log);
            buildStatus = false;
            failuresBucket.add(log);
        } catch (Exception e) {
            testStorage.get(Thread.currentThread().getId()).fail(log);
        }
    }

}
