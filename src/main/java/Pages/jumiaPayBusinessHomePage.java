package Pages;

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

    public String copyShopKey(WebDriverWait wait, Actions actions, String actualShopKey)
    {
        moveAndClick(shopConfigurationsButton, actions);
        wait.until(ExpectedConditions.visibilityOf(shopNameLabel));
        actions.moveToElement(shopNameLabel);
        String shopKey = shopKeyLabel.getText();
        Assert.assertEquals(actualShopKey, shopKey);
        return shopKey;
    }
}
