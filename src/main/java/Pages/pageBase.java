package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class pageBase
{
    public pageBase (WebDriver driver)
    {
        super();
        PageFactory.initElements(driver, this);
    }

    protected static void clickOnButton(WebElement button, WebDriverWait wait, Actions actions)
    {
        wait.until(ExpectedConditions.elementToBeClickable(button));
        actions.moveToElement(button).click().build().perform();
    }

    protected static void clickInsidePopupButton(WebElement popup, WebElement button, WebDriverWait wait, Actions actions)
    {
        actions.moveToElement(popup);
        actions.moveToElement(button);
        wait.until(ExpectedConditions.elementToBeClickable(button));
        button.click();
    }

    protected static void moveAndClick(WebElement button, Actions actions)
    {
        actions.moveToElement(button);
        button.click();
    }

    protected static void goToTab(WebDriver driver, int tabIndex, String partOfTitle) throws InterruptedException {
        Thread.sleep(3000);
        ArrayList<String> activeTabs = new ArrayList<String>(driver.getWindowHandles());
        //System.out.println(activeTabs.size());
        while (true)
        {
            Boolean tabIsDisplaying = (driver.switchTo().window(activeTabs.get(tabIndex)).getTitle().contains(partOfTitle));
            if (activeTabs.size()>=tabIndex && tabIsDisplaying == true)
                break;

        }
        //Assert.assertTrue(driver.switchTo().window(activeTabs.get(tabIndex)).getTitle().contains(partOfTitle));
        driver.switchTo().window(activeTabs.get(tabIndex));
    }

    protected static void writeText(WebElement textBox, WebDriverWait wait, String text)
    {
        wait.until(ExpectedConditions.visibilityOf(textBox));
        textBox.clear();
        textBox.sendKeys(text);
    }
    protected static void writeTextAndEnter(WebElement textBox, WebDriverWait wait, String text)
    {
        wait.until(ExpectedConditions.visibilityOf(textBox));
        textBox.clear();
        textBox.sendKeys(text);
        textBox.sendKeys(Keys.ENTER);
    }
    protected static void writeTextWithoutWait(WebElement textBox, WebDriverWait wait, String text)
    {
        //wait.until(ExpectedConditions.visibilityOf(textBox));
        textBox.clear();
        textBox.sendKeys(text);
    }

    protected static WebElement detectLink(java.util.List<WebElement> elementsList, WebElement targetElement, String elementName)
    {
        System.out.println("Detect Link started");
        for(int i = 0; i<elementsList.size();i++)
        {
            System.out.println("Target Element is: "+elementsList.get(i).getText());
            System.out.println("Target Element class: "+elementsList.get(i).getTagName());
            if(elementsList.get(i).getText().contains(elementName))
            {
                targetElement = elementsList.get(i+1);
                System.out.println("Target Element is: "+targetElement.getText());
                break;
            }
        }

        return targetElement;
    }

    protected static WebElement detectLinkByIndex(java.util.List<WebElement> elementsList, WebElement targetElement, int index)
    {
        System.out.println("Detect Link started");
//        for(int i = 0; i<elementsList.size();i++)
//        {
//            System.out.println("Target Element is: "+elementsList.get(i).getText());
//            if(elementsList.get(i).getText().contains(elementName))
//            {
//                targetElement = elementsList.get(i+1);
//                System.out.println("Target Element is: "+targetElement.getText());
//                break;
//            }
//        }
        targetElement = elementsList.get(index);

        return targetElement;
    }

    protected static WebElement showShadowRootElement(WebDriver driver, String jsonPath)
    {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        WebElement element = (WebElement) jse.executeScript("return " + jsonPath);
        //System.out.println("Found element tag: "+ element.getTagName());
        
        return element;
    }

    protected static void scrollToElement(WebElement element, WebDriver driver)
    {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView();", element);
    }
    protected static void scrollToBottom(WebDriver driver)
    {
        JavascriptExecutor jse = (JavascriptExecutor) driver;

        // Scroll Down to the bottom of the page
        jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }
    protected static String goToParentWindow(WebDriver driver)
    {
        String parentWindow = driver.getWindowHandle();
        return parentWindow;
    }
    protected static Boolean searchInsideTable(WebElement targetTable, String targetCellText)
    {
        List<WebElement> allRows = targetTable.findElements(By.tagName("tr"));
        Boolean cellisExisted = false;
        for (WebElement row:allRows)
        {
            List<WebElement> column = row.findElements(By.tagName("td"));
            for(WebElement cell:column)
            {
                if(cell.getText().contains(targetCellText))
                {
                    cellisExisted = true;
                    break;
                }
            }
            if(cellisExisted == true)
            {
                break;
            }
        }
        return cellisExisted;
    }

    public static void loginWithHtaccessCredentails(WebDriver driver, String username, String password, int index) throws InterruptedException {
        Thread.sleep(5000);
        ArrayList<String> activeTabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(activeTabs.get(index));
        String url = driver.getCurrentUrl();
        url = url.replaceAll("https://", "https://" + username + ":" + password + "@");
        driver.navigate().to(url);
    }
    public static void openNewTabAndLogin(WebDriver driver, String userName, String password, String url, String domain, String pageTitle)
    {
        driver.switchTo().newWindow(WindowType.TAB);
        url+=domain;
        url = url.replaceAll("https://", "https://" + userName + ":" + password + "@");
        driver.navigate().to(url);
        Assert.assertTrue(driver.getTitle().contains(pageTitle));
    }
    public static Boolean isHTAccessNeeded(WebDriver driver, String targetUrl) throws InterruptedException {
        Thread.sleep(5000);
        Boolean urlIsExisted = false;
        if(driver.getCurrentUrl().contains(targetUrl))
            urlIsExisted = true;
        return urlIsExisted;
    }

}
