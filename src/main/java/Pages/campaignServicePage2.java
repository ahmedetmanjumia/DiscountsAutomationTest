package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class campaignServicePage2 extends pageBase{
    public campaignServicePage2(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "div.paper-item-label")
    //@FindBy(xpath = "/html/body/dash-app//app-header-layout/app-drawer-layout/app-drawer/div/dash-navigation-menu//div/paper-listbox/dash-submenu[2]/paper-item/div")
    //@FindBy(xpath = "/html/body/dash-app//app-header-layout/app-drawer-layout/app-drawer/div/dash-navigation-menu//div/paper-listbox/dash-submenu[2]")
    //@FindBy(xpath = "/html/body/dash-app//app-header-layout/app-drawer-layout/app-drawer/div/dash-navigation-menu//div/paper-listbox/dash-submenu[2]/paper-item")
    //@FindBy(xpath = "/html/body/dash-app//app-header-layout/app-drawer-layout/app-drawer/div/dash-navigation-menu//div/paper-listbox/dash-submenu[2]/paper-item/iron-icon")
    WebElement discountsManagementDropDownList;

    @FindBy(xpath = "/html/body/dash-app//app-header-layout/app-drawer-layout/app-drawer/div/dash-navigation-menu//div/paper-listbox/dash-submenu[2]/paper-listbox/iron-selector/paper-item")
    WebElement discountsButton;

    @FindBy(xpath = "/html/body/dash-app//app-header-layout/app-drawer-layout/iron-pages/discounts-list//dash-list//paper-material/div/paper-datatable-api//table/tbody/tr[1]/td[2]/dash-list-cell-content//div")
    WebElement firstDiscountCell;

    @FindBy(xpath = "/html/body/dash-app//app-header-layout/app-drawer-layout/app-drawer/div/dash-navigation-menu//div")
    WebElement managementDropDownList;

    @FindBy(xpath = "/html/body/dash-app//app-header-layout/app-drawer-layout/app-drawer/div/dash-navigation-menu//div/paper-listbox")
    WebElement managementDropDownListItems;


    public void openDiscounts(Actions actions, WebDriverWait wait) throws InterruptedException {
        Thread.sleep(3000);
        actions.moveToElement(managementDropDownList);
        //System.out.println("Dropdown list located");

        actions.moveToElement(managementDropDownListItems);
       // System.out.println("managementDropDownListItems located");

        actions.moveToElement(discountsManagementDropDownList);
        System.out.println("Discounts Management Dropdown list located");

        moveAndClick(discountsManagementDropDownList, actions);
        moveAndClick(discountsButton, actions);

        wait.until(ExpectedConditions.visibilityOf(firstDiscountCell));
        Assert.assertEquals(true, firstDiscountCell.isDisplayed());
    }
}
