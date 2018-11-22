package utils.datageneration.generator;

public class Address {
    public static Address DEFAULT_ADDRESS = new Address() {
        public String getStreet() {
            return "Shevchenko Str 1";
        }


        public String getZipCode() {
            return "46001";
        }

        public String getFullAddress() {
            return "Shevchenko Str 1, 46001";
        }

        public String getCity() {
            return "Ternopil";
        }
    };

    private String street;
    private String city;
    private String zipCode;
    private String fullAddress;

    public Address() {
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getFullAddress() {
        return this.fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String toString() {
        return this.fullAddress;
    }
}
