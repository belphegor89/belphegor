package utils.datageneration.keywords;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class KeywordManager {
    private final Path filePath;
    private List<Keyword> keywordDictionary;

    public KeywordManager(Path filePath) {
        this.filePath = filePath;
        this.keywordDictionary = new ArrayList();
        this.readFile();
    }

    public List<Keyword> getKeywordDictionary() {
        return this.keywordDictionary;
    }

    public Keyword getKeyword(String keyword) {
        String tKeyword;
        String tModifier;
        if (keyword.contains("|")) {
            String[] keywordArray = keyword.split("\\|");
            tKeyword = keywordArray[0];
            tModifier = keywordArray[1];
        } else {
            tKeyword = keyword;
            tModifier = "NONE";
        }

        Iterator var6 = this.keywordDictionary.iterator();

        Keyword k;
        do {
            if (!var6.hasNext()) {
                return null;
            }

            k = (Keyword)var6.next();
        } while(!k.getName().toLowerCase().equals(tKeyword.toLowerCase()));

        k.setModifier(tModifier);
        return k;
    }

    public boolean isKeyword(String keyword) {
        String tKeyword;
        if (keyword.contains("|")) {
            String[] keywordArray = keyword.split("\\|");
            tKeyword = keywordArray[0];
        } else {
            tKeyword = keyword;
        }

        Iterator var5 = this.keywordDictionary.iterator();

        Keyword k;
        do {
            if (!var5.hasNext()) {
                return false;
            }

            k = (Keyword)var5.next();
        } while(!k.getName().equals(tKeyword));

        return true;
    }

    public int keywordCount() {
        return this.keywordDictionary.size();
    }

    public void setKeywordDictionary(List<Keyword> keywordDictionary) {
        this.keywordDictionary = keywordDictionary;
    }

    private void readFile() {
        File folder = new File(this.filePath.toString());
        File[] fileList = folder.listFiles((pathname) -> {
            return !pathname.isHidden();
        });
        File[] var3 = fileList;
        int var4 = fileList.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            File file = var3[var5];
            Path filePath = Paths.get(file.getAbsolutePath());
            this.readXLSXFile(filePath);
        }

    }

    private void readXLSFile(String FILE_NAME, String FOLDER_NAME) throws IOException {
        try {
            File file = new File(FILE_NAME);
            String filePath = file.getAbsoluteFile().getParentFile().getAbsolutePath() + "\\" + FOLDER_NAME + "\\" + FILE_NAME;
            FileInputStream excelFile = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            this.iterateExcel(rowIterator);
            excelFile.close();
        } catch (FileNotFoundException var9) {
            throw new FileNotFoundException("Invalid generator file! Please check the file path.");
        } catch (IOException var10) {
            throw new IOException("Invalid generator format! Please check the file generator.");
        }
    }

    private void readXLSXFile(Path filePath) {
        try {
            Workbook workbook = new XSSFWorkbook(Files.newInputStream(filePath));
            workbook.setMissingCellPolicy(Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

            for(int i = 0; i < workbook.getNumberOfSheets(); ++i) {
                Sheet dataSheet = workbook.getSheetAt(i);
                Iterator<Row> rowIterator = dataSheet.iterator();
                this.iterateExcel(rowIterator);
            }
        } catch (Throwable var6) {
            System.err.println(var6.getMessage());
        }

    }

    private void iterateExcel(Iterator<Row> rowIterator) {
        new ArrayList();

        while(true) {
            Row curRow;
            do {
                if (!rowIterator.hasNext()) {
                    return;
                }

                curRow = (Row)rowIterator.next();
            } while(curRow.getRowNum() == 0);

            Iterator<Cell> cellIterator = curRow.iterator();
            List keywordVals = new ArrayList();
            Keyword tKeyword = new Keyword();

            while(cellIterator.hasNext()) {
                Cell curCell = (Cell)cellIterator.next();
                switch(curCell.getCellType()) {
                    case 0:
                        keywordVals.add(curCell.getNumericCellValue());
                        break;
                    case 1:
                        keywordVals.add(curCell.getStringCellValue());
                }
            }

            tKeyword.setName((String)keywordVals.get(0));
            tKeyword.setType((String)keywordVals.get(1));
            if (tKeyword.getType().equals("FILE")) {
                tKeyword.setFileName((String)keywordVals.get(2));
            } else {
                tKeyword.setDataSource(DataSource.valueOf((String)keywordVals.get(2)));
            }

            if (keywordVals.size() > 3) {
                if (keywordVals.size() == 4) {
                    tKeyword.setDescription((String)keywordVals.get(3));
                } else {
                    tKeyword.setDataType((String)keywordVals.get(3));
                    tKeyword.setDescription((String)keywordVals.get(4));
                }
            }

            this.keywordDictionary.add(tKeyword);
        }
    }
}
