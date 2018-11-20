package webdriver;

import org.openqa.selenium.WebDriver;
import java.net.MalformedURLException;

/**
 * @author yzosin
 * <p>
 * DriverSetup Interface make sure that every DiveryType has desired
 * capabilities defined and return the new instance of webdriver
 * </p>
 */
public interface DriverSetup {

    /**
     * <p>
     * It returns the instance of new webdriver and accepts one of the following objects: ChromeOptions, FirefoxOptions,
     * InternetExplorerOptions, SafariOptions
     * </p>
     *
     * @param instanceOptions - Describes a series of key/value pairs that encapsulate
     *                        aspects of a browser. Basically, the this parameter helps
     *                        to set properties for the chosen instance of WebDriver.
     * @return - the instance of desired capabilities
     * @throws MalformedURLException Thrown to indicate that a malformed URL has occurred. Either
     *                               no legal protocol could be found in a specification string or
     *                               the string could not be parsed.
     */
    <T> WebDriver getWebDriverObject(T instanceOptions) throws MalformedURLException;

    <T> T getDesiredDriverOptions();

}