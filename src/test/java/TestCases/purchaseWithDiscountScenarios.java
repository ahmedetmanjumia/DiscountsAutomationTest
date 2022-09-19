package TestCases;

import Pages.*;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.Test;

public class purchaseWithDiscountScenarios extends testBase
{
    static adminUiLoginPage adminUiLoginPage;
    static adminUIHomePage adminUIHomePage;
    static userDetailsPage userDetails;
    static accountJumiaPayPage accountJPayPage;
    static jPayPage jPay;
    static  campaignServicePage campaignPage;
    static jumiaPayBusinessPage jumiaPayBusinessPage;
    static jumiaPayBusinessHomePage jumiaPayBusinessHomePage;
    static payToolPage payToolPage;
    static jumiaCentralAuthenticationPage jumiaCentralAuthenticationPage;

    @Test(dataProvider = "PurchaseWithDiscountData", dataProviderClass = dataProvidersClass.class)
    public static void purchaseWithDiscount(String userName, String password, String domain, String email, String emailPassword, String searchEmail, String searchEmailPassword, String discountName, String testCustomerEmail, String price, String quantity, String testCustomerPassword, String actualShopKey, String verificationCode) throws InterruptedException {
        adminUiLoginPage = new adminUiLoginPage(driver);
        adminUiLoginPage.login(userName, password, domain, wait, actions, driver, email, emailPassword);
        //Thread.sleep(5000);

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
        if (domain == "eg")
        {
            Assert.assertEquals(driver.getTitle(), "Account JumiaPay");
        }
        else // for Nigeria
        {
            Assert.assertEquals(driver.getTitle(), "Account Jumia");
        }

        accountJPayPage = new accountJumiaPayPage(driver);
        accountJPayPage.openCampaignsPage(actions, driver);
        Assert.assertEquals(driver.getTitle(), "Campaign service");

        // Open Campaign Service Page
        campaignPage = new campaignServicePage(driver);
        campaignPage.openDiscounts(actions, wait, driver, domain);

        // Get First Discount name
        campaignPage.checkDiscountStatus(discountName, driver);

        //purchaseWithDiscount
        driver.switchTo().newWindow(WindowType.TAB);
        driver.navigate().to("https://"+userName+":"+password+"@business-staging-pay.jumia.com."+domain);
        Assert.assertTrue(driver.getTitle().contains("JumiaPay Business"));

        // Login as Jumia Pay Employee
        jumiaPayBusinessPage = new jumiaPayBusinessPage(driver);
        jumiaPayBusinessPage.loginAsJumiaEmployee(driver, actions, wait, 5);

        // Login with HTACCESS credentials
        jumiaPayBusinessPage.loginWithHtaccessCredentails(driver, userName, password, 4);

        // Copy Shop Key
        jumiaPayBusinessHomePage = new jumiaPayBusinessHomePage(driver);
        String shopKey = jumiaPayBusinessHomePage.copyShopKey(wait, actions, actualShopKey);

        // Open Pay tool
        driver.switchTo().newWindow(WindowType.TAB);
        driver.navigate().to("https://"+userName+":"+password+"@tools-pay.jumia.com/");
        Assert.assertTrue(driver.getTitle().contains("Pay tools"));

        // Purchase from shop with discount
        price = price.replaceAll("\"", "");
        quantity = quantity.replaceAll("\"", "");
        payToolPage = new payToolPage(driver);
        payToolPage.purchaseFromShopWithDiscount(wait, actions, domain, shopKey, testCustomerEmail, price, quantity);

        // Ensure that the discount applied on the purchase process
        jumiaCentralAuthenticationPage = new jumiaCentralAuthenticationPage(driver);
        jumiaCentralAuthenticationPage.login(driver, wait, testCustomerEmail, testCustomerPassword, domain, verificationCode, actions, 6);
        //Thread.sleep(3000);
        jumiaCentralAuthenticationPage.checkAmountAfterDiscount(price, quantity);
    }

