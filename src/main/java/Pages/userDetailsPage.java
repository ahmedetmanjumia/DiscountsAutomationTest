package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.Locale;

public class userDetailsPage extends pageBase{
    public userDetailsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "table.table.table-bordered.table-striped.table-hover")
    WebElement balanceTable;

    @FindBy(xpath = "/html/body/div[2]/div/div[1]/div[2]/div[6]/table/tbody/tr[2]/td[1]")
    WebElement discountCell;

    @FindBy(id = "features_button")
    WebElement featuresButton;

    @FindBy(xpath = "//*[@id=\"featuresModal\"]/div/form/div/div[2]/div[2]/div/div/label/span[1]")
    WebElement activeDiscountsButton;

    //@FindBy(css = "button.btn.btn-success")
    @FindBy(xpath = "/html/body/div[2]/div/div[1]/div[1]/div[1]/section/div[2]/div/form/div/div[3]/button[2]")
    WebElement okButton;

    @FindBy(css = "div.alert.alert-dismissible.alert-success")
    WebElement savedSuccessfullyAlert;

    //@FindBy(css = "button.close")
    @FindBy(xpath = "/html/body/div[2]/div/div[1]/div[1]/div[1]/section/div[2]/div/form/div/div[3]/button[1]")
    WebElement closeButton;

    @FindBy(css = "form.form-horizontal")
    WebElement discountsPopup;

    @FindBy(xpath = "/html/body/div[2]/div/div[1]/div[1]/div[1]/section/div[2]/div/form/div/div[3]")
    WebElement popupFooter;

    public void activateOrDeactivateDiscounts(WebDriverWait wait, Actions actions, WebDriver driver) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(balanceTable));

        //Ensure that Discount Balance is existed
        Boolean discountisExisted = discountCell.getText().equals("Discount");

        //Open Features popup
//        clickOnButton(featuresButton, wait, actions);

        // If discount is displayed, then the discount feature is active
        if (discountisExisted == true)
        {
            //Inactive discount, check again, then activate
//            activateActions(wait, actions,driver);

            //Ensure that discount isn't exist at Balance table
//            Thread.sleep(3000);
//            Assert.assertFalse(discountCell.getText().equals("Discount"));

            //Activate discount again
//            Thread.sleep(5000);
//            featuresButton.click();
//            activateActions(wait, actions, driver);
            Thread.sleep(3000);
            Assert.assertTrue(discountCell.getText().equals("Discount"));
        }
        else
        {
            // Activate discount, then check
            activateActions(wait, actions, driver);
            Thread.sleep(3000);
            Assert.assertTrue(discountCell.getText().equals("Discount"));
        }
    }

    public void activateActions(WebDriverWait wait, Actions actions, WebDriver driver)
    {
        clickInsidePopupButton(discountsPopup, activeDiscountsButton, wait, actions);

        clickInsidePopupButton(popupFooter, okButton, wait, actions);
        wait.until(ExpectedConditions.visibilityOf(savedSuccessfullyAlert));

        clickInsidePopupButton(popupFooter, closeButton, wait, actions);
        wait.until(ExpectedConditions.visibilityOf(balanceTable));
    }
}
