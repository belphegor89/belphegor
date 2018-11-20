package utils.datageneration.generator;

public class CreditCard {
    private String number;
    private String creditCardName;
    private CreditCard.CreditCardType creditCardType;
    public static final CreditCard DEFAULT_CREDIT_CARD = new CreditCard() {
        public String getNumber() {
            return "4556 3447 5526 0439";
        }

        public CreditCard.CreditCardType getCreditCardType() {
            return CreditCard.CreditCardType.VISA;
        }
    };

    public CreditCard() {
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public CreditCard.CreditCardType getCreditCardType() {
        return this.creditCardType;
    }

    private CreditCard.CreditCardType getCreditCardType(String type) {
        if (type.endsWith("MasterCard")) {
            return CreditCard.CreditCardType.MASTERCARD;
        } else if (type.endsWith("Visa")) {
            return CreditCard.CreditCardType.VISA;
        } else if (type.endsWith("Express")) {
            return CreditCard.CreditCardType.AMEX;
        } else if (type.endsWith("Discover")) {
            return CreditCard.CreditCardType.DISCOVER;
        } else if (type.endsWith("Diners Club")) {
            return CreditCard.CreditCardType.DINERS;
        } else if (type.endsWith("JCB")) {
            return CreditCard.CreditCardType.JCB;
        } else {
            return type.endsWith("Express Corporate") ? CreditCard.CreditCardType.AMEX_CORPORATE : CreditCard.CreditCardType.VISA;
        }
    }

    private void setCreditCardType(CreditCard.CreditCardType creditCardType) {
        this.creditCardType = creditCardType;
    }

    public void setCreditCardName(String name) {
        this.creditCardName = name;
        this.setCreditCardType(this.getCreditCardType(name));
    }

    public static enum CreditCardType {
        MASTERCARD,
        VISA,
        AMEX,
        DISCOVER,
        AMEX_CORPORATE,
        DINERS,
        JCB;

        private CreditCardType() {
        }
    }
}
