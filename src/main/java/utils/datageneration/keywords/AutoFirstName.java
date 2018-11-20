package utils.datageneration.keywords;

public class AutoFirstName implements AutoKeyword {
    public AutoFirstName() {
    }

    public String getKeyword() {
        return "FIRST_NAME";
    }

    public String generateData() {
        return (String)DataGenerator.getInstance().getFirstName().getLeft();
    }

    public String generateData(String modifier) {
        byte var3 = -1;
        switch(modifier.hashCode()) {
            case 2358797:
                if (modifier.equals("MALE")) {
                    var3 = 1;
                }
                break;
            case 2402104:
                if (modifier.equals("NONE")) {
                    var3 = 0;
                }
                break;
            case 2070122316:
                if (modifier.equals("FEMALE")) {
                    var3 = 2;
                }
        }

        switch(var3) {
            case 0:
                return (String)DataGenerator.getInstance().getFirstName().getLeft();
            case 1:
                return DataGenerator.getInstance().getGenderFirstName(Gender.MALE);
            case 2:
                return DataGenerator.getInstance().getGenderFirstName(Gender.FEMALE);
            default:
                return DataGenerator.getInstance().getName();
        }
    }
}
