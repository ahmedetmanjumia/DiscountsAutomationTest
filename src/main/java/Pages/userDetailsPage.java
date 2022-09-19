package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class userDetailsPage extends pageBase{
    public userDetailsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "/html/body/div[2]/div/div[1]/div[2]/div[6]/table")
    WebElement balanceTable;
    @FindBy(xpath = "(//td[contains(.,'Discount')])[1]")
    WebElement discountCell;
    WebElement inactiveDiscountCell;
    @FindBy(id = "features_button")
    WebElement featuresButton;
    @FindBy(xpath = "//*[@id=\"featuresModal\"]/div/form/div/div[2]/div[2]/div/div/label/span[1]")
    WebElement activeDiscountsButton;
    @FindBy(xpath = "/html/body/div[2]/div/div[1]/div[1]/div[1]/section/div[2]/div/form/div/div[3]/button[2]")
    WebElement okButton;
    @FindBy(css = "div.alert.alert-dismissible.alert-success")
    WebElement savedSuccessfullyAlert;
    @FindBy(xpath = "/html/body/div[2]/div/div[1]/div[1]/div[1]/section/div[2]/div/form/div/div[3]/button[1]")
    WebElement closeButton;
    @FindBy(css = "form.form-horizontal")
    WebElement discountsPopup;
    @FindBy(xpath = "/html/body/div[2]/div/div[1]/div[1]/div[1]/section/div[2]/div/form/div/div[3]")
    WebElement popupFooter;
    Boolean discountisExisted;
    @FindBy(xpath = "//a[contains(.,'Sub-Products')]")
    WebElement subProductsButton;
    @FindBy(id = "B2C_CREDITS")
    WebElement b2cCreditsStatus;
    @FindBy(xpath = "(//span[contains(@class,'switch-label')])[1]")
    WebElement b2cCreditsButton;
    //@FindBy(xpath = "(//button[@type='button'][contains(.,'Close')])[2]")
    @FindBy(xpath = "(//button[@data-dismiss='modal'])[2]")
    WebElement subProductsCloseButton;
    @FindBy(xpath = "(//div[contains(@class,'modal-footer')])[1]")
    WebElement subProductsFooter;
    @FindBy(xpath = "//button[@id='saveSubProducts']")
    WebElement subProductsOkButton;
    @FindBy(xpath = "//div[@class='alert alert-dismissible alert-success'][contains(.,'×CloseSaved successfully.')]")
    WebElement subProductsSavedSuccessfullyAlert;
    @FindBy(xpath = "//a[@href='#b2c_wallets'][contains(.,'B2C Credits')]")
    WebElement b2cCreditsTab;
    @FindBy(xpath = "//a[@id='create-b2c-wallet']")
    WebElement createB2cCreditWalletButton;
    @FindBy(xpath = "//input[@id='name']")
    WebElement b2cCreditWalletNameTextBox;
    @FindBy(xpath = "//button[@id='save-b2c-wallets']")
    WebElement b2cCreditWalletSaveButton;
    @FindBy(xpath = "//table[@id='b2c_wallets-grid-data-api']")
    WebElement b2cTable;
    @FindBy(xpath = "/html/body/div[2]/div/div[2]/div[2]/div[6]/table")
    WebElement balanceTableCi;


    public void activateDiscounts(WebDriverWait wait, Actions actions, String domain, WebDriver driver) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(balanceTable));

        if (domain.equals("eg"))
            inactiveDiscountCell = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[6]/table/tbody/tr[2]/td[1]"));
        else if(domain.equals("ng"))
            inactiveDiscountCell = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[6]/table/tbody/tr[3]/td[1]"));
        discountisExisted = false;
        System.out.println("Cell text: "+ inactiveDiscountCell.getText());
        if (inactiveDiscountCell.getText().contains("Discount"))
        {
            discountisExisted = true;
        }

        if (discountisExisted == false)
        {
            //Open Features popup
            clickOnButton(featuresButton, wait, actions);
            activateActions(wait, actions);
            Thread.sleep(3000);
            Assert.assertTrue(discountCell.getText().contains("Discount"));
        }
    }

    public void deactivateDiscounts(WebDriverWait wait, Actions actions, String domain, WebDriver driver) throws InterruptedException {
        if (domain.equals("eg"))
            inactiveDiscountCell = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[6]/table/tbody/tr[2]/td[1]"));
        else if(domain.equals("ng"))
            inactiveDiscountCell = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[6]/table/tbody/tr[3]/td[1]"));
        System.out.println("cell text: "+ inactiveDiscountCell.getText());
        if(inactiveDiscountCell.getText().contains("Discount")==true) {
            //Open Features popup
            clickOnButton(featuresButton, wait, actions);

            // Activate discount, then check
            activateActions(wait, actions);
            Thread.sleep(3000);
            System.out.println("Discount is deactivated");
            if (domain.equals("eg"))
                inactiveDiscountCell = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[6]/table/tbody/tr[2]/td[1]"));
            else if(domain.equals("ng"))
                inactiveDiscountCell = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[6]/table/tbody/tr[3]/td[1]"));

            System.out.println("inactiveDiscountCell text: "+inactiveDiscountCell.getText());
            Assert.assertFalse(inactiveDiscountCell.getText().contains("Discount"));
            System.out.println("deactivateDiscounts finished");
        }
    }

    public void activateActions(WebDriverWait wait, Actions actions)
    {
        clickInsidePopupButton(discountsPopup, activeDiscountsButton, wait, actions);

        clickInsidePopupButton(popupFooter, okButton, wait, actions);
        wait.until(ExpectedConditions.visibilityOf(savedSuccessfullyAlert));

        clickInsidePopupButton(popupFooter, closeButton, wait, actions);
        wait.until(ExpectedConditions.visibilityOf(balanceTable));
    }

    public void activateSubProducts(WebDriverWait wait, Actions actions) throws InterruptedException {
        clickOnButton(subProductsButton, wait, actions);
        if (b2cCreditsStatus.getAttribute("value").equals("1")) // if B2C Credits button is Active
            subProductsCloseButton.click();
        else // B2C Credits is not active, so we'll activate it
        {
            b2cCreditsButton.click();
            subProductsOkButton.click();
            wait.until(ExpectedConditions.visibilityOf(subProductsSavedSuccessfullyAlert));
            subProductsCloseButton.click();
        }
        Thread.sleep(3000);
        wait.until(ExpectedConditions.visibilityOf(b2cCreditsTab));
    }

    public void createB2cCreditWallet(WebDriverWait wait, Actions actions, String creditWalletName, WebDriver driver)
    {
        Boolean b2cWalletIsExisted = false;
        clickOnButton(b2cCreditsTab, wait, actions);

        // Check if the single credit wallet not added, then add it
        wait.until(ExpectedConditions.visibilityOf(b2cTable));
        scrollToBottom(driver);
        b2cWalletIsExisted = searchInsideTable(b2cTable, creditWalletName);
        if(!b2cWalletIsExisted)
        {
            clickOnButton(createB2cCreditWalletButton, wait, actions);
            wait.until(ExpectedConditions.visibilityOf(b2cCreditWalletNameTextBox));
            writeText(b2cCreditWalletNameTextBox, wait, creditWalletName);
            b2cCreditWalletSaveButton.click();

            // Ensure that the credit wallet is added at both B2C and Balance
            wait.until(ExpectedConditions.visibilityOf(b2cTable));
            Assert.assertTrue(searchInsideTable(b2cTable, creditWalletName));
            Assert.assertTrue(searchInsideTable(balanceTableCi, creditWalletName));
        }
    }

}
