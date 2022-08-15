package Pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class jPayPage extends pageBase{
    public jPayPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "p.sign-in-button")
    WebElement signInButton;
    @FindBy(css = "input.mdc-text-field__input.email-input")
    WebElement emailTextBox;
    @FindBy(css = "input.mdc-text-field__input.password-input  ")
    WebElement passwordTextBox;
    @FindBy(id = "otpCode")
    WebElement otpLabel;
    @FindBy(css = "input.mdc-text-field__input")
    WebElement otpFirstCell;
    @FindBy(id = "my-account-desktop")
    WebElement accountDropDownList;
    //@FindBy(xpath = "/html/body/div/div/div/div[6]/div/div[1]/a/div")
    @FindBy(xpath = "(//div[@class='v-list__tile__title'][contains(.,'Jumia Store credit')])[2]")
    WebElement jumiaStoreCreditButton;
    //@FindBy(xpath = "/html/body/div/div/div/div[4]/div/div[1]/a/div")
    @FindBy(xpath = "(//div[@class='v-list__tile__title'][contains(.,'Jumia Store credit')])[2]")
    WebElement jumiaStoreCreditNGButton;
    @FindBy(id = "btn-remind-me-later")
    WebElement remindMeLaterButton;

    public void login(WebDriver driver, WebDriverWait wait, Actions actions, String email, String emailPassword, String domain)
    {
        moveAndClick(signInButton, actions);
        if(domain == "eg")
        {
            Assert.assertEquals(driver.getTitle(), "My Jumia");
        }
        else if (domain == "ng")
        {
            Assert.assertEquals(driver.getTitle(), "Jumia");
        }
        wait.until(ExpectedConditions.visibilityOf(emailTextBox));
        writeText(emailTextBox, wait, email);
        emailTextBox.sendKeys(Keys.ENTER);

        // Enter Password
        wait.until(ExpectedConditions.visibilityOf(passwordTextBox));
        writeText(passwordTextBox, wait, emailPassword);
        passwordTextBox.sendKeys(Keys.ENTER);

        // Enter OTP
        wait.until(ExpectedConditions.visibilityOf(otpLabel));
        wait.until(ExpectedConditions.visibilityOf(otpFirstCell));

        // Copy OTP and enter it in OTP cells
        String otp = otpLabel.getText();

        actions.moveToElement(otpFirstCell);
        otpFirstCell.sendKeys(otp);

        // Check existence of "Remind me later" button, is existed, then click on it
        //Boolean remindMeLaterExistence = remindMeLaterButton.isDisplayed();
        //if(remindMeLaterExistence == true)
        if(domain.equals("ng"))
        {
            moveAndClick(remindMeLaterButton, actions);
        }
    }

    public void openDiscountsPage(Actions actions, String domain) {
        moveAndClick(accountDropDownList, actions);
        //if(domain == "eg")
        //{
            moveAndClick(jumiaStoreCreditButton, actions);
        //}
        //else // For Nigeria
        //{
          //  moveAndClick(jumiaStoreCreditNGButton, actions);
        //}
    }
}
