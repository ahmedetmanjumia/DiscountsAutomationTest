package TestCases;

import Pages.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;

public class B2cWalletScenarios extends testBase
{
    static Pages.adminUiLoginPage adminUiLoginPage;
    static Pages.adminUIHomePage adminUIHomePage;
    static userDetailsPage userDetails;
    static jumiaPayBusinessPage jumiaPayBusinessPage;
    static jumiaPayBusinessHomePage jumiaPayBusinessHomePage;
    static singleCreditApprovalPage singleCreditApprovalPage;
    static bulkCreditApprovalPage bulkCreditApprovalPage;

    @Test(dataProvider = "createSingleCreditData", dataProviderClass = dataProvidersClass.class)
    public static void createSingleCredit(String userName, String password, String domain, String email, String emailPassword, String searchEmail, String creditWalletName, String singleCreditName, String singleCreditCustomerEmail, String amount, String description, String loginEmail, String verificationCode, String approvalEmail) throws InterruptedException {
        adminUiLoginPage = new adminUiLoginPage(driver);
        adminUiLoginPage.login(userName, password, domain, wait, actions, driver, email, emailPassword);

        // activateDiscount
        adminUIHomePage = new adminUIHomePage(driver);
        adminUIHomePage.activateDiscount(wait, actions, driver, searchEmail, 0);

        // Check that "User Details" page is opened
        Assert.assertEquals(driver.getTitle(), "User Details");
        userDetails = new userDetailsPage(driver);
        userDetails.activateSubProducts(wait, actions);
        userDetails.createB2cCreditWallet(wait, actions, creditWalletName, driver);

        // Create Single credit
        pageBase.openNewTabAndLogin(driver, userName, password, "https://business-staging-pay.jumia.", domain, "JumiaPay Business");

        // Login as Jumia Pay Employee
        jumiaPayBusinessPage = new jumiaPayBusinessPage(driver);
        verificationCode = verificationCode.replaceAll("\"", "");
        jumiaPayBusinessPage.login(driver, wait, actions, loginEmail, verificationCode, userName, password, 2, domain);

        // Create Single Credit
        jumiaPayBusinessHomePage = new jumiaPayBusinessHomePage(driver);
        amount = amount.replaceAll("\"", "");
        jumiaPayBusinessHomePage.createSingleCredit(wait, actions, creditWalletName, driver, singleCreditName, singleCreditCustomerEmail, amount, description);
        jumiaPayBusinessHomePage.signOut(driver);
        wait.until(ExpectedConditions.urlContains("https://business-staging-pay.jumia."+domain));

        // Approve the single credit
        // Close Browser and open a new window
        Thread.sleep(3000);
        driver.quit();
        openBrowser();

        // Login
        driver.navigate().to("https://business-staging-pay.jumia."+domain);
        pageBase.loginWithHtaccessCredentails(driver, userName, password,0);
        jumiaPayBusinessPage = new jumiaPayBusinessPage(driver);
        jumiaPayBusinessPage.login(driver, wait, actions, approvalEmail, verificationCode, userName, password, 0, domain);
        Thread.sleep(3000);
        wait.until(ExpectedConditions.titleContains("Jumia Business"));

        // Approve the created single credit
        jumiaPayBusinessHomePage = new jumiaPayBusinessHomePage(driver);
        jumiaPayBusinessHomePage.openCreatedSingleCredit(actions, creditWalletName, driver, singleCreditName);

        singleCreditApprovalPage = new singleCreditApprovalPage(driver);
        singleCreditApprovalPage.approveSingleCredit(wait, actions);
        //System.out.println("test completed!");
    }
    @Test(dataProvider = "createSingleCreditData", dataProviderClass = dataProvidersClass.class)
    public static void cancelSingleCredit(String userName, String password, String domain, String email, String emailPassword, String searchEmail, String creditWalletName, String singleCreditName, String singleCreditCustomerEmail, String amount, String description, String loginEmail, String verificationCode, String approvalEmail) throws InterruptedException {
        adminUiLoginPage = new adminUiLoginPage(driver);
        adminUiLoginPage.login(userName, password, domain, wait, actions, driver, email, emailPassword);

        // activateDiscount
        adminUIHomePage = new adminUIHomePage(driver);
        adminUIHomePage.activateDiscount(wait, actions, driver, searchEmail, 0);

        // Check that "User Details" page is opened
        Assert.assertEquals(driver.getTitle(), "User Details");
        userDetails = new userDetailsPage(driver);
        userDetails.activateSubProducts(wait, actions);
        userDetails.createB2cCreditWallet(wait, actions, creditWalletName, driver);

        // Create Single credit
        pageBase.openNewTabAndLogin(driver, userName, password, "https://business-staging-pay.jumia.", domain, "JumiaPay Business");


        // Login as Jumia Pay Employee
        jumiaPayBusinessPage = new jumiaPayBusinessPage(driver);
        verificationCode = verificationCode.replaceAll("\"", "");
        jumiaPayBusinessPage.login(driver, wait, actions, loginEmail, verificationCode, userName, password, 2, domain);

        // Create Single Credit
        jumiaPayBusinessHomePage = new jumiaPayBusinessHomePage(driver);
        amount = amount.replaceAll("\"", "");
        jumiaPayBusinessHomePage.createSingleCredit(wait, actions, creditWalletName, driver, singleCreditName, singleCreditCustomerEmail, amount, description);
        signOutAndSignIn(jumiaPayBusinessHomePage, domain, userName, password, approvalEmail, verificationCode);

        // Cancel the created single credit
        jumiaPayBusinessHomePage = new jumiaPayBusinessHomePage(driver);
        jumiaPayBusinessHomePage.openCreatedSingleCredit(actions, creditWalletName, driver, singleCreditName);

        singleCreditApprovalPage = new singleCreditApprovalPage(driver);
        String newSingleCreditname = "2";
        singleCreditApprovalPage.declineSingleCredit(driver, wait, actions, newSingleCreditname, singleCreditName);

        signOutAndSignIn(jumiaPayBusinessHomePage, domain, userName, password, loginEmail, verificationCode);

        // Approve the declined Single Credit
        newSingleCreditname = singleCreditName+"2";
        jumiaPayBusinessHomePage = new jumiaPayBusinessHomePage(driver);
        jumiaPayBusinessHomePage.openCreatedSingleCredit(actions, creditWalletName, driver, newSingleCreditname);

        singleCreditApprovalPage = new singleCreditApprovalPage(driver);
        singleCreditApprovalPage.approveSingleCredit(wait, actions);
        System.out.println("Test Completed!");
    }
    @Test(dataProvider = "createBulkCreditData", dataProviderClass = dataProvidersClass.class)
    public static void createBulkCredit(String userName, String password, String domain, String email, String emailPassword, String searchEmail, String creditWalletName, String bulkCreditName, String description, String loginEmail, String verificationCode, String approvalEmail) throws InterruptedException, AWTException {
        adminUiLoginPage = new adminUiLoginPage(driver);
        adminUiLoginPage.login(userName, password, domain, wait, actions, driver, email, emailPassword);

        // activateDiscount
        adminUIHomePage = new adminUIHomePage(driver);
        adminUIHomePage.activateDiscount(wait, actions, driver, searchEmail, 0);

        // Check that "User Details" page is opened
        Assert.assertEquals(driver.getTitle(), "User Details");
        userDetails = new userDetailsPage(driver);
        userDetails.activateSubProducts(wait, actions);
        userDetails.createB2cCreditWallet(wait, actions, creditWalletName, driver);

        // Create Single credit
        pageBase.openNewTabAndLogin(driver, userName, password, "https://business-staging-pay.jumia.", domain, "JumiaPay Business");


        // Login as Jumia Pay Employee
        jumiaPayBusinessPage = new jumiaPayBusinessPage(driver);
        verificationCode = verificationCode.replaceAll("\"", "");
        jumiaPayBusinessPage.login(driver, wait, actions, loginEmail, verificationCode, userName, password, 2, domain);

        // Create Bulk Credit
        jumiaPayBusinessHomePage = new jumiaPayBusinessHomePage(driver);
        jumiaPayBusinessHomePage.createBulkCredit(wait, actions, creditWalletName, driver, bulkCreditName, description);
        signOutAndSignIn(jumiaPayBusinessHomePage, domain, userName, password, approvalEmail, verificationCode);

        // Approve the created bulk credit
        jumiaPayBusinessHomePage = new jumiaPayBusinessHomePage(driver);
        jumiaPayBusinessHomePage.openCreatedSingleCredit(actions, creditWalletName, driver, bulkCreditName);

        // Approve the bulk Credit
        bulkCreditApprovalPage = new bulkCreditApprovalPage(driver);
        bulkCreditApprovalPage.approveBulkCredit(actions, wait);
        Thread.sleep(3000);
        System.out.println("Test Completed!");
    }
    public static void signOutAndSignIn(jumiaPayBusinessHomePage jumiaPayBusinessHomePage, String domain, String userName, String password, String approvalEmail, String verificationCode) throws InterruptedException {
        jumiaPayBusinessHomePage.signOut(driver);
        wait.until(ExpectedConditions.urlContains("https://business-staging-pay.jumia."+domain));

        // Cancel the single credit
        // Close Browser and open a new window
        Thread.sleep(3000);
        driver.quit();
        openBrowser();

        // Login
        driver.navigate().to("https://business-staging-pay.jumia."+domain);
        pageBase.loginWithHtaccessCredentails(driver, userName, password,0);
        jumiaPayBusinessPage = new jumiaPayBusinessPage(driver);
        jumiaPayBusinessPage.login(driver, wait, actions, approvalEmail, verificationCode, userName, password, 0, domain);
        Thread.sleep(3000);
        wait.until(ExpectedConditions.titleContains("Jumia Business"));
    }
}
