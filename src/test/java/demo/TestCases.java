package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;
import dev.failsafe.internal.util.Assert;

public class TestCases extends ExcelDataProvider { // Lets us read the data
        static ChromeDriver driver;
        static WebDriverWait wait;
        static SoftAssert softAssert;

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */

        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);
                driver.manage().window().maximize();

                wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                softAssert = new SoftAssert();
        }

        // Go to YouTube.com and Assert you are on the correct URL
        @Test(enabled = true)
        public static void testCase01() throws InterruptedException {

                driver.get("https://www.youtube.com/");
                wait.until(ExpectedConditions.urlContains("youtube"));
                Thread.sleep(3000);

                boolean status = driver.getCurrentUrl().contains("youtube.com");
                softAssert.assertTrue(status, "Failed");

                WebElement about = Wrappers.find_Element(By.xpath("//*[@id=\"guide-links-primary\"]/a[1]"), driver);
                Wrappers.click_On(about);
                wait.until(ExpectedConditions.urlContains("about.youtube"));

                WebElement headingElement = Wrappers.find_Element(By.xpath("//*[@id=\"content\"]/section/h1"), driver);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", headingElement);
                Thread.sleep(2000);

                String head = headingElement.getText();
                String body = Wrappers.find_Element(By.xpath("//*[@id=\"content\"]/section/p[1]"), driver).getText();

                System.out.println(head);
                System.out.println(body);

        }

        // Go to the "Films" tab and in the “Top Selling” section
        @Test(enabled = true)
        public static void testCase02() throws InterruptedException {

                driver.navigate().back();
                wait.until(ExpectedConditions.urlContains("youtube"));
                Thread.sleep(3000);

                WebElement films = Wrappers.find_Element(
                                By.xpath("//a[contains(@title, 'Films') or contains(@title, 'Movies')]"), driver);
                Wrappers.click_On(films);
                Thread.sleep(3000);

                WebElement rightArrow = Wrappers.find_Element(By.xpath(
                                "//*[@id='right-arrow']/ytd-button-renderer/yt-button-shape/button/yt-touch-feedback-shape/div"),
                                driver);
                Wrappers.click_On(rightArrow);
                Wrappers.click_On(rightArrow);
                Wrappers.click_On(rightArrow);

                String A = Wrappers.find_Element(By.xpath(
                                "//*[@id=\"items\"]/ytd-grid-movie-renderer[16]/ytd-badge-supported-renderer/div[2]/p"),
                                driver).getText();
                softAssert.assertTrue(A == "A", "Failed");

                String comedy = Wrappers
                                .find_Element(By.xpath("//*[@id='items']/ytd-grid-movie-renderer[16]/a/span"), driver)
                                .getText();

                boolean status = comedy.contains("Comedy") || comedy.contains("Animation");
                softAssert.assertTrue(status, "Failed");

        }

        // Go to the "Music" tab and in the 1st section
        @Test
        public static void testCase03() throws InterruptedException {

                driver.navigate().back();
                driver.get(driver.getCurrentUrl());
                Thread.sleep(3000);

                WebElement music = Wrappers.find_Element(By.xpath("//a[contains(@title, 'Music')]"), driver);
                Wrappers.click_On(music);
                Thread.sleep(3000);

                WebElement firstSection = Wrappers.find_Element(By.xpath("(//ytd-item-section-renderer)[1]"), driver);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", firstSection);
                Thread.sleep(2000);

                WebElement rightArrow = Wrappers.find_Element(By.xpath(
                                "//*[@id=\"right-arrow\"]/ytd-button-renderer/yt-button-shape/button/yt-touch-feedback-shape/div/div[2]"),
                                driver);
                Wrappers.click_On(rightArrow);
                Wrappers.click_On(rightArrow);
                Wrappers.click_On(rightArrow);

                String trackName = Wrappers
                                .find_Element(By.xpath("//*[@id=\"items\"]/ytd-compact-station-renderer[11]/div/a/h3"),
                                                driver)
                                .getText();
                System.out.println(trackName);

                String tracks = Wrappers.find_Element(By.xpath("//*[@id=\"video-count-text\"]"), driver).getText();

                softAssert.assertTrue(tracks.contains("50"), "Failed");

        }

        // Go to the "News" tab and print the title and body
        @Test
        public static void testCase04() throws InterruptedException {

                driver.navigate().back();
                driver.get(driver.getCurrentUrl());
                Thread.sleep(3000);

                WebElement music = Wrappers.find_Element(By.xpath("//a[contains(@title, 'News')]"), driver);
                Wrappers.click_On(music);
                Thread.sleep(3000);

                WebElement latestNewsPosts = Wrappers.find_Element(By.xpath("//*[@id=\"dismissible\"]"), driver);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", latestNewsPosts);
                Thread.sleep(2000);

                List<WebElement> newsTitle = Wrappers.find_Elements(By.xpath("//*[@id='author-text']/span"), driver);
                List<WebElement> newsBody = Wrappers.find_Elements(By.xpath("//*[@id='home-content-text']/span[1]"),
                                driver);
                List<WebElement> newsLikes = Wrappers.find_Elements(By.xpath("//*[@id='vote-count-middle']"), driver);

                int like = 0;

                for (int i = 0; i < 3; i++) {

                        System.out.println("Title " + (i + 1) + ": " + newsTitle.get(i).getText());
                        System.out.println("Body " + (i + 1) + ": " + newsBody.get(i).getText());
                        System.out.println();

                        String likeText = newsLikes.get(i).getText();
                        likeText = likeText.replaceAll("\\s", "");
                        try {
                                int likeCount = Integer.parseInt(likeText);
                                like += likeCount;
                        } catch (NumberFormatException e) {
                                System.err.println("Invalid like count: " + likeText);
                        }

                }

                System.out.println("Total likes are : " + like);

        }

        // Search for each of the items given in the stubs
        @Test(dataProvider = "excelData", dataProviderClass = ExcelDataProvider.class)
        public static void testCase05(String textToBeSearch) throws InterruptedException {

                driver.navigate().back();
                driver.get(driver.getCurrentUrl());
                Thread.sleep(3000);

                double limit = 100000000;

                WebElement searchBox = Wrappers.find_Element(By.xpath("//input[@id='search']"), driver);
                WebElement searchClick = Wrappers.find_Element(By.xpath("(//button[@aria-label='Search'])[1]"), driver);
               
                Wrappers.sen_Keys(searchBox, textToBeSearch);
                Wrappers.click_On(searchClick);

                List<WebElement> list1 = driver.findElements(By.xpath(
                                "//ytd-video-renderer[1]/div[1]/div/div[1]/ytd-video-meta-block/div[1]/div[2]/span[1]"));
                double sum = 0;
                for (WebElement webElement : list1) {
                        String[] arr = webElement.getText().split(" ");
                        if (arr[0].contains("M")) {
                                String[] arr1 = arr[0].split("M");
                                double value = Double.parseDouble(arr1[0]) * 0.1;
                                double crore = value * 10000000;
                                sum = sum + crore;
                                if (sum >= limit) {
                                        break;
                                }
                                JavascriptExecutor js = (JavascriptExecutor) driver;
                                js.executeScript("arguments[0].scrollIntoView();", webElement);
                        }
                        if (arr[0].contains("K")) {
                                String[] arr1 = arr[0].split("K");
                                double value = Double.parseDouble(arr1[0]);
                                double crore = value * 1000;
                                sum = sum + crore;
                                if (sum >= limit) {
                                        break;
                                }
                                JavascriptExecutor js = (JavascriptExecutor) driver;
                                js.executeScript("arguments[0].scrollIntoView();", webElement);
                        }
                }

                System.out.println("Total "+textToBeSearch+" views : " +sum);

        }

        @AfterTest
        public void endTest() {
                // driver.close();
                driver.quit();

        }
}