    @Test(dependsOnMethods = "purchaseWithDiscount", dataProvider = "PurchaseWithDifferentShopData", dataProviderClass = dataProvidersClass.class)
    public static void purchaseWithDifferentShop(String userName, String password, String price, String quantity, String domain, String shopKey, String testCustomerEmail, String testCustomerPassword, String verificationCode) throws InterruptedException {
        // Go to the pay tools tab
        //ArrayList<String> activeTabs = new ArrayList<String>(driver.getWindowHandles());
        //driver.switchTo().window(activeTabs.get(5));

        // Open Pay tool
        driver.switchTo().newWindow(WindowType.TAB);
        driver.navigate().to("https://"+userName+":"+password+"@tools-pay.jumia.com/");

        Assert.assertTrue(driver.getTitle().contains("Pay tools"));
        System.out.println("Current tab title: "+driver.getTitle());

        price = price.replaceAll("\"", "");
        quantity = quantity.replaceAll("\"", "");
        payToolPage = new payToolPage(driver);
        payToolPage.purchaseFromShopWithDiscount(wait, actions, domain, shopKey, testCustomerEmail, price, quantity);

        // Ensure that the discount applied on the purchase process
        jumiaCentralAuthenticationPage = new jumiaCentralAuthenticationPage(driver);
        Pages.jumiaCentralAuthenticationPage.loginWithHtaccessCredentails(driver, userName, password, 2);
        jumiaCentralAuthenticationPage.login(driver, wait, testCustomerEmail, testCustomerPassword, domain, verificationCode, actions, 2);
        jumiaCentralAuthenticationPage.checkAmountWithoutDiscount(price, quantity);
    }

    @Test(dataProvider = "PurchaseWithDiscountData", dataProviderClass = dataProvidersClass.class)
    public static void purchaseWithoutDiscount(String userName, String password, String domain, String email, String emailPassword, String searchEmail, String searchEmailPassword, String discountName, String testCustomerEmail, String price, String quantity, String testCustomerPassword, String actualShopKey, String verificationCode) throws InterruptedException
    {
        adminUiLoginPage = new adminUiLoginPage(driver);
        adminUiLoginPage.login(userName, password, domain, wait, actions, driver, email, emailPassword);
        //Thread.sleep(5000);

        // activateDiscount
        adminUIHomePage = new adminUIHomePage(driver);
        adminUIHomePage.activateDiscount(wait, actions, driver, searchEmail, 0);

        // Check that "User Details" page is opened
        Assert.assertEquals(driver.getTitle(), "User Details");
        userDetails = new userDetailsPage(driver);
        userDetails.deactivateDiscounts(wait, actions, domain, driver);
        System.out.println("deactivateDiscounts finished");

        //purchaseWithDiscount
        driver.switchTo().newWindow(WindowType.TAB);
        driver.navigate().to("https://"+userName+":"+password+"@business-staging-pay.jumia.com."+domain);
        Assert.assertTrue(driver.getTitle().contains("JumiaPay Business"));

        // Login as Jumia Pay Employee
        jumiaPayBusinessPage = new jumiaPayBusinessPage(driver);
        jumiaPayBusinessPage.loginAsJumiaEmployee(driver, actions, wait, 3);

        // Login with HTACCESS credentials
        jumiaPayBusinessPage.loginWithHtaccessCredentails(driver, userName, password, 2);

        // Copy Shop Key
        jumiaPayBusinessHomePage = new jumiaPayBusinessHomePage(driver);
        String shopKey = jumiaPayBusinessHomePage.copyShopKey(wait, actions, actualShopKey);

        // Open Pay tool
        driver.switchTo().newWindow(WindowType.TAB);
        driver.navigate().to("https://"+userName+":"+password+"@tools-pay.jumia.com/");
        Assert.assertTrue(driver.getTitle().contains("Pay tools"));

        // Purchase from shop with discount
        price = price.replaceAll("\"", "");
        quantity = quantity.replaceAll("\"", "");
        payToolPage = new payToolPage(driver);
        payToolPage.purchaseFromShopWithDiscount(wait, actions, domain, shopKey, testCustomerEmail, price, quantity);

        // Ensure that the discount applied on the purchase process
        jumiaCentralAuthenticationPage = new jumiaCentralAuthenticationPage(driver);
        Thread.sleep(3000);
        Pages.jumiaCentralAuthenticationPage.loginWithHtaccessCredentails(driver, userName, password, 4);

        jumiaCentralAuthenticationPage.login(driver, wait, testCustomerEmail, testCustomerPassword, domain, verificationCode, actions, 4);
        jumiaCentralAuthenticationPage.checkAmountWithoutDiscount(price, quantity);
    }

}
