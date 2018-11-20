package pages;

import com.google.common.base.Function;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.*;
import utils.Reporter;
import webdriver.WebDriverThread;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by yzosin on 23-Aug-17.
 */
public class BasePage {

    public static Logger logger = Logger.getLogger(BasePage.class);
    public String pageURL = "";
    public String pageTitle = "";
    static String TARGET_FOLDER = "target";
    public static final int DEFAULT_SHORT_TIMEOUT = 10;
    public static final int DEFAULT_LONG_TIMEOUT = 50;
    public static final int STATIC_TIMEOUT = 1;
    private WebDriver e_driver;
    private EventFiringWebDriver driver;
    public static String rootFolder = System.getProperty("user.dir");
    private static String fileUploadPath = rootFolder + File.separator + TARGET_FOLDER + File.separator + "SinoptikScreenshot.png";

    //public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

    static Reporter reporter = Reporter.Instance;

    private static final ThreadLocal<BasePage> instance = new ThreadLocal<BasePage>() {
        @Override
        protected BasePage initialValue() {
            return new BasePage() {
            };
        }
    };

    public BasePage() {
        e_driver = instantiateDriver();
        driver = new EventFiringWebDriver(e_driver);

        driver.manage().timeouts().implicitlyWait(DEFAULT_SHORT_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    private WebDriver instantiateDriver() {
        try {
            return new WebDriverThread().getDriver();
        } catch (Exception e) {
            throw new WebDriverException("Could not start the Driver", e);
        }
    }

    public static BasePage getInstance() {
        return instance.get();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public static void closeInstance() {
        instance.remove();
    }

    public void closeBrowser() {
        try {
            if (driver != null) {
                driver.quit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean isPageLoaded() {
        boolean result = false;
        Reporter.log("Page title is: " + driver.getTitle());
        Reporter.log("Page URL is: " + driver.getCurrentUrl());
        if (driver.getTitle().contains(pageTitle))
            result = true;
        else {
            Reporter.log("Expected title: " + pageTitle);
            result = false;
        }

        if (driver.getCurrentUrl().contains(pageURL))
            result = true;
        else {
            Reporter.log("Expected URL: " + pageURL);
            result = false;
        }
        return result;
    }

    public void reloadPage() {
        driver.navigate().refresh();
    }

    public void open(String url) {
        Reporter.log("Opening the page: " + "\"" + url);
        driver.get(url);
    }

    public String getTitle() {
        Reporter.log("The page title is: " + "\"" + pageTitle + "\"");
        return pageTitle;
    }

    public String getURL(String url) {
        Reporter.log("The requested URL is: " + url + pageURL);
        return url + pageURL;
    }

    public void setText(By element, String value) {
        if (value != null) {
            findElement(element).clear();
            findElement(element).sendKeys(value);
        }
    }

    public boolean isTextPresent(String text) {
        return driver.getPageSource().contains(text);
    }

    public boolean isElementPresent(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (RuntimeException e) {
            return false;
        }
    }

    public boolean isElementPresentAndDisplay(By by) {
        try {
            return findElement(by).isDisplayed();
        } catch (NoSuchElementException e) {
            e.getLocalizedMessage();
            return false;
        }
    }

    public WebElement getWebElement(By by) {
        return findElement(by);
    }

    public void selectFromDropdown(By element, String value) {
        Select dropdown = new Select(findElement(element));
        dropdown.selectByVisibleText(value);
    }

    public void clickOnElement(By element) {
        waitForPageToLoad();
        try {
            (new WebDriverWait(driver, STATIC_TIMEOUT))
                    .until(ExpectedConditions.visibilityOfElementLocated(element));
            driver.findElement(element).click();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failure clicking on element");
        }
        waitForPageToLoad();
    }

    public WebElement findElement(By element) {
        waitForPageToLoad();
        try {
            (new WebDriverWait(driver, DEFAULT_SHORT_TIMEOUT))
                    .until(ExpectedConditions.visibilityOfElementLocated(element));
            return driver.findElement(element);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            throw new RuntimeException("Failure finding element");

        }
    }

    public List<WebElement> findElements(By element) {
        waitForPageToLoad();
        try {
            (new WebDriverWait(driver, DEFAULT_SHORT_TIMEOUT))
                    .until(ExpectedConditions.presenceOfElementLocated(element));
            return driver.findElements(element);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failure by finding elements");
        }
    }

    public void scrollToElement(WebElement element) {
        waitForPageToLoad();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

    }

    /**
     * Waits for a page to load completely
     */
    public void waitForPageToLoad() {
        Wait<WebDriver> wait = new WebDriverWait(driver, STATIC_TIMEOUT).ignoring(WebDriverException.class);
        wait.until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
                return String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
                        .equals("complete");
            }
        });
    }

    void waitForElement(By by) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_SHORT_TIMEOUT);
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    static void waitForElementVisible(WebDriver driver, By by, int timeout) {

        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    /**
     * Action to accept alert / error message on the web page
     *
     * @return String webelement text with information provided in the Popup Window
     */
    public String acceptAlertMessage() {
        Alert jsalert = driver.switchTo().alert();
        String alertMsg = jsalert.getText();
        jsalert.accept();
        return alertMsg;
    }

    public String autoSelectFromDropdown(WebDriver driver, By by) {
        WebElement selectElement = driver.findElement(by);
        Select select = new Select(selectElement);

        int value;
        Random random = new Random();
        List<WebElement> allOptions = select.getOptions();

        // needs to updated based on application
        if (allOptions.get(0).getText().toLowerCase().contains("none")) {
            value = 1 + random.nextInt(allOptions.size() - 1);
        } else {
            value = random.nextInt(allOptions.size() - 1);
        }

        select.selectByIndex(value);
        return allOptions.get(value).getText();
    }

    public static void sleepFor(int timeout) {
        try {
            Sleeper.SYSTEM_SLEEPER.sleep(new Duration(STATIC_TIMEOUT, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
        }
    }

    static void waitForAlert(WebDriver driver, int timeout) {
        int i = 0;
        while (i++ < timeout) {
            try {
                Alert alert = driver.switchTo().alert();
                break;
            } catch (NoAlertPresentException e) {
                sleepFor(1);
                continue;
            }
        }
    }

    public void switchToFrame(By by) {
        logger.info("Switching to frame: " + by.toString());
        driver.switchTo().frame(findElement(by));
    }

    public void switchToDefaultContent() {
        logger.info("Switching to default content");
        driver.switchTo().defaultContent();
    }

    public static String takeScreenshot(WebDriver driver, String name) {
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String filename = name + "Screenshot.png";
        try {
            FileUtils.copyFile(file, new File(TARGET_FOLDER + File.separator + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filename;
    }

    public void fileUpload(By element) {
        WebElement webelement = driver.findElement(element);
        webelement.sendKeys(fileUploadPath);
    }
}
