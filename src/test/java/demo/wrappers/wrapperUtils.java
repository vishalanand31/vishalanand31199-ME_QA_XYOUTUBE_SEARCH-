package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;


public class wrapperUtils {
    private static WebDriver driver;
        private static WebDriverWait wait;
            
                public wrapperUtils(WebDriver driver, WebDriverWait wait) {
                    this.driver = driver;
                    this.wait = wait;
                }
            
                public static void clickElement(By by) {
                    wait.until(ExpectedConditions.elementToBeClickable(by)).click();
        }
    
        public static WebElement waitForVisibility(By by) {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        }
    
        public static void moveToElement(WebElement element) {
            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();
        }
    
        public static void scrollBy(int x, int y) {
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(arguments[0], arguments[1]);", x, y);
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getText(By by) {
        
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by)).getText();
    }

    public static List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }
  
}