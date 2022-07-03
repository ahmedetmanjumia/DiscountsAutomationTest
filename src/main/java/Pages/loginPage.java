package Pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class loginPage extends pageBase
{
    public loginPage(WebDriver driver) {
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

    public void login (WebDriverWait wait, Actions action, WebDriver driver, String email, String emailPassword) throws InterruptedException {
        Thread.sleep(3000);
        clickOnButton(loginBtn, wait, action);

        // login with Google authentication
        goToTab(driver, 1, "Google");
        writeText(emailTextBox, wait, email);
        emailTextBox.sendKeys(Keys.ENTER);

        // login with Jumia authentication
        goToTab(driver, 1, "JUMIA");
        writeText(jumiaMailTextBox, wait, email);
        writeText(jumiaPasswordTextBox, wait, emailPassword);
        jumiaPasswordTextBox.sendKeys(Keys.ENTER);

        // Okta Verify
        goToTab(driver, 1, "JUMIA");
        clickOnButton(oktaVerifyBtn, wait, action); // After clicking you've to approve from your mobile

        // Confirm email
        goToTab(driver, 1, "Google Accounts");
        clickOnButton(verifyGmailBtn, wait, action);

    }
}
