package TestCases;

import TestData.excelReader;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

@Test
public class dataProvidersClass {

    @DataProvider(name = "ExcelData")
    public Object [][] readuserRegisterData() throws IOException
    {
        excelReader er = new excelReader();
        return er.getExcelData("loginData.xlsx",0);
    }

    @DataProvider(name = "discountData")
    public Object [][] readDiscountsData() throws IOException
    {
        excelReader er = new excelReader();
        return er.getExcelData("loginData.xlsx",1);
    }

    @DataProvider(name = "purchaseData")
    public Object [][] readPurchaseData() throws IOException
    {
        excelReader er = new excelReader();
        return er.getExcelData("loginData.xlsx",2);
    }
}
