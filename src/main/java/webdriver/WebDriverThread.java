package webdriver;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import utils.PropertiesReader;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author yzosin
 * <p>
 * <p>
 * This enables us to create webdriver instance for a thread
 * </p>
 */
public class WebDriverThread {

    private WebDriver driver;
    private DriverType driverType;
    private DriverType selectedDriverType;
    private String driver_id;

    // setting the default DriverType as FIREFOX_LOCAL
    private final DriverType defaultDriverType = DriverType.FIREFOX;

    // setting the other variables
    private final String operatingSystem = System.getProperty("os.name").toUpperCase();
    private final String systemArchitecture = System.getProperty("os.arch").toUpperCase();
    private final boolean useRemoteWebDriver = Boolean.getBoolean("remoteDriver");

    /**
     * <p>
     * Create a desired capabilities instance It instantiate the web webdriver by
     * determining the effective webdriver type.
     * </p>
     *
     * @return webdriver
     * @throws Exception The class Exception and its subclasses are a form of
     *                   Throwable that indicates conditions that a reasonable
     *                   application might want to catch.
     *                   <p>
     *                   The class Exception and any subclasses that are not also
     *                   subclasses of RuntimeException are checked exceptions.
     *                   Checked exceptions need to be declared in a method or
     *                   constructor's throws clause if they can be thrown by the
     *                   execution of the method or constructor and propagate outside
     *                   the method or constructor boundary.
     */
    public WebDriver getDriver() throws Exception {

        if (null == driver) {
            selectedDriverType = determineEffectiveDriverType();

            MutableCapabilities desiredCapabilities = selectedDriverType.getDesiredDriverOptions();

            instantiateWebDriver(desiredCapabilities);
        }
        return driver;
    }

    /**
     * <p>
     * Returns the most appropriate webdriver type It allows to read the driver_id
     * from the environment variables
     * </p>
     *
     * @return DriverType
     */
    private DriverType determineEffectiveDriverType() {
        driverType = defaultDriverType;

        // get the browser id from Command Line
        if (null != System.getProperty("driver")) {
            driver_id = System.getProperty("driver");
            driverType = DriverType.valueOf(driver_id);
        } else {
            PropertiesReader propertiesReader = new PropertiesReader();
            driver_id = propertiesReader.getConfigProperty("driver").toString();
            driverType = DriverType.valueOf(driver_id);
        }

        return driverType;
    }

    /**
     * <p>
     * Instantiate the webdriver using desiredCapabilities object.
     * </p>
     *
     * @param desiredCapabilities
     * @throws MalformedURLException
     */
    private void instantiateWebDriver(MutableCapabilities desiredCapabilities) throws MalformedURLException {

        System.out.println(String.format("Current Operating System : {%s}", operatingSystem));
        System.out.println(String.format("Current System Architecture : {%s}", systemArchitecture));
        System.out.println(String.format("Current Browser : {%s}\n", driverType));

        if (useRemoteWebDriver) {
            URL seleniumGridURL = new URL(System.getProperty("gridURL"));
            System.out.println("seleniumGridURL = " + seleniumGridURL);
            String desiredBrowserVersion = System.getProperty("desiredBrowserVersion");
            String desiredPlatform = System.getProperty("desiredPlatform");

            DesiredCapabilities capabilities = (DesiredCapabilities) desiredCapabilities;

            if ((null != desiredPlatform) && desiredPlatform.isEmpty()) {
                capabilities.setPlatform(Platform.valueOf(desiredPlatform));
            }

            if (null != desiredBrowserVersion && desiredBrowserVersion.isEmpty()) {
                capabilities.setVersion(desiredBrowserVersion);
            }
            driver = new RemoteWebDriver(seleniumGridURL, desiredCapabilities);

        } else {
            driver = selectedDriverType.getWebDriverObject(desiredCapabilities);
        }
    }
}
