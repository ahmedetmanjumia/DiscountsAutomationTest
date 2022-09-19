package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class jumiaPayBusinessHomePage extends pageBase{
    public jumiaPayBusinessHomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "/html/body/div/div[1]/nav/div[1]/div[3]/div[2]/a[8]/div[2]/div")
    WebElement shopConfigurationsButton;
    @FindBy(xpath = "/html/body/div/div[1]/main/div/div[2]/div/div[2]/div/div[1]/div[1]")
    WebElement shopNameLabel;
    @FindBy(xpath = "/html/body/div/div[1]/main/div/div[2]/div/div[2]/div/div[1]/div[2]/div[4]/div[2]/span")
    WebElement shopKeyLabel;
    /*@FindBy(xpath = "//div[contains(@class,'d-flex justify-space-between align-start')]")
    WebElement leftSidePanel;*/
    @FindBy(xpath = "(//div[contains(.,'MRP Credits')])[8]")
    WebElement mrpCreditsDropDownList;
    @FindBy(xpath = "(//span[contains(.,'Create single credit')])[2]")
    WebElement createSingleCreditButton;
    @FindBy(xpath = "/html/body/div/div[1]/main/div/div[2]/div/div[2]/form/div[1]/div/div/div/div[1]/div/input")
    WebElement singleCreditNameTextBox;
    @FindBy(xpath = "/html/body/div/div/main/div/div[2]/div/div[2]/form/div[2]/div/div/div/div[1]/div/input")
    WebElement customerEmailTextBox;
    @FindBy(xpath = "/html/body/div/div/main/div/div[2]/div/div[2]/form/div[3]/div[1]/div/div/div[1]/div/input")
    WebElement amountTextBox;
    @FindBy(xpath = "/html/body/div/div/main/div/div[2]/div/div[2]/form/div[5]/div/div/div/div[1]/div/textarea")
    WebElement descriptionTextArea;
    @FindBy(xpath = "//span[@class='v-btn__content'][contains(.,'Confirm')]")
    WebElement confirmButton;
    @FindBy(xpath = "(//div[contains(.,'Submit credit details?')])[5]")
    WebElement submitCreditPopup;
    @FindBy(xpath = "//span[@class='v-btn__content'][contains(.,'Yes')]")
    WebElement submitCreditButton;
    @FindBy(xpath = "(//div[contains(.,'Sign Out')])[6]")
    WebElement signOutButton;
    WebElement targetMRPCredit, savedSingleCredit;

    public String copyShopKey(WebDriverWait wait, Actions actions, String actualShopKey)
    {
        moveAndClick(shopConfigurationsButton, actions);
        wait.until(ExpectedConditions.visibilityOf(shopNameLabel));
        actions.moveToElement(shopNameLabel);
        String shopKey = shopKeyLabel.getText();
        Assert.assertEquals(actualShopKey, shopKey);
        return shopKey;
    }
    public void createSingleCredit(WebDriverWait wait, Actions actions, String creditWalletName, WebDriver driver, String singleCreditName, String singleCreditCustomerEmail, String amount, String description)
    {
        moveAndClick(mrpCreditsDropDownList, actions);
        String targetMRPCreditXPath = "//div[@class='v-list-item__title'][contains(.,'"+creditWalletName+"')]";
        targetMRPCredit = driver.findElement(By.xpath(targetMRPCreditXPath));
        moveAndClick(targetMRPCredit, actions);
        wait.until(ExpectedConditions.elementToBeClickable(createSingleCreditButton));
        moveAndClick(createSingleCreditButton, actions);

        // Fill new single Credit data
        writeText(singleCreditNameTextBox, wait, singleCreditName);
        writeText(customerEmailTextBox, wait, singleCreditCustomerEmail);
        writeText(amountTextBox, wait, amount);
        writeText(descriptionTextArea, wait, description);
        moveAndClick(confirmButton, actions);
        clickInsidePopupButton(submitCreditPopup, submitCreditButton, wait, actions);

        //Ensure that Single Credit saved
        String singleCreditNameXpath = "//span[contains(.,'"+singleCreditName+"')]";
        savedSingleCredit = driver.findElement(By.xpath(singleCreditNameXpath));
        Assert.assertEquals(savedSingleCredit.getText(), singleCreditName);
    }
    public void signOut(WebDriver driver)
    {
        scrollToElement(signOutButton, driver);
        signOutButton.click();
    }
    public void approveOrDeclineSingleCredit(String approvalStatus, WebDriverWait wait, Actions actions, String creditWalletName, WebDriver driver, String singleCreditName)
    {
        moveAndClick(mrpCreditsDropDownList, actions);
        String targetMRPCreditXPath = "//div[@class='v-list-item__title'][contains(.,'"+creditWalletName+"')]";
        targetMRPCredit = driver.findElement(By.xpath(targetMRPCreditXPath));
        moveAndClick(targetMRPCredit, actions);

        //Ensure that the founded single credit is the target
        String singleCreditNameXpath = "//span[contains(.,'"+singleCreditName+"')]";
        savedSingleCredit = driver.findElement(By.xpath(singleCreditNameXpath));
        Assert.assertEquals(savedSingleCredit.getText(), singleCreditName);
        savedSingleCredit.click();
    }
}
