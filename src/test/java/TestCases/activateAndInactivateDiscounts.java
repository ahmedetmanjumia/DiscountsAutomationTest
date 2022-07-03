package TestCases;

import Pages.*;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.Test;

public class activateAndInactivateDiscounts extends testBase
{
    static loginPage loginPage;
    static adminUIHomePage adminUIHomePage;
    static userDetailsPage userDetails;
    static accountJumiaPayPage accountJPayPage;
    static jPayPage jPay;
    static  campaignServicePage campaignPage;

    @Test(dataProvider = "ExcelData", dataProviderClass = dataProvidersClass.class)
    public static void firstLogin(String userName, String password, String domain, String email, String emailPassword) throws InterruptedException {
        driver.navigate().to("https://"+userName+":"+password+"@admin-staging-pay.jumia.com."+domain);
        Assert.assertEquals(driver.getTitle(), "JumiaPay Administration - Login");

        loginPage = new loginPage(driver);
        loginPage.login(wait, actions, driver, email, emailPassword);
    }

    @Test(dependsOnMethods = "firstLogin", dataProvider = "discountData", dataProviderClass = dataProvidersClass.class)
    public static void activateDiscount(String email) throws InterruptedException {

        adminUIHomePage = new adminUIHomePage(driver);
        adminUIHomePage.activateDiscount(wait, actions, driver, email);

        // Check that "User Details" page is opened
        Assert.assertEquals(driver.getTitle(), "User Details");
        userDetails = new userDetailsPage(driver);
        userDetails.activateOrDeactivateDiscounts(wait, actions, driver);
    }

    @Test(dependsOnMethods = "activateDiscount", dataProvider = "purchaseData", dataProviderClass = dataProvidersClass.class)
    public static void openDiscountsPage(String email, String domain, String userName, String password, String emailPassword) throws InterruptedException {
        //actions.keyDown(Keys.CONTROL).sendKeys(Keys.).build().perform();
        // Open a new tab and go to MSA (Merchant Service Area) (Staging - jumia Pay)
        driver.switchTo().newWindow(WindowType.TAB);
        driver.navigate().to("https://"+userName+":"+password+"@staging-pay.jumia.com."+domain);
        Assert.assertTrue(driver.getTitle().contains("Jumia One"));

        //Login to JPay
        jPay = new jPayPage(driver);
        jPay.login(driver, wait, actions, email, emailPassword);
        jPay.openDiscountsPage(driver, wait, actions);

        // Assert that account Jumia pay page is opened
        Assert.assertEquals(driver.getTitle(), "Account JumiaPay");

        accountJPayPage = new accountJumiaPayPage(driver);
        accountJPayPage.openCampaignsPage(actions, driver);
        Assert.assertEquals(driver.getTitle(), "Campaign service");

        // Open Campaign Service Page
        campaignPage = new campaignServicePage(driver);
        campaignPage.openDiscounts(actions, wait, driver);
    }
}
