package Pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class adminUiLoginPage extends pageBase
{
    public adminUiLoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "button.btn.btn-primary")
    WebElement loginBtn;
    @FindBy(id = "identifierId")
    WebElement emailTextBox;
    @FindBy(id = "okta-signin-username")
    WebElement jumiaMailTextBox;
    @FindBy(id = "okta-signin-password")
    WebElement jumiaPasswordTextBox;
    @FindBy(css = "input.button.button-primary")
    WebElement oktaVerifyBtn;
    @FindBy(css = "span.VfPpkd-vQzf8d")
    WebElement verifyGmailBtn;
    @FindBy(xpath = "//div[@class='AuthBoxRow--name'][contains(.,'Okta ・ Jumia Okta')]")
    WebElement jumiaOktaLoginButton;
    @FindBy(xpath = "//input[@id='okta-signin-username']")
    WebElement oktaSignInUsername;
    @FindBy(xpath = "//input[@id='okta-signin-password']")
    WebElement oktaSignInPassword;
    @FindBy(xpath = "//input[@value='Send Push']")
    WebElement oktaSendPushButton;

    public void login (String userName, String password, String domain, WebDriverWait wait, Actions action, WebDriver driver, String email, String emailPassword) throws InterruptedException {
        // First Login
        //driver.navigate().to("https://"+userName+":"+password+"@admin-staging-pay.jumia.com."+domain);

        // New login function with Okta verification
        driver.navigate().to("https://admin-staging-pay.jumia.com."+domain);
        wait.until(ExpectedConditions.titleContains("Sign in ・ Cloudflare Access"));
        clickOnButton(jumiaOktaLoginButton, wait, action);

        // Okta sign in
        Assert.assertEquals(driver.getTitle(), "JUMIA - Sign In");
        writeText(oktaSignInUsername, wait, email);
        writeText(oktaSignInPassword, wait, emailPassword);
        oktaSignInPassword.sendKeys(Keys.ENTER);

        // Send Push
        clickOnButton(oktaSendPushButton, wait, action);
        wait.until(ExpectedConditions.titleContains("JumiaPay Administration - Login"));

        //Assert.assertEquals(driver.getTitle(), "JumiaPay Administration - Login");

        // Login
        Thread.sleep(3000);
        clickOnButton(loginBtn, wait, action);

        // login with Google authentication
        goToTab(driver, 1, "Google");

        writeText(emailTextBox, wait, email);
        emailTextBox.sendKeys(Keys.ENTER);

        // login with Jumia authentication
        /*goToTab(driver, 1, "JUMIA");
        writeText(jumiaMailTextBox, wait, email);
        writeText(jumiaPasswordTextBox, wait, emailPassword);
        jumiaPasswordTextBox.sendKeys(Keys.ENTER);

        // Okta Verify
        goToTab(driver, 1, "JUMIA");
        clickOnButton(oktaVerifyBtn, wait, action); // After clicking you've to approve from your mobile */

        // Confirm email
        goToTab(driver, 1, "Google Accounts");
        clickOnButton(verifyGmailBtn, wait, action);
    }
}
