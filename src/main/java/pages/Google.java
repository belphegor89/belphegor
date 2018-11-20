package pages;

import utils.LogManager;

public class Google  {

    private static Google instance;
    public static Google Instance =(instance!=null) ? instance: new Google();

    public void start(){
        BasePage page = BasePage.getInstance();
        page.open("https://www.google.com/");
        LogManager.getLogger().info("Successfully navigated to web page!!!");
    }
}
