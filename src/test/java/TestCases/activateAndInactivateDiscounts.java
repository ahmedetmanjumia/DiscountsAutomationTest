package TestCases;

import Pages.*;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.Test;

public class activateAndInactivateDiscounts extends testBase
{
    static adminUiLoginPage adminUiLoginPage;
    static adminUIHomePage adminUIHomePage;
    static userDetailsPage userDetails;
    static accountJumiaPayPage accountJPayPage;
    static jPayPage jPay;
    static  campaignServicePage campaignPage;

    @Test(dataProvider = "ExcelData", dataProviderClass = dataProvidersClass.class)
    public static void activateDiscount(String userName, String password, String domain, String email, String emailPassword, String searchEmail, String searchEmailPassword) throws InterruptedException {
        adminUiLoginPage = new adminUiLoginPage(driver);
        adminUiLoginPage.login(userName, password, domain, wait, actions, driver, email, emailPassword);

        // activateDiscount
        adminUIHomePage = new adminUIHomePage(driver);
        adminUIHomePage.activateDiscount(wait, actions, driver, searchEmail, 0);

        // Check that "User Details" page is opened
        Assert.assertEquals(driver.getTitle(), "User Details");
        userDetails = new userDetailsPage(driver);
        userDetails.activateDiscounts(wait, actions, domain, driver);

        //openDiscountsPage
        // Open a new tab and go to MSA (Merchant Service Area) (Staging - jumia Pay)
        driver.switchTo().newWindow(WindowType.TAB);
        driver.navigate().to("https://"+userName+":"+password+"@staging-pay.jumia.com."+domain);
        Assert.assertTrue(driver.getTitle().contains("Jumia One"));

        //Login to JPay
        jPay = new jPayPage(driver);
        jPay.login(driver, wait, actions, searchEmail, searchEmailPassword, domain);
        jPay.openDiscountsPage(actions, domain);

        // Assert that account Jumia pay page is opened
        Assert.assertEquals(driver.getTitle(), "Account JumiaPay");

        accountJPayPage = new accountJumiaPayPage(driver);
        accountJPayPage.openCampaignsPage(actions, driver);
        Assert.assertEquals(driver.getTitle(), "Campaign service");

        // Open Campaign Service Page
        campaignPage = new campaignServicePage(driver);
        campaignPage.openDiscounts(actions, wait, driver, domain);

    }
}
