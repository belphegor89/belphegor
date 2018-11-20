package utils.datageneration.keywords;

import utils.datageneration.Parser;

public class Keyword {
    private String name;
    private String type;
    private DataSource dataSource;
    private String fileName;
    private String dataType;
    private String data;
    private String description;
    private String modifier;

    public Keyword() {
        this.name = null;
        this.type = null;
        this.dataSource = null;
        this.fileName = null;
        this.dataType = null;
        this.data = null;
        this.description = null;
        this.modifier = null;
    }

    public Keyword(Keyword keyword) {
        this.name = keyword.name;
        this.type = keyword.type;
        this.dataSource = keyword.dataSource;
        this.fileName = keyword.fileName;
        this.dataType = keyword.dataType;
        this.data = keyword.data;
        this.description = keyword.description;
        this.modifier = keyword.modifier;
    }

    public String convertKeyword() {
        Parser parser = new Parser();
        String var2 = this.type.toUpperCase();
        byte var3 = -1;
        switch(var2.hashCode()) {
            case -2131401768:
                if (var2.equals("FUNCTION")) {
                    var3 = 0;
                }
                break;
            case 2157948:
                if (var2.equals("FILE")) {
                    var3 = 1;
                }
        }

        switch(var3) {
            case 0:
                parser.parse(this.dataSource, this.modifier);
                return parser.getAutoKeyword();
            case 1:
                parser.parse(this.fileName);
                return parser.getRandomData();
            default:
                return null;
        }
    }

    public String convertKeyword(int atIndex) {
        Parser parser = new Parser();
        String var3 = this.type.toUpperCase();
        byte var4 = -1;
        switch(var3.hashCode()) {
            case -2131401768:
                if (var3.equals("FUNCTION")) {
                    var4 = 0;
                }
                break;
            case 2157948:
                if (var3.equals("FILE")) {
                    var4 = 1;
                }
        }

        switch(var4) {
            case 0:
                parser.parse(this.dataSource, this.modifier);
                return parser.getAutoKeyword();
            case 1:
                parser.parse(this.fileName);
                return parser.getRandomData();
            default:
                return null;
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DataSource getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModifier() {
        return this.modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }
}
