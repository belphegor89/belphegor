package utils.datageneration.metadata;

public class MetaData {

        private String fileName;
        private String tag;
        private String sheet;
        private String value;
        private String description;

        public MetaData() {
            this.fileName = null;
            this.tag = null;
            this.sheet = null;
            this.value = null;
            this.description = null;
        }

        public MetaData(String filename, String tag, String sheet, String value, String description) {
            this.fileName = filename;
            this.tag = tag;
            this.sheet = sheet;
            this.value = value;
            this.description = description;
        }

        public String getFileName() {
            return this.fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getTag() {
            return this.tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getSheet() {
            return this.sheet;
        }

        public void setSheet(String sheet) {
            this.sheet = sheet;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
