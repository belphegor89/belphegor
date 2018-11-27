import org.testng.annotations.Test;
import pages.Google;
import utils.BaseUITest;
import utils.Reporter;

public class GoogleTest extends BaseUITest {


    @Test
    public void execute() {

        Google google = Google.Instance;
        Reporter.log("Starting test for Google");
        google.start();
        Reporter.log("Navigated to google.com");
    }
}
