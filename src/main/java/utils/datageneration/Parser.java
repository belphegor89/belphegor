package utils.datageneration;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import utils.datageneration.keywords.*;
import utils.datageneration.metadata.MetaData;


    public class Parser {
        public static String autoKeyword;
        public static String fileData;
        public static String metadata;
        private List<String> fileDataList;

        public Parser() {
        }

        public void parse(DataSource keyword) {
            KeywordFactory keywordFactory = new KeywordFactory();
            AutoKeyword aKeyword = keywordFactory.generateKeyword(keyword);
            autoKeyword = aKeyword.generateData("NONE");
        }

        public void parse(DataSource keyword, String modifier) {
            KeywordFactory keywordFactory = new KeywordFactory();
            AutoKeyword aKeyword = keywordFactory.generateKeyword(keyword);
            autoKeyword = aKeyword.generateData(modifier.toUpperCase());
        }

        public void parse(Keyword k) {
            KeywordFactory keywordFactory = new KeywordFactory();
            AutoKeyword aKeyword = keywordFactory.generateKeyword(k.getDataSource());
            autoKeyword = aKeyword.generateData(k.getModifier().toUpperCase());
        }

        public void parse(MetaData m) {
            metadata = m.getValue();
        }

        public void parse(String fileName) {
            if (this.fileDataList == null) {
                this.fileDataList = new ArrayList();
            }

            try {
                String file = (new File(fileName)).getAbsolutePath();
                FileInputStream fileInputStream = new FileInputStream(file);
                DataInputStream dataInputStream = new DataInputStream(fileInputStream);

                while(dataInputStream.available() > 0) {
                    String data = dataInputStream.readLine();
                    this.fileDataList.add(data);
                }
            } catch (Exception var6) {
                var6.printStackTrace();
            }

            fileData = this.getRandomFileData();
        }

        public void parse(String fileName, int atIndex) {
            if (this.fileDataList == null) {
                this.fileDataList = new ArrayList();
            }

            try {
                String file = (new File(fileName)).getAbsolutePath();
                FileInputStream fileInputStream = new FileInputStream(file);
                DataInputStream dataInputStream = new DataInputStream(fileInputStream);

                while(dataInputStream.available() > 0) {
                    String data = dataInputStream.readLine();
                    this.fileDataList.add(data);
                }
            } catch (Exception var7) {
                var7.printStackTrace();
            }

            fileData = this.getFileDataFromIndex(atIndex);
        }

        public String getAutoKeyword() {
            return autoKeyword;
        }

        public String getRandomData() {
            return fileData;
        }

        public static String getMetadata() {
            return metadata;
        }

        private String getRandomFileData() {
            Random random = new Random();
            int randomIndex = random.nextInt(this.fileDataList.size());
            String randomData = (String)this.fileDataList.get(randomIndex);
            return randomData;
        }

        private String getFileDataFromIndex(int index) {
            String randomData = (String)this.fileDataList.get(index);
            return randomData;
        }
    }
