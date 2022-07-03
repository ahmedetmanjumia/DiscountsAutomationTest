package Pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class adminUIHomePage extends pageBase{
    public adminUIHomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(linkText = "Users")
    WebElement usersTab;

    @FindBy(id = "emailLike")
    WebElement searchMailTextBox;

    @FindBy(id = "grid-data-api")
    WebElement searchResultsTable;

    @FindBy(xpath = "/html/body/div[2]/div/div/table/tbody/tr/td[3]")
    WebElement emailSearchResultCell;

    public void activateDiscount(WebDriverWait wait, Actions action, WebDriver driver, String email) throws InterruptedException
    {
        goToTab(driver, 0, "JumiaPay Administration");
        wait.until(ExpectedConditions.elementToBeClickable(usersTab));
        clickOnButton(usersTab, wait, action);

        // Search for the discounts mail
        writeText(searchMailTextBox, wait, email);
        searchMailTextBox.sendKeys(Keys.ENTER);

        // Check search results table content
        wait.until(ExpectedConditions.visibilityOf(searchResultsTable));
        Assert.assertEquals((emailSearchResultCell.getText()), email);
        clickOnButton(emailSearchResultCell, wait, action);
        goToTab(driver, 1, "User Details");
    }
}
