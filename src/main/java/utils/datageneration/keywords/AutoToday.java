package utils.datageneration.keywords;

import utils.datageneration.generator.DataGenerator;

import java.time.LocalDate;

public class AutoToday implements AutoKeyword{
    public AutoToday() {
    }

    public String getKeyword() {
        return "DOB";
    }

    public String generateData() {
        org.joda.time.LocalDate DOB = DataGenerator.getInstance().getToday().toLocalDate();
        return DOB.toString("MM/dd/YYYY");
    }

    public String generateData(String modifier) {
        String retVal = "";
        org.joda.time.LocalDate DOB;
        if (modifier.equals("NONE")) {
            DOB = DataGenerator.getInstance().getToday().toLocalDate();
            retVal = DOB.toString("MMMM dd, YYYY");
        } else {
            String[] mods = modifier.split("_");
            String format = mods[0];
            if (mods.length == 1) {
                DOB = DataGenerator.getInstance().getToday().toLocalDate();
            } else {
                int modLen = mods[1].length();
                String modNum = mods[1].substring(0, modLen - 1);
                String modTime = String.valueOf(mods[1].charAt(modLen - 1));
                int early;
                if (modNum.contains("-")) {
                    modNum = modNum.replace("-", "").trim();
                    early = Integer.parseInt(modNum);
                    DOB = DataGenerator.getInstance().getTodayMinus(early, modTime).toLocalDate();
                } else {
                    early = Integer.parseInt(modNum);
                    DOB = DataGenerator.getInstance().getTodayPlus(early, modTime).toLocalDate();
                }
            }

            String var10 = format.toUpperCase();
            byte var11 = -1;
            switch(var10.hashCode()) {
                case -2046892343:
                    if (var10.equals("M-D-YY")) {
                        var11 = 24;
                    }
                    break;
                case -2045043379:
                    if (var10.equals("M/D/YY")) {
                        var11 = 13;
                    }
                    break;
                case -2024983465:
                    if (var10.equals("MDYYYY")) {
                        var11 = 10;
                    }
                    break;
                case -2017317568:
                    if (var10.equals("MMDDYY")) {
                        var11 = 20;
                    }
                    break;
                case -1611929504:
                    if (var10.equals("MMDDYYYY")) {
                        var11 = 8;
                    }
                    break;
                case -1460335360:
                    if (var10.equals("MM/DD/YYYY")) {
                        var11 = 0;
                    }
                    break;
                case -949231936:
                    if (var10.equals("DD/MM/YYYY")) {
                        var11 = 2;
                    }
                    break;
                case -734391571:
                    if (var10.equals("D/M/YYYY")) {
                        var11 = 3;
                    }
                    break;
                case -652835776:
                    if (var10.equals("MM-DD-YYYY")) {
                        var11 = 4;
                    }
                    break;
                case -165510432:
                    if (var10.equals("DD-MM-YY")) {
                        var11 = 17;
                    }
                    break;
                case -141732352:
                    if (var10.equals("DD-MM-YYYY")) {
                        var11 = 5;
                    }
                    break;
                case -108250208:
                    if (var10.equals("DD/MM/YY")) {
                        var11 = 14;
                    }
                    break;
                case 2102633:
                    if (var10.equals("DMYY")) {
                        var11 = 23;
                    }
                    break;
                case 2362103:
                    if (var10.equals("MDYY")) {
                        var11 = 22;
                    }
                    break;
                case 31482793:
                    if (var10.equals("M-D-YYYY")) {
                        var11 = 6;
                    }
                    break;
                case 751022176:
                    if (var10.equals("DDMMYYYY")) {
                        var11 = 9;
                    }
                    break;
                case 1783721321:
                    if (var10.equals("D-M-YYYY")) {
                        var11 = 7;
                    }
                    break;
                case 1808337197:
                    if (var10.equals("M/D/YYYY")) {
                        var11 = 1;
                    }
                    break;
                case 1866381654:
                    if (var10.equals("MM-D-YY")) {
                        var11 = 18;
                    }
                    break;
                case 1990680713:
                    if (var10.equals("D-M-YY")) {
                        var11 = 19;
                    }
                    break;
                case 1992529677:
                    if (var10.equals("D/M/YY")) {
                        var11 = 15;
                    }
                    break;
                case 2011952448:
                    if (var10.equals("DDMMYY")) {
                        var11 = 21;
                    }
                    break;
                case 2020633161:
                    if (var10.equals("DMYYYY")) {
                        var11 = 11;
                    }
                    break;
                case 2023899424:
                    if (var10.equals("MM-DD-YY")) {
                        var11 = 16;
                    }
                    break;
                case 2081159648:
                    if (var10.equals("MM/DD/YY")) {
                        var11 = 12;
                    }
            }

            switch(var11) {
                case 0:
                    retVal = DOB.toString("MM/dd/YYYY");
                    break;
                case 1:
                    retVal = DOB.toString("M/d/YYYY");
                    break;
                case 2:
                    retVal = DOB.toString("dd/MM/YYYY");
                    break;
                case 3:
                    retVal = DOB.toString("d/M/YYYY");
                    break;
                case 4:
                    retVal = DOB.toString("MM-dd-YYYY");
                    break;
                case 5:
                    retVal = DOB.toString("dd-MM-YYYY");
                    break;
                case 6:
                    retVal = DOB.toString("M-d-YYYY");
                    break;
                case 7:
                    retVal = DOB.toString("d-M-YYYY");
                    break;
                case 8:
                    retVal = DOB.toString("MMddYYYY");
                    break;
                case 9:
                    retVal = DOB.toString("ddMMYYYY");
                    break;
                case 10:
                    retVal = DOB.toString("MdYYYY");
                    break;
                case 11:
                    retVal = DOB.toString("dMYYYY");
                    break;
                case 12:
                    retVal = DOB.toString("MM/dd/YY");
                    break;
                case 13:
                    retVal = DOB.toString("M/d/YY");
                    break;
                case 14:
                    retVal = DOB.toString("dd/MM/YY");
                    break;
                case 15:
                    retVal = DOB.toString("d/M/YY");
                    break;
                case 16:
                    retVal = DOB.toString("MM-dd-YY");
                    break;
                case 17:
                    retVal = DOB.toString("dd-MM-YY");
                    break;
                case 18:
                    retVal = DOB.toString("MM-d-YY");
                    break;
                case 19:
                    retVal = DOB.toString("d-M-YY");
                    break;
                case 20:
                    retVal = DOB.toString("MMddYY");
                    break;
                case 21:
                    retVal = DOB.toString("ddMMYY");
                    break;
                case 22:
                    retVal = DOB.toString("MdYY");
                    break;
                case 23:
                    retVal = DOB.toString("dMYY");
                    break;
                case 24:
                    retVal = DOB.toString("M-d-YY");
            }
        }

        return retVal;
    }
}
