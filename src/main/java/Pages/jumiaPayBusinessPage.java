package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class jumiaPayBusinessPage extends pageBase{
    public jumiaPayBusinessPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(partialLinkText = "Login as Jumia Employee")
    WebElement loginAsJumiaEmployeeLink;
    @FindBy(id = "google-login-btn")
    WebElement signInWithJumiaButton;
    @FindBy(css = "div.WBW9sf")
    WebElement existedEmailLabel;

    public void loginAsJumiaEmployee(WebDriver driver, Actions actions, WebDriverWait wait, int tabIndex) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(loginAsJumiaEmployeeLink));
        scrollToBottom(driver);
        Thread.sleep(3000);
        moveAndClick(loginAsJumiaEmployeeLink, actions);

        // ACL
        Assert.assertTrue(driver.getTitle().contains("ACL"));
        moveAndClick(signInWithJumiaButton, actions);

        // Choose the already existed google account
        //goToTab(driver, 5, "Google");
        goToTab(driver, tabIndex, "Google");
        moveAndClick(existedEmailLabel, actions);

        // Sign in with Google
        /*goToTab(driver, 5, "Google");
        writeText(emailTextBox, wait, email);
        emailTextBox.sendKeys(Keys.ENTER);

        // enter username and password
        goToTab(driver, 5, "JUMIA");
        writeText(secondEmailTextBox, wait, email);
        writeText(passwordTextBox, wait, emailPassword);
        passwordTextBox.sendKeys(Keys.ENTER);

        // Okta verify
        goToTab(driver, 5, "JUMIA");
        Thread.sleep(5000);
        // Verify from your mobile
        moveAndClick(sendPushButton, actions);

        // Continue
        goToTab(driver, 5, "Google Accounts");
        moveAndClick(continueButton, actions);*/
        //actions.keyDown(Keys.CONTROL).sendKeys(Keys.).build().perform();
        //System.out.println("loginAsJumiaEmployee Finished");
    }
    /*public void loginWithHtaccessCredentails(WebDriver driver, String username, String password, int index) throws InterruptedException {
        /*Thread.sleep(5000);
        ArrayList<String> activeTabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(activeTabs.get(index));
        String url = driver.getCurrentUrl();
        url = url.replaceAllin("https://", "https://"+username+":"+password+"@");
        driver.navigate().to(url);
        loginWithHtaccessCredentails(driver, username, password, index);
    }*/
}
