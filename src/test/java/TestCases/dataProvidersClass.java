package TestCases;

import TestData.excelReader;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

@Test
public class dataProvidersClass {

    @DataProvider(name = "ExcelData")
    public static Object [][] readuserRegisterData() throws IOException
    {
        excelReader er = new excelReader();
        return er.getExcelData("loginData.xlsx",0);
    }

    @DataProvider(name = "PurchaseWithDiscountData")
    public Object [][] readPurchaseWithDiscountData() throws IOException
    {
        excelReader er = new excelReader();
        return er.getExcelData("purchaseWithDiscountData.xlsx",0);
    }
    @DataProvider(name = "PurchaseWithDifferentShopData")
    public Object [][] readDiscountsData() throws IOException
    {
        excelReader er = new excelReader();
        return er.getExcelData("purchaseWithDiscountData.xlsx",1);
    }

    @DataProvider(name = "PurchaseWithoutDiscountData")
    public Object [][] readpurchaseData() throws IOException
    {
        excelReader er = new excelReader();
        return er.getExcelData("purchaseWithDiscountData.xlsx",2);
    }
    @DataProvider(name = "createSingleCreditData")
    public Object [][] readcreateSingleCreditData() throws IOException
    {
        excelReader er = new excelReader();
        return er.getExcelData("B2CData.xlsx",0);
    }
    @DataProvider(name = "createBulkCreditData")
    public Object [][] readcreateBulkCreditData() throws IOException
    {
        excelReader er = new excelReader();
        return er.getExcelData("B2CData.xlsx",1);
    }
}
