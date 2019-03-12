package utils.datageneration.metadata;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MetaDataManager {
    private final Path filePath;
    private String metaPrefix;
    private List<MetaData> metaDictionary;

    public MetaDataManager(Path filePath, String prefix) {
        this.filePath = filePath;
        this.metaPrefix = prefix;
        this.metaDictionary = new ArrayList();
        this.readFile();
    }

    public MetaData getMetaData(String tag) {
        String[] tagIDs = tag.split("_");
        String fileName = tagIDs[1];
        String page = tagIDs[2];
        String tagName = this.metaPrefix + tagIDs[3];
        Iterator var6 = this.metaDictionary.iterator();

        MetaData m;
        do {
            if (!var6.hasNext()) {
                return null;
            }

            m = (MetaData)var6.next();
        } while(!m.getFileName().toUpperCase().contains(fileName.toUpperCase()) || !m.getSheet().toUpperCase().equals(page.toUpperCase()) || !m.getTag().toUpperCase().equals(tagName.toUpperCase()));

        return m;
    }

    public boolean isMetaData(String metaData) {
        int preSize = this.metaPrefix.length();
        String metaPre = metaData.substring(0, preSize);
        return this.metaPrefix.toUpperCase().equals(metaPre.toUpperCase());
    }

    public int count() {
        return this.metaDictionary.size();
    }

    public List<MetaData> getMetaDictionary() {
        return this.metaDictionary;
    }

    public void setMetaDictionary(List<MetaData> metaDictionary) {
        this.metaDictionary = metaDictionary;
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

        try {
            this.writeToCSV();
        } catch (FileNotFoundException var8) {
            var8.printStackTrace();
        }

    }

    private void readXLSXFile(Path filePath) {
        try {
            Workbook workbook = new XSSFWorkbook(Files.newInputStream(filePath));
            workbook.setMissingCellPolicy(Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

            for(int i = 0; i < workbook.getNumberOfSheets(); ++i) {
                Sheet dataSheet = workbook.getSheetAt(i);
                Iterator rowIterator = dataSheet.iterator();

                while(rowIterator.hasNext()) {
                    Row row = (Row)rowIterator.next();
                    if (row.getRowNum() != 0) {
                        Iterator<Cell> cellIterator = row.cellIterator();
                        List metaVals = new ArrayList();
                        MetaData metaData = new MetaData();
                        metaData.setFileName(filePath.getFileName().toString());
                        metaData.setSheet(dataSheet.getSheetName());

                        while(cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            switch(cell.getCellType()) {
                                case 0:
                                    double numVal = cell.getNumericCellValue();
                                    int numValInt = (int)numVal;
                                    metaVals.add(Integer.toString(numValInt));
                                    break;
                                case 1:
                                    metaVals.add(cell.getStringCellValue());
                            }
                        }

                        String upperTag = ((String)metaVals.get(0)).toUpperCase();
                        metaData.setTag(this.metaPrefix + upperTag);
                        metaData.setValue((String)metaVals.get(1));
                        metaData.setDescription((String)metaVals.get(2));
                        this.metaDictionary.add(metaData);
                    }
                }
            }
        } catch (Throwable var14) {
            System.err.println(var14.getMessage());
        }

    }

    private void writeToCSV() throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter("MetaData_Dictionary.csv");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TAG");
        stringBuilder.append(",");
        stringBuilder.append("VALUE");
        stringBuilder.append(",");
        stringBuilder.append("DESCRIPTION");
        stringBuilder.append("\n");
        Iterator var3 = this.metaDictionary.iterator();

        while(var3.hasNext()) {
            MetaData metaData = (MetaData)var3.next();
            stringBuilder.append(metaData.getTag());
            stringBuilder.append(",");
            stringBuilder.append(metaData.getValue());
            stringBuilder.append(",");
            stringBuilder.append(metaData.getDescription());
            stringBuilder.append("\n");
        }

        printWriter.write(stringBuilder.toString());
        printWriter.close();
    }
}
