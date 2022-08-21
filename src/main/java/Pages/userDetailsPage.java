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

    //@FindBy(css = "table.table.table-bordered.table-striped.table-hover")
    @FindBy(xpath = "/html/body/div[2]/div/div[1]/div[2]/div[6]/table")
    WebElement balanceTable;
    //@FindBy(xpath = "/html/body/div[2]/div/div[1]/div[2]/div[6]/table/tbody/tr[3]/td[1]")
    @FindBy(xpath = "(//td[contains(.,'Discount')])[1]")
    WebElement discountCell;
    //@FindBy(xpath = "/html/body/div[2]/div/div[1]/div[2]/div[6]/table/tbody/tr[3]/td[1]")
    WebElement inactiveDiscountCell;
    //@FindBy(xpath = "/html/body/div[2]/div/div[1]/div[2]/div[6]/table/tbody/tr[2]/td[1]")
    //WebElement inactiveDiscountCellEg;
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

    public void activateDiscounts(WebDriverWait wait, Actions actions, String domain, WebDriver driver) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(balanceTable));

        if (domain.equals("eg"))
            inactiveDiscountCell = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[6]/table/tbody/tr[2]/td[1]"));
        else if(domain.equals("ng"))
            inactiveDiscountCell = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[6]/table/tbody/tr[3]/td[1]"));
        //discountisExisted = searchInsideTable(balanceTable, "Discount");
        //if (discountCell.getText().contains("Discount"))
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

            // Activate discount, then check
            activateActions(wait, actions);
            Thread.sleep(3000);
            //discountisExisted = searchInsideTable(balanceTable, "Discount");
            //Assert.assertTrue(discountisExisted);
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
            //wait.until(ExpectedConditions.visibilityOf(balanceTable));
            //discountisExisted = searchInsideTable(balanceTable, "Discount");
            //if (discountCell.getText().contains("Discount"))
            //{
            //  discountisExisted = true;

            //Open Features popup
            clickOnButton(featuresButton, wait, actions);

            // Activate discount, then check
            activateActions(wait, actions);
            Thread.sleep(3000);
            System.out.println("Discount is deactivated");
            //discountisExisted = searchInsideTable(balanceTable, "Discount");
            //Assert.assertTrue(discountisExisted);
            if (domain.equals("eg"))
                inactiveDiscountCell = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[6]/table/tbody/tr[2]/td[1]"));
            else if(domain.equals("ng"))
                inactiveDiscountCell = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div[2]/div[6]/table/tbody/tr[3]/td[1]"));

            System.out.println("inactiveDiscountCell text: "+inactiveDiscountCell.getText());
            Assert.assertFalse(inactiveDiscountCell.getText().contains("Discount"));
            System.out.println("deactivateDiscounts finished");
        }
        //}

        /*if (discountisExisted == false)
        {

        }*/
    }

    public void activateActions(WebDriverWait wait, Actions actions)
    {
        clickInsidePopupButton(discountsPopup, activeDiscountsButton, wait, actions);

        clickInsidePopupButton(popupFooter, okButton, wait, actions);
        wait.until(ExpectedConditions.visibilityOf(savedSuccessfullyAlert));

        clickInsidePopupButton(popupFooter, closeButton, wait, actions);
        wait.until(ExpectedConditions.visibilityOf(balanceTable));
    }

}
