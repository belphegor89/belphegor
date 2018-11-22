package utils.datageneration.keywords;
import utils.datageneration.generator.*;

public class AutoAddress implements AutoKeyword {
    public AutoAddress() {
    }

    public String getKeyword() {
        return "ADDRESS";
    }

    public String generateData() {
        Address address = DataGenerator.getInstance().getAddress();
        return address.getStreet() + ", " + address.getCity() + ", " + ", " + address.getZipCode();
    }

    public String generateData(String modifier) {
        if (modifier.equals("NONE")) {
            Address address = DataGenerator.getInstance().getAddress();
            return address.getStreet() + ", " + address.getCity() + ", " + ", " + address.getZipCode();
        } else {
            return "Need more address generator...";
        }
    }
}
