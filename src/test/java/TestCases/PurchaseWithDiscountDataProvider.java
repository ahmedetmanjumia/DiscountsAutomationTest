package TestCases;

import TestData.excelReader;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

@Test
public class PurchaseWithDiscountDataProvider {

    @DataProvider(name = "PurchaseWithDiscountData")
    public Object [][] readPurchaseWithDiscountData() throws IOException
    {
        excelReader er = new excelReader();
        return er.getExcelData("purchaseWithDiscountData.xlsx",0);
    }
    @DataProvider(name = "PurchaseWithDiscountData1")
    public Object [][] readDiscountsData() throws IOException
    {
        excelReader er = new excelReader();
        return er.getExcelData("purchaseWithDiscountData.xlsx",1);
    }

    @DataProvider(name = "purchaseData")
    public Object [][] readpurchaseData() throws IOException
    {
        excelReader er = new excelReader();
        return er.getExcelData("purchaseWithDiscountData.xlsx",2);
    }

    @DataProvider(name = "jumiaPayBusinessData")
    public Object[][] readJumiaPayBusinessData() throws IOException {
        excelReader er = new excelReader();
        return er.getExcelData("purchaseWithDiscountData.xlsx", 3);
    }
}
