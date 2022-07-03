package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class accountJumiaPayPage extends pageBase{
    public accountJumiaPayPage(WebDriver driver) {
        super(driver);
    }

    //@FindBy(partialLinkText = "Campaigns")
    @FindBy(xpath = "/html/body/nav/div/ul[1]/li[5]/a")
    WebElement campaignsButton;

    public void openCampaignsPage(Actions actions, WebDriver driver) throws InterruptedException {
        moveAndClick(campaignsButton, actions);
        goToTab(driver, 3, "Campaign service");
    }

}
