package utils.datageneration.keywords;

import utils.datageneration.generator.DataGenerator;

public class AutoPhoneNumber implements AutoKeyword {
    public AutoPhoneNumber() {
    }

    public String getKeyword() {
        return "PHONE_NUMBER";
    }

    public String generateData() {
        return DataGenerator.getInstance().getTelephoneNumber();
    }

    public String generateData(String modifier) {
        return modifier.equals("NONE") ? DataGenerator.getInstance().getTelephoneNumber() : "Need more phone generator...";
    }
}
