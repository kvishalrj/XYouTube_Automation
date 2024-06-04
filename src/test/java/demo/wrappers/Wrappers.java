package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */// Wrapper method to find elements
    public static List<WebElement> find_Elements(By by, WebDriver driver) {
        List<WebElement> element = driver.findElements(by);
        return element;
    }

    // Wrapper method to find element
    public static WebElement find_Element(By by, WebDriver driver) {
        WebElement element = driver.findElement(by);
        return element;
    }

    // Wrapper method to send value in a field
    public static void sen_Keys(WebElement element, String value) throws InterruptedException {
        element.clear();
        Thread.sleep(1000);
        element.sendKeys(value);
        Thread.sleep(1000);
    }

    // Wrapper method to click on an element
    public static void click_On(WebElement element) throws InterruptedException {
        element.click();
        Thread.sleep(2000);
    }

}
