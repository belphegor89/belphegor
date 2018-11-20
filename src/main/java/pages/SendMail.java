package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import utils.LogManager;
import utils.PropertiesReader;
import utils.Tools;

import static pages.BasePage.logger;

/**
 * Created by yzosin on 04-Sep-17.
 */
public class SendMail {

    private static SendMail instance;
    public static SendMail Instance = (instance != null) ? instance : new SendMail();

    String writeTo = PropertiesReader.getConfigProperty("recipient");

    By writeMail = By.xpath(".//*[@id='content']//button");
    By recipient = By.xpath(".//div[@class='sendmsg__form-label']/div[4]");
    By subject = By.xpath(".//input[@name='subject']");
    By file = By.id("file-to-upload");
    By sendButton = By.xpath(".//button[@class='default send']");
    By frame = By.xpath("//iframe[contains(@title,'Текстове')]");
    By text = By.id("tinymce");
    By message = By.xpath(".//div[@class='sendmsg__ads-ready']");

    BasePage page = BasePage.getInstance();

    public void sendMailWithFile() {

        LogManager.getLogger().info("Clicking Write Mail link");
        page.findElement(writeMail).click();
        LogManager.getLogger().info("Entering recipient: " + writeTo);
        page.isElementPresentAndDisplay(subject);
        Actions actions = new Actions(BasePage.getInstance().getDriver());
        WebElement element = BasePage.getInstance().getDriver().findElement(recipient);
        actions.moveToElement(element);
        actions.click();
        actions.sendKeys(writeTo);
        actions.build().perform();
        LogManager.getLogger().info("Entering subject");
        page.findElement(subject).sendKeys("Weather report for " + Tools.getCurrentTime());
        LogManager.getLogger().info("Adding weather report to mail");
        page.fileUpload(file);
        page.switchToFrame(frame);
        LogManager.getLogger().info("Adding mail body");
        page.findElement(text).sendKeys("Today's forecast");
        page.switchToDefaultContent();
        LogManager.getLogger().info("Sending mail");
        page.findElement(sendButton).click();
        try {
            page.acceptAlertMessage();
        } catch (NoAlertPresentException Ex) {
            LogManager.getLogger().info("No alert message found");
        }
        LogManager.getLogger().info("Mail sent!");
    }

    public boolean validateMessageSent() {
        String validationMessage = page.findElement(message).getText();
        Assert.assertEquals(validationMessage, "Ваш лист надіслано\n" +
                        "Написати щеПовернутись у вхідні",
                "Success message doesn't match the expected one");
        return true;
    }
}
