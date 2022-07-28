package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class payToolPage extends pageBase{
    public payToolPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "/html/body/div/nav/div[2]/ul/li[3]/a")
    WebElement createPurchaseButton;
    @FindBy(xpath = "/html/body/div/nav/div[2]/ul/li[2]/select")
    WebElement allCountries;
    @FindBy(xpath = "/html/body/div/div/div/div/div/div/div[1]/form/div[3]/div[2]/div[1]/h4/a")
    WebElement shopInformationButton;
    @FindBy(xpath = "/html/body/div/div/div/div/div/div/div[1]/form/div[3]/div[2]/div[2]/div/div[2]/input")
    WebElement merchantKeyTextBox;
    @FindBy(xpath = "//a[@role='button'][contains(.,'Relevant')]")
    WebElement relevantButtton;
    @FindBy(xpath = "//input[contains(@name,'consumerData[emailAddress]')]")
    WebElement customerEmailTextBox;
    @FindBy(xpath = "/html/body/div/div/div/div/div/div/div[1]/form/div[1]/div/div/div/div[1]/table/tbody/tr/td[3]/input")
    WebElement priceTextBox;
    @FindBy(xpath = "/html/body/div/div/div/div/div/div/div[1]/form/div[1]/div/div/div/div[1]/table/tbody/tr/td[5]/input")
    WebElement quantityTextBox;
    @FindBy(id = "basket-total")
    WebElement totalPrice;
    @FindBy(xpath = "(//button[@type='submit'][contains(.,'Submit purchase create')])[1]")
    WebElement submitPurchaseCreateButton;
    @FindBy(xpath = "/html/body/div/div/div/div/div/div/div[2]/div[1]/div/pre[2]/span[2]")
    WebElement requestStatusLabel;
    @FindBy(xpath = "//a[contains(.,'Visit checkout page')]")
    WebElement visitCheckoutPageButton;

    public void purchaseFromShopWithDiscount(WebDriverWait wait, Actions actions, String domain, String shopKey, String testCustomerEmail, String price, String quantity) throws InterruptedException {

        moveAndClick(createPurchaseButton, actions);
        wait.until(ExpectedConditions.visibilityOf(allCountries));

        // Choose country
        Select countriesList = new Select(allCountries);
        Assert.assertEquals(10, countriesList.getOptions().size());
        countriesList.selectByVisibleText(domain.toUpperCase());

        // Enter Shop Information
        moveAndClick(shopInformationButton, actions);
        writeText(merchantKeyTextBox, wait, shopKey);

        // Open "Relevant" and Enter test pay email
        moveAndClick(relevantButtton, actions);
        writeText(customerEmailTextBox, wait, testCustomerEmail);

        // Edit Price and Quantity
        actions.moveToElement(priceTextBox);
        writeTextWithoutWait(priceTextBox, wait, price);
        actions.moveToElement(quantityTextBox);
        writeTextWithoutWait(quantityTextBox, wait, quantity);

        // Ensure that calculated value = total value
        double calculatedTotalPrice = Double.parseDouble(price) * Double.parseDouble(quantity);
        Assert.assertEquals(calculatedTotalPrice, Double.parseDouble(totalPrice.getText()));

        // Click on Purchase button and check results
        moveAndClick(submitPurchaseCreateButton, actions);
        wait.until(ExpectedConditions.visibilityOf(requestStatusLabel));
        Assert.assertEquals("true", requestStatusLabel.getText());
        moveAndClick(visitCheckoutPageButton, actions);
    }
}
