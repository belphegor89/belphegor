package utils.datageneration.keywords;

import utils.datageneration.generator.DataGenerator;

public class AutoCity implements AutoKeyword {
    public AutoCity() {
    }

    public String getKeyword() {
        return "CITY";
    }

    public String generateData() {
        return DataGenerator.getInstance().getAddress().getCity();
    }

    public String generateData(String modifier) {
        return modifier.equals("NONE") ? DataGenerator.getInstance().getAddress().getCity() : "Need more city generator...";
    }
}
