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

    @FindBy(xpath = "/html/body/div/div/div/div[6]/div/div[1]/a/div")
    WebElement jumiaStoreCreditButton;

    //@FindBy(css = "div.v-list.theme--light")
    //WebElement accountDropDownListItems;

    public void login(WebDriver driver, WebDriverWait wait, Actions actions, String email, String emailPassword)
    {
        moveAndClick(signInButton, actions);

        Assert.assertEquals(driver.getTitle(), "My Jumia");
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
    }

    public void openDiscountsPage(WebDriver driver, WebDriverWait wait, Actions actions) {
        moveAndClick(accountDropDownList, actions);
        moveAndClick(jumiaStoreCreditButton, actions);
    }
}
