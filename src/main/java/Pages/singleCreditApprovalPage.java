package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class singleCreditApprovalPage extends pageBase{
    public singleCreditApprovalPage(WebDriver driver) {super(driver);}

    @FindBy(xpath = "//span[@class='v-btn__content'][contains(.,'Approve')]")
    WebElement approveButton;
    @FindBy(xpath = "//span[@class='v-btn__content'][contains(.,'Decline')]")
    WebElement declineButton;
    @FindBy(xpath = "//div[@class='v-card__title text-h5 word-break-unset'][contains(.,'Approve Single Credit?')]")
    WebElement approveSingleCreditPopup;
    @FindBy(xpath = "//div[@class='v-card__title text-h5 word-break-unset'][contains(.,'Decline Single Credit approval?')]")
    WebElement declineSingleCreditPopup;
    @FindBy(xpath = "//span[@class='v-btn__content'][contains(.,'Yes')]")
    WebElement yesButton;
    @FindBy(xpath = "//div[@class='d-flex align-center font-weight-medium'][contains(.,'Single Credit approved successfully')]")
    WebElement approvalAlert;
    @FindBy(xpath = "//div[@role='status'][contains(.,'Single Credit declined successfully')]")
    WebElement declineAlert;
    @FindBy(xpath = "//span[@class='text-truncate'][contains(.,'Processing Delivery')]")
    WebElement singleCreditFinalStatusLabel;
    @FindBy(xpath = "(//span[contains(.,'Submit')])[2]")
    WebElement submitButton;
    @FindBy(xpath = "(//span[contains(.,'Edit')])[2]")
    WebElement editButton;
    @FindBy(xpath = "(//input[contains(@type,'text')])[1]")
    WebElement singleCreditnameTextBox;
    @FindBy(xpath = "//span[@class='v-btn__content'][contains(.,'Confirm')]")
    WebElement confirmButton;
    @FindBy(xpath = "//div[@class='v-card__title text-h5 word-break-unset'][contains(.,'Submit credit details?')]")
    WebElement submitCreditPopup;
    @FindBy(xpath = "//span[@class='v-btn__content'][contains(.,'Yes')]")
    WebElement submitYesButton;
    @FindBy(xpath = "//div[@role='status'][contains(.,'Single credit successfully updated')]")
    WebElement updateAlert;
    @FindBy(xpath = "//span[contains(.,'Single credit successfully submitted')]")
    WebElement submitAlert;
    WebElement updatedSingleCreditNameLabel;
    //@FindBy(xpath = "//svg[contains(@class,'v-icon__svg')]")
    @FindBy(xpath = "(//span[contains(@class,'v-btn__content')])[2]")
    WebElement backButton;

    public void approveSingleCredit(WebDriverWait wait, Actions actions)
    {
        // Approve the single credit
        moveAndClick(approveButton, actions);
        clickInsidePopupButton(approveSingleCreditPopup, yesButton, wait, actions);
        wait.until(ExpectedConditions.visibilityOf(approvalAlert));
        Assert.assertTrue(singleCreditFinalStatusLabel.getText().contains("Processing Delivery"));
    }
    public void declineSingleCredit(WebDriver driver, WebDriverWait wait, Actions actions, String newSingleCreditName, String singleCreditName) throws InterruptedException {
        // Approve the single credit
        moveAndClick(declineButton, actions);
        clickInsidePopupButton(declineSingleCreditPopup, yesButton, wait, actions);
        wait.until(ExpectedConditions.visibilityOf(declineAlert));
        Assert.assertTrue(editButton.isDisplayed());

        // Edit the single Credit
        moveAndClick(editButton, actions);
        wait.until(ExpectedConditions.visibilityOf(singleCreditnameTextBox));
        singleCreditnameTextBox.clear();
        //Thread.sleep(10000);
        writeText(singleCreditnameTextBox, wait, newSingleCreditName);
        newSingleCreditName = singleCreditName+newSingleCreditName;
        moveAndClick(confirmButton, actions);
        wait.until(ExpectedConditions.visibilityOf(submitCreditPopup));
        clickInsidePopupButton(submitCreditPopup, submitYesButton, wait, actions);
        wait.until(ExpectedConditions.visibilityOf(updateAlert));
        moveAndClick(submitButton, actions);
        wait.until(ExpectedConditions.visibilityOf(submitCreditPopup));
        clickInsidePopupButton(submitCreditPopup, submitYesButton, wait, actions);
        wait.until(ExpectedConditions.visibilityOf(submitAlert));

        // Ensure that the name is updated
        updatedSingleCreditNameLabel = driver.findElement(By.xpath("//span[@class='font-weight-medium'][contains(.,'"+newSingleCreditName+"')]"));
        Assert.assertTrue(updatedSingleCreditNameLabel.isDisplayed());
        moveAndClick(backButton, actions);
    }

}
