package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class campaignServicePage extends pageBase{
    public campaignServicePage(WebDriver driver) {
        super(driver);
    }

    WebElement discountsManagementDropDownList;
    WebElement discountsButton;
    WebElement firstDiscountRow;
    WebElement firstDiscountName;

    public void openDiscounts(Actions actions, WebDriverWait wait, WebDriver driver, String domain) throws InterruptedException {
        Thread.sleep(10000);
        if (domain == "eg")
        {
            discountsManagementDropDownList = showShadowRootElement(driver, "document.querySelector(\"body > dash-app\").shadowRoot.querySelector(\"#drawer > div > dash-navigation-menu\").shadowRoot.querySelector(\"div > paper-listbox > dash-submenu:nth-child(2)\")");
        }
        else // for Nigeria
        {
            discountsManagementDropDownList = showShadowRootElement(driver, "document.querySelector(\"body > dash-app\").shadowRoot.querySelector(\"#drawer > div > dash-navigation-menu\").shadowRoot.querySelector(\"div > paper-listbox > dash-submenu:nth-child(2) > paper-item > div\")");

        }
        wait.until(ExpectedConditions.visibilityOf(discountsManagementDropDownList));
        actions.moveToElement(discountsManagementDropDownList);
        discountsManagementDropDownList.click();

        discountsButton = showShadowRootElement(driver, "document.querySelector(\"body > dash-app\").shadowRoot.querySelector(\"#drawer > div > dash-navigation-menu\").shadowRoot.querySelector(\"div > paper-listbox > dash-submenu.iron-selected > paper-listbox > iron-selector > paper-item\")");
        actions.moveToElement(discountsButton);
        discountsButton.click();

        // Check First Discount Row
        Thread.sleep(5000);
        firstDiscountRow = showShadowRootElement(driver, "document.querySelector(\"body > dash-app\").shadowRoot.querySelector(\"#drawerLayout > iron-pages > discounts-list\").shadowRoot.querySelector(\"dash-list\").shadowRoot.querySelector(\"paper-material > div > paper-datatable-api\").shadowRoot.querySelector(\"table > tbody > tr:nth-child(1)\")");
        wait.until(ExpectedConditions.visibilityOf(firstDiscountRow));
        actions.moveToElement(firstDiscountRow);
        Assert.assertTrue(firstDiscountRow.isDisplayed());
    }
    public void checkDiscountStatus(String discountName, WebDriver driver)
    {
        firstDiscountName = showShadowRootElement(driver, "document.querySelector(\"body > dash-app\").shadowRoot.querySelector(\"#drawerLayout > iron-pages > discounts-list\").shadowRoot.querySelector(\"dash-list\").shadowRoot.querySelector(\"paper-material > div > paper-datatable-api\").shadowRoot.querySelector(\"table > tbody > tr:nth-child(1) > td:nth-child(2) > dash-list-cell-content\")");
        Assert.assertEquals(firstDiscountName.getText(), discountName);
    }
}