package utils.datageneration.keywords;

public class AutoLastName implements AutoKeyword {
    public AutoLastName() {
    }

    public String getKeyword() {
        return "LAST_NAME";
    }

    public String generateData() {
        return DataGenerator.getInstance().getLastName();
    }

    public String generateData(String modifier) {
        return modifier.equals("NONE") ? DataGenerator.getInstance().getLastName() : "Need More Data...";
    }
}
