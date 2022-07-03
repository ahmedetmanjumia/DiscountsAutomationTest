package TestData;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class excelReader
{
    static FileInputStream fis = null;
    public FileInputStream getFileInputStream(String excelFileName) throws FileNotFoundException
    {
        String filePath = System.getProperty("user.dir")+"/src/test/java/TestData/"+excelFileName;
        File srcFile = new File(filePath);
        fis = new FileInputStream(srcFile);
        return fis;
    }

    public Object [][] getExcelData(String excelFileName, int sheetIndex) throws IOException {
        fis = getFileInputStream(excelFileName);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(sheetIndex);
        int totalNumOfRows = (sheet.getLastRowNum()+1);
        XSSFRow row = sheet.getRow(0);
        int totalNumOfCols = (row.getLastCellNum());
        String [][] arrayExcelData = new String[totalNumOfRows][totalNumOfCols];

        for (int i = 0; i< totalNumOfRows; i++)
        {
            for (int j = 0; j< totalNumOfCols; j++)
            {
                row = sheet.getRow(i);
                arrayExcelData[i][j] = row.getCell(j).toString();
            }
        }
        wb.close();
        return arrayExcelData;
    }
}
