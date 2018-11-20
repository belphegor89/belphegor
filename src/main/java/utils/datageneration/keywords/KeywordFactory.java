package utils.datageneration.keywords;

public class KeywordFactory {
    public KeywordFactory() {
    }

    public AutoKeyword generateKeyword(DataSource function) {
        switch(function) {
            case FIRST_NAME:
                return new AutoFirstName();
            case LAST_NAME:
                return new AutoLastName();
            case CITY:
                return new AutoCity();
            case STREET:
                return new AutoStreet();
            case ZIP_CODE:
                return new AutoZipCode();
            case ADDRESS:
                return new AutoAddress();
            case DOB:
                return new AutoDOB();
            case TODAY:
                return new AutoToday();
            case PHONE_NUMBER:
                return new AutoPhoneNumber();
            case FORMATTED_NUMBER:
                return new AutoPhoneNumber();
            case RANGED_NUMBER:
                return new AutoRangedNumber();
            default:
                return new AutoFirstName();
        }
    }
}
