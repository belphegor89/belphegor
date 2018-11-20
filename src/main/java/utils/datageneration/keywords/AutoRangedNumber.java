package utils.datageneration.keywords;

import utils.datageneration.generator.DataGenerator;
import java.time.LocalDate;

public class AutoRangedNumber implements AutoKeyword {
    public AutoRangedNumber() {
    }

    public String getKeyword() {
        return "RANGED_NUMBER";
    }

    public String generateData() {
        LocalDate year = new LocalDate();
        org.joda.time.LocalDate date = DataGenerator.getInstance().getDateBetween((long)year.getYear(), (long)(year.getYear() + 10));
        return date.toString("YYYY");
    }

    public String generateData(String modifier) {
        LocalDate now = new LocalDate();
        byte var5 = -1;
        switch(modifier.hashCode()) {
            case 67452:
                if (modifier.equals("DAY")) {
                    var5 = 0;
                }
                break;
            case 2719805:
                if (modifier.equals("YEAR")) {
                    var5 = 2;
                }
                break;
            case 73542240:
                if (modifier.equals("MONTH")) {
                    var5 = 1;
                }
        }

        org.joda.time.LocalDate date;
        switch(var5) {
            case 0:
                date = DataGenerator.getInstance().getDateBetween((long)now.getDayOfMonth(), (long)(now.getDayOfMonth() + 7));
                return date.toString("dd");
            case 1:
                date = DataGenerator.getInstance().getDateBetween((long)now.getMonth(), (long)(now.getMonth() + 12));
                return date.toString("mm");
            case 2:
                date = DataGenerator.getInstance().getDateBetween((long)now.getYear(), (long)(now.getYear() + 10));
                return date.toString("YYYY");
            default:
                return null;
        }
    }
}
