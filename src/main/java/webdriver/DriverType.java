package webdriver;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import utils.PropertiesReader;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yzosin
 * <p>
 * DriverType implements DriverSetup interface
 * <p>
 * It has enums for all the types of Driver Types
 * This is what is passed from System Property variable as
 * driver=FIREFOX
 * </p>
 */
public enum DriverType implements DriverSetup {

    FIREFOX {
        public FirefoxOptions getDesiredDriverOptions() {
            FirefoxOptions options =
                    new FirefoxOptions();

            return options;
        }

        public <T> WebDriver getWebDriverObject(T instanceOptions) {
            try {

                configureGecko();

                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("browser.download.folderList", 2);
                profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                        "image/jpeg, application/pdf, application/octet-stream, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                profile.setPreference("pdfjs.disabled", true);

                FirefoxOptions options = (FirefoxOptions) instanceOptions;
                options.setCapability(FirefoxDriver.PROFILE, profile);

                return new FirefoxDriver(options);
            } catch (Exception e) {
                throw new WebDriverException("Unable to launch the browser", e);
            }
        }
    },
    CHROME {
        public ChromeOptions getDesiredDriverOptions() {
            //downloads folder to automatically save the downloaded files
            File folder = new File("downloads");
            folder.mkdir();

            configureChrome();

            ChromeOptions options = new ChromeOptions();
            Map<String, Object> prefs = new HashMap<String, Object>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);

            options.setExperimentalOption("prefs", prefs);
            options.addArguments("--test-type");
            options.addArguments("--start-maximized");
            options.addArguments("--disable-save-password-bubble");

            options.setCapability(ChromeOptions.CAPABILITY, options);
            options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

            options.setCapability("chrome.switches", Arrays.asList("--no-default-browser-check"));
            HashMap<String, Object> chromePreferences = new HashMap<>();
            chromePreferences.put("profile.password_manager_enabled", "false");
            chromePreferences.put("credentials_enable_service", "false");
            chromePreferences.put("profile.default_content_settings.popups", 0);
            chromePreferences.put("download.default_directory", folder.getAbsolutePath());

            options.setCapability("chrome.prefs", chromePreferences);

            return options;
        }

        public <T> WebDriver getWebDriverObject(T instanceOptions) {
            return new ChromeDriver((ChromeOptions) instanceOptions);
        }
    },
    SAFARI {
        public SafariOptions getDesiredDriverOptions() {
            SafariOptions options =
                    new SafariOptions();
            options.setCapability("safari.cleansession",
                    true);

            return options;
        }

        public <T> WebDriver getWebDriverObject(T instanceOptions) {
            return new SafariDriver((SafariOptions) instanceOptions);
        }
    };

    /**
     * It configures the Gecko webdriver
     */
    private static void configureGecko() {

        String os = System.getProperty("os.name").toLowerCase();
        String geckoPath = null;
        if (os.indexOf("mac") >= 0) {
            geckoPath = System.getProperty("user.dir").replace("\\", "/") + "/" + "drivers/geckodriver_mac";
        } else if (os.indexOf("win") >= 0) {
            geckoPath = System.getProperty("user.dir").replace("\\", "/") + "/" +"drivers/geckodriver.exe";
        } else {
            throw new IllegalArgumentException("Operating System : " + os + " is not supported");
        }
        System.setProperty("webdriver.gecko.driver", geckoPath);

    }

    /**
     * It configures the Chrome webdriver
     */
    private static void configureChrome() {
        String os = System.getProperty("os.name").toLowerCase();
        String chromePath = null;
        if (os.indexOf("mac") >= 0) {
            chromePath = System.getProperty("user.dir").replace("\\", "/") + "/" + "drivers/chromedriver_mac";
        } else if (os.indexOf("win") >= 0) {
            chromePath = System.getProperty("user.dir").replace("\\", "/") + "/" + "drivers/chromedriver.exe";
        } else {
            throw new IllegalArgumentException("Operating System : " + os + " is not supported");
        }
        System.setProperty("webdriver.chrome.driver", chromePath);
    }


    /**
     * It returns the property value specified in either environment variable or configuration.properties
     * It gives priority to the property specified in Java environment variable For e.g. -Ddriver_id=FIREFOX
     *
     * @param key
     * @return
     */
    private static String determineEffectivePropertyValue(String key) {

        PropertiesReader propertiesReader = new PropertiesReader();

        if (null != System.getProperty(key)) {
            return System.getProperty(key);
        } else {
            return propertiesReader.getConfigProperty(key);
        }
    }
}