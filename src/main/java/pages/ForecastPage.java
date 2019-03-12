package pages;

import org.openqa.selenium.By;
import utils.LogManager;
import utils.PropertiesReader;

/**
 * Created by yzosin on 04-Sep-17.
 */
public class ForecastPage extends BasePage {

    private static ForecastPage instance;
    public static ForecastPage Instance = (instance != null) ? instance : new ForecastPage();

    By searchField = By.id("search_city");
    By searchButton = By.xpath(".//*[@id='form-search']//input[2]");

    //BasePage page = BasePage.getInstance();

    public void searchCity(String URL, String searchCity) {
        open(URL);
        waitForPageToLoad();
        LogManager.getLogger().info("Entering " + searchCity + " into search field");
        findElement(searchField).sendKeys(searchCity);
        LogManager.getLogger().info("Clicking search icon");
        findElement(searchButton).click();
    }

    public void takeScreenshotForecast() {
        LogManager.getLogger().info("Taking screenshot for page");
        takeScreenshot(driver(), "Sinoptik");
    }
}
