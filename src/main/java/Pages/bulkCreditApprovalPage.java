package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class bulkCreditApprovalPage extends pageBase{
    public bulkCreditApprovalPage(WebDriver driver) {super(driver);}

    @FindBy(xpath = "(//span[contains(.,'Submit')])[2]")
    WebElement submitButton;
    @FindBy(xpath = "(//div[contains(.,'Submit bulk credit details?')])[5]")
    WebElement submitBulkCreditPopup;
    @FindBy(xpath = "//span[@class='v-btn__content'][contains(.,'Yes')]")
    WebElement yesButton;
    @FindBy(xpath = "//span[contains(.,'Bulk credit successfully submitted')]")
    WebElement submitAlert;
    @FindBy(xpath = "(//span[@class='text-truncate'][contains(.,'Awaiting Approval')])[1]")
    WebElement statusLabel;

    public void approveBulkCredit(Actions actions, WebDriverWait wait)
    {
        moveAndClick(submitButton, actions);
        clickInsidePopupButton(submitBulkCreditPopup, yesButton, wait, actions);
        wait.until(ExpectedConditions.visibilityOf(submitAlert));
        Assert.assertTrue(statusLabel.getText().contains("Awaiting Approval"));
    }
}
