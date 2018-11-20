package utils.datageneration.keywords;

import utils.datageneration.generator.DataGenerator;

public class AutoZipCode implements AutoKeyword{
    public AutoZipCode() {
    }

    public String getKeyword() {
        return "ZIP_CODE";
    }

    public String generateData() {
        String ZIP = DataGenerator.getInstance().bothify("#####");
        return ZIP.toUpperCase();
    }

    public String generateData(String modifier) {
        byte var4 = -1;
        switch(modifier.hashCode()) {
            case 53:
                if (modifier.equals("5")) {
                    var4 = 1;
                }
                break;
            case 1567:
                if (modifier.equals("10")) {
                    var4 = 2;
                }
                break;
            case 2402104:
                if (modifier.equals("NONE")) {
                    var4 = 0;
                }
        }

        String ZIP;
        switch(var4) {
            case 0:
                return DataGenerator.getInstance().getAddress().getZipCode();
            case 1:
                ZIP = DataGenerator.getInstance().getAddress().getZipCode();
                if (ZIP.length() > 5) {
                    return ZIP.substring(0, 5);
                }

                return ZIP;
            case 2:
                return DataGenerator.getInstance().numerify("##########");
            default:
                ZIP = DataGenerator.getInstance().bothify(modifier.toUpperCase());
                return ZIP.toUpperCase();
        }
    }
}
