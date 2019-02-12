package pages;

import org.openqa.selenium.By;
import utils.LogManager;
import utils.PropertiesReader;

/**
 * Created by yzosin on 04-Sep-17.
 */
public class LoginPage extends BasePage {

    private static LoginPage instance;
    public static LoginPage Instance =(instance!=null) ? instance: new LoginPage();

    By username = By.xpath(".//input[@name='regular']");
    By password = By.xpath(".//input[@type='password']");
    By loginbtn = By.xpath(".//button[@type='submit']");
    By writeMail = By.xpath(".//*[@id='content']//button");
    //BasePage page = BasePage.getInstance();

    public void login(String URL, String userName, String userPassword) {

        open(URL);
        LogManager.getLogger().info("Entering username");
        findElement(username).sendKeys(userName);
        LogManager.getLogger().info("Entering password");
        findElement(password).sendKeys(userPassword);
        LogManager.getLogger().info("Clicking login");
        findElement(loginbtn).click();
        validateLogin();
        LogManager.getLogger().info("User is successfully logged in!");
        waitForPageToLoad();
        isPageLoaded();
    }

    public void validateLogin(){
        isElementPresentAndDisplay(writeMail);
    }
}
