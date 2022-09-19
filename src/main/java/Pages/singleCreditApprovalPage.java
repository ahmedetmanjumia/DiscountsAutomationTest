package Pages;

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
    @FindBy(xpath = "//div[@class='v-card__title text-h5 word-break-unset'][contains(.,'Approve Single Credit?')]")
    WebElement approveSingleCreditPopup;
    @FindBy(xpath = "//span[@class='v-btn__content'][contains(.,'Yes')]")
    WebElement yesButton;
    @FindBy(xpath = "//div[@class='d-flex align-center font-weight-medium'][contains(.,'Single Credit approved successfully')]")
    WebElement approvalAlert;
    @FindBy(xpath = "//span[@class='text-truncate'][contains(.,'Processing Delivery')]")
    WebElement singleCreditFinalStatusLabel;

    public void approveSingleCredit(WebDriverWait wait, Actions actions)
    {
        // Approve the single credit
        moveAndClick(approveButton, actions);
        clickInsidePopupButton(approveSingleCreditPopup, yesButton, wait, actions);
        wait.until(ExpectedConditions.visibilityOf(approvalAlert));
        Assert.assertTrue(singleCreditFinalStatusLabel.getText().contains("Processing Delivery"));
    }

}
