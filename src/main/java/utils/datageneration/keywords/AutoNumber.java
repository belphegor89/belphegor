package utils.datageneration.keywords;

public class AutoNumber implements AutoKeyword {
    public AutoNumber() {
    }

    public String getKeyword() {
        return "NUMBER";
    }

    public String generateData() {
        return DataGenerator.getInstance().numerify("#");
    }

    public String generateData(String modifier) {
        String retVal = DataGenerator.getInstance().numerify(modifier);
        if (retVal.charAt(0) == '0') {
            retVal = this.replace(modifier, retVal);
        }

        if (retVal.charAt(1) == '0') {
            retVal = this.replace(modifier, retVal);
        }

        return retVal;
    }

    private String replace(String modifier, String retVal) {
        boolean replaceZero = false;

        while(!replaceZero) {
            retVal = DataGenerator.getInstance().numerify(modifier);
            if (retVal.charAt(0) != '0') {
                replaceZero = true;
            }
        }

        return retVal;
    }
}
