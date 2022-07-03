package Pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;

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

}
