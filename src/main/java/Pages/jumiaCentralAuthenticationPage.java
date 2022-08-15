package Pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class jumiaCentralAuthenticationPage extends pageBase{
    public jumiaCentralAuthenticationPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "email")
    WebElement emailTextBox;
    @FindBy(xpath = "//input[contains(@id,'password')]")
    WebElement passwordTextBox;
    @FindBy(xpath = "/html/body/main/div/div/div[3]/div/div/div[1]/div[2]/div/div[1]/div[2]/span")
    WebElement orderTotalLabel;
    @FindBy(css = "div.phs.discount-amount")
    WebElement paymentDiscountLabel;
    @FindBy(xpath = "//input[@id='SMSOTP']")
    WebElement verificationCodeTextBox;

    public void login(WebDriver driver, WebDriverWait wait, String testCustomerEmail, String testCustomerPassword, String domain, String verificationCode, Actions actions) throws InterruptedException {

        goToTab(driver, 6, "Jumia Central Authentication");
        wait.until(ExpectedConditions.titleContains("Jumia Central Authentication"));
        actions.moveToElement(emailTextBox);
        writeTextWithoutWait(emailTextBox, wait, testCustomerEmail);
        emailTextBox.sendKeys(Keys.ENTER);

        // Enter Password
        wait.until(ExpectedConditions.visibilityOf(passwordTextBox));
        writeText(passwordTextBox, wait, testCustomerPassword);
        Thread.sleep(3000);
        passwordTextBox.sendKeys(Keys.ENTER);

        // Enter Verifacation code
        /*System.out.println("Enter Verification Code");
        System.out.println("Domain is: "+domain);*/
        /*if (domain.equals("ng"))
        {
            //System.out.println("Verification Code Before Replacing: "+verificationCode);
            verificationCode = verificationCode.replaceAll("\"", "");
            //System.out.println("Verification Code After Replacing: "+verificationCode);
            writeText(verificationCodeTextBox, wait, verificationCode);
            verificationCodeTextBox.sendKeys(Keys.ENTER);
        }*/

        Thread.sleep(10000);
        goToTab(driver, 6, "JumiaPay");
    }
    public void checkAmountAfterDiscount(String price, String quantity)
    {
        double calculatedTotalPrice = Double.parseDouble(price) * Double.parseDouble(quantity);
        Assert.assertTrue(orderTotalLabel.getText().contains(Double.toString(calculatedTotalPrice)));

        // Ensure that the discount label is displaying
        Assert.assertTrue(paymentDiscountLabel.getText().contains("Get a discount of"));
    }
}
