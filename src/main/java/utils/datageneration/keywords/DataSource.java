package utils.datageneration.keywords;

public enum DataSource {
    FIRST_NAME("FIRST_NAME"),
    MIDDLE_NAME("MIDDLE_NAME"),
    LAST_NAME("LAST_NAME"),
    CITY("CITY"),
    STATE("STATE"),
    STREET("STREET"),
    ZIP_CODE("ZIP_CODE"),
    ADDRESS("ADDRESS"),
    DOB("DOB"),
    TODAY("TODAY"),
    PHONE_NUMBER("PHONE_NUMBER"),
    FORMATTED_NUMBER("FORMATTED_NUMBER"),
    RANGED_NUMBER("RANGED_NUMBER");

    private String DATA_SOURCE;

    private DataSource(String dataSource) {
        this.DATA_SOURCE = dataSource;
    }

    public String getDataSource() {
        return this.DATA_SOURCE;
    }

    public void setDataSource(String ds) {
        this.DATA_SOURCE = ds;
    }

    public void setFileName(String fileName) {
        this.setDataSource(fileName);
    }

    public DataSource file() {
        return this;
    }
}
