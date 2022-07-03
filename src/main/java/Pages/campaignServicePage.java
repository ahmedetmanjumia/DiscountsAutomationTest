package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;

public class campaignServicePage extends pageBase{
    public campaignServicePage(WebDriver driver) {
        super(driver);
    }

    WebElement discountsManagementDropDownList;

    WebElement discountsButton;

    WebElement firstDiscountRow;

    public void openDiscounts(Actions actions, WebDriverWait wait, WebDriver driver) throws InterruptedException {
        Thread.sleep(3000);

        discountsManagementDropDownList = showShadowRootElement(driver, "document.querySelector(\"body > dash-app\").shadowRoot.querySelector(\"#drawer > div > dash-navigation-menu\").shadowRoot.querySelector(\"div > paper-listbox > dash-submenu:nth-child(2)\")");
        //actions.moveToElement(discountsManagementDropDownList);
        // We can add a wait until element to be clickable here
        discountsManagementDropDownList.click();

        discountsButton = showShadowRootElement(driver, "document.querySelector(\"body > dash-app\").shadowRoot.querySelector(\"#drawer > div > dash-navigation-menu\").shadowRoot.querySelector(\"div > paper-listbox > dash-submenu.iron-selected > paper-listbox > iron-selector > paper-item\")");
        actions.moveToElement(discountsButton);
        discountsButton.click();

        // Check First Discount Row
        Thread.sleep(3000);
        firstDiscountRow = showShadowRootElement(driver, "document.querySelector(\"body > dash-app\").shadowRoot.querySelector(\"#drawerLayout > iron-pages > discounts-list\").shadowRoot.querySelector(\"dash-list\").shadowRoot.querySelector(\"paper-material > div > paper-datatable-api\").shadowRoot.querySelector(\"table > tbody > tr:nth-child(1)\")");
        actions.moveToElement(firstDiscountRow);
        Assert.assertTrue(firstDiscountRow.isDisplayed());

    }
}