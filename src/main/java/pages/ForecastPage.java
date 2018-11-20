package pages;

import org.openqa.selenium.By;
import utils.LogManager;
import utils.PropertiesReader;

/**
 * Created by yzosin on 04-Sep-17.
 */
public class ForecastPage {

    private static ForecastPage instance;
    public static ForecastPage Instance =(instance!=null) ? instance: new ForecastPage();

    By searchField = By.id("search_city");
    By searchButton = By.xpath(".//*[@id='form-search']//input[2]");

    BasePage page = BasePage.getInstance();

    public void searchCity() {
        page.open(PropertiesReader.getConfigProperty("URL"));
        page.waitForPageToLoad();
        LogManager.getLogger().info("Entering " + PropertiesReader.getConfigProperty("searchCity") + " into search field");
        page.findElement(searchField).sendKeys(PropertiesReader.getConfigProperty("searchCity"));
        LogManager.getLogger().info("Clicking search icon");
        page.findElement(searchButton).click();
    }

    public void takeScreenshotForecast() {
        LogManager.getLogger().info("Taking screenshot for page");
        page.takeScreenshot(BasePage.getInstance().getDriver(),"Sinoptik");
    }
}
