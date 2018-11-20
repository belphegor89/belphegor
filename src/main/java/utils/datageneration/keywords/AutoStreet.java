package utils.datageneration.keywords;

import utils.datageneration.generator.DataGenerator;

import java.util.Random;

public class AutoStreet implements  AutoKeyword {
    public AutoStreet() {
    }

    public String getKeyword() {
        return "STREET";
    }

    public String generateData() {
        String num = DataGenerator.getInstance().numerify("###");
        String retNum = "";
        if (num.charAt(0) == '0') {
            Random random = new Random();
            int randNum = random.nextInt(9) + 1;
            retNum = num.replace("0", Integer.toString(randNum));
        } else {
            retNum = num;
        }

        return retNum + " " + DataGenerator.getInstance().getStreet();
    }

    public String generateData(String modifier) {
        String num;
        String retNum;
        Random random;
        int randNum;
        if (modifier.equals("NONE")) {
            num = DataGenerator.getInstance().numerify("###");
            if (num.charAt(0) == '0') {
                random = new Random();
                randNum = random.nextInt(9) + 1;
                retNum = num.replace("0", Integer.toString(randNum));
            } else {
                retNum = num;
            }

            return retNum + " " + DataGenerator.getInstance().getStreet();
        } else {
            num = DataGenerator.getInstance().numerify(modifier);
            if (num.charAt(0) == '0') {
                random = new Random();
                randNum = random.nextInt(9) + 1;
                retNum = num.replace("0", Integer.toString(randNum));
            } else {
                retNum = num;
            }

            return retNum + " " + DataGenerator.getInstance().getStreet();
        }
    }
}
