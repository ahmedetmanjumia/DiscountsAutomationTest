package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

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
    @FindBy(xpath = "(//span[contains(.,'Create bulk credit')])[2]")
    WebElement createBulkCreditButton;
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
    @FindBy(xpath = "(//input[contains(@type,'text')])[1]")
    WebElement bulkCreditNameTextBox;
    @FindBy(xpath = "//textarea[contains(@rows,'5')]")
    WebElement bulkDescriptionTextArea;
    @FindBy(xpath = "//div[contains(@class,'v-file-input__text')]")
    WebElement uploadFilesTextBox;
    @FindBy(xpath = "//span[@class='v-btn__content'][contains(.,'Confirm')]")
    WebElement bulkConfirmButton;


    public String copyShopKey(WebDriverWait wait, Actions actions, String actualShopKey)
    {
        moveAndClick(shopConfigurationsButton, actions);
        wait.until(ExpectedConditions.visibilityOf(shopNameLabel));
        actions.moveToElement(shopNameLabel);
        String shopKey = shopKeyLabel.getText();
        Assert.assertEquals(actualShopKey, shopKey);
        return shopKey;
    }
    public void createSingleCredit(WebDriverWait wait, Actions actions, String creditWalletName, WebDriver driver, String singleCreditName, String singleCreditCustomerEmail, String amount, String description) throws InterruptedException {
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
        //String singleCreditNameXpath = "//span[contains(.,'"+singleCreditName+"')]";
        Thread.sleep(5000);
        driver.navigate().refresh();
        String singleCreditNameXpath = "(//span[@data-cy='item-value'][contains(.,'"+singleCreditName+"')])[1]";
        savedSingleCredit = driver.findElement(By.xpath(singleCreditNameXpath));
        Assert.assertEquals(savedSingleCredit.getText(), singleCreditName);
    }
    public void createBulkCredit(WebDriverWait wait, Actions actions, String creditWalletName, WebDriver driver, String bulkCreditName, String description) throws InterruptedException, AWTException {
        moveAndClick(mrpCreditsDropDownList, actions);
        String targetMRPCreditXPath = "//div[@class='v-list-item__title'][contains(.,'" + creditWalletName + "')]";
        targetMRPCredit = driver.findElement(By.xpath(targetMRPCreditXPath));
        moveAndClick(targetMRPCredit, actions);
        wait.until(ExpectedConditions.elementToBeClickable(createBulkCreditButton));
        moveAndClick(createBulkCreditButton, actions);
        // create Bulk credit
        writeText(bulkCreditNameTextBox, wait, bulkCreditName);
        writeText(bulkDescriptionTextArea, wait, description);

        // choose the Bulk Credit File
        String imgPath = System.getProperty("user.dir")+"/src/test/java/TestData/bulk-credits-template.csv";
        System.out.println("Image Path: "+imgPath);
        //uploadFilesTextBox.sendKeys(imgPath);

        moveAndClick(uploadFilesTextBox, actions);
        Robot robot = new Robot(); // to deal with the selection screen

        // Copy the csv file path
        StringSelection selection = new StringSelection(imgPath);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); // to get the last copied value at the clipboard
        clipboard.setContents(selection, null);

        // Enter csv File name
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.delay(2000);

        // CTRL + V to paste the csv file name
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.delay(2000);

        // Click on Enter button to upload file
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.delay(2000);

        Thread.sleep(3000);
        moveAndClick(bulkConfirmButton, actions);
    }
    public void signOut(WebDriver driver)
    {
        scrollToElement(signOutButton, driver);
        signOutButton.click();
    }
    public void openCreatedSingleCredit(Actions actions, String creditWalletName, WebDriver driver, String singleCreditName)
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
