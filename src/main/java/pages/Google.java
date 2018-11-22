package pages;

import utils.LogManager;

public class Google  extends BasePage {

    private static Google instance;
    public static Google Instance =(instance!=null) ? instance: new Google();

    public void start(){
        open("https://www.google.com/");
        LogManager.getLogger().info("Successfully navigated to web page!!!");
    }
}
