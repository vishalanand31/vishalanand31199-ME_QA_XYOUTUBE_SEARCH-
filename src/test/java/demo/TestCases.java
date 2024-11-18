package demo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import demo.wrappers.WrapperMethods;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases {
    private WebDriver driver;
    private WebDriverWait wait;
    private WrapperMethods seleniumWrapper;
    private SoftAssert sa;

    @BeforeSuite(alwaysRun = true)
    public void startTest() {
        System.out.println("TestCases Started:");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        seleniumWrapper = new WrapperMethods(driver, wait);
        sa = new SoftAssert();
    }
    // Go to YouTube.com and Assert you are on the correct URL. Click on “About” at
    // the bottom of the sidebar, and print the message on the screen.

@Test(priority = 1, enabled = true)
public void testCase01() {
    try {
        System.out.println("Start Test case: Go to Youtube.com and print About Section Contents ");
        driver.get("https://www.youtube.com/");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.youtube.com/", "URL does not Match");

        // Replace Thread.sleep with WebDriverWait to ensure proper visibility of the About link
        WebElement aboutLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='About']")));
        seleniumWrapper.scrollBy(0, 7000);

        seleniumWrapper.clickElement(By.xpath("//a[text()='About']"));

        // Wait for visibility of the About section and print the text
        WebElement about = seleniumWrapper.waitForVisibility(By.xpath("//main[@id='content']"));
        String text = about.getText();
        System.out.println("About Section Text: " + text);

        System.out.println("End Test case: About Section Contents Printed Successfully");
    } catch (Exception e) {
        e.printStackTrace();
        Assert.fail("Test case 01 failed due to an exception: " + e.getMessage());
    }
}

@Test(priority = 2, enabled = true)
public void testCase02() {
    try {
        System.out.println("Start Test case: Go to Youtube.com and print Movies Tab Contents");
        driver.get("https://www.youtube.com/");
        seleniumWrapper.sleep(2000);
        
        seleniumWrapper.clickElement(By.xpath("//a[@title='Movies']"));
        seleniumWrapper.sleep(2000);

        // Use WebDriverWait to ensure top selling section is visible before interacting
        WebElement topSellingSection = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Top selling']")));
        seleniumWrapper.moveToElement(topSellingSection);
        seleniumWrapper.sleep(3000);

        WebElement topSellingButton = driver.findElement(By.xpath(
                "(//*[@id='right-arrow']/ytd-button-renderer/yt-button-shape/button/yt-touch-feedback-shape/div/div[2])[1]"));
        
        for (int i = 0; i < 3; i++) {
            topSellingButton.click();
            seleniumWrapper.sleep(3000);
        }

        WebElement lastMovie = seleniumWrapper.waitForVisibility(By.xpath("//*[@id='items']/ytd-grid-movie-renderer[16]"));
        String movieRating = seleniumWrapper.getText(
                By.xpath("//*[@id='items']/ytd-grid-movie-renderer[16]/ytd-badge-supported-renderer/div[2]"));
        System.out.println("Movie Rating: " + movieRating);

        sa.assertTrue(movieRating.contains("U"), "The movie is not marked 'U' for Mature.");

        String movieGenre = seleniumWrapper.getText(By.xpath("//*[@id='items']/ytd-grid-movie-renderer[16]/a/span"));
        System.out.println("Movie Genre: " + movieGenre);

        sa.assertTrue(movieGenre.contains("Comedy") || movieGenre.contains("Animation"),
                "The movie is neither 'Comedy' nor 'Animation'.");
        sa.assertAll();

        System.out.println("End Test case: Movies Tab Contents Printed Successfully");
    } catch (Exception e) {
        e.printStackTrace();
        Assert.fail("Test case 02 failed due to an exception: " + e.getMessage());
    }
}
@Test(priority = 3, enabled = true)
    public void testCase_03() {
        try {
            System.out.println("Start Test case: Go to Youtube.com and print Music Tab Contents ");
            driver.get("https://www.youtube.com/");
            Thread.sleep(2000);
            WebElement musicTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Music']")));
            musicTab.click();
            Thread.sleep(4000);

            seleniumWrapper.scrollBy(0, 600);
            Thread.sleep(4000);
            for (int i = 0; i < 3; i++) {
                WebElement rightButton = driver.findElement(By.xpath(
                        "(//*[@id='right-arrow']/ytd-button-renderer/yt-button-shape/button/yt-touch-feedback-shape/div/div[2])[1]"));
                rightButton.click();
                Thread.sleep(3000);
            }

            WebElement playlistName = driver
                    .findElement(By.xpath("//span[text()='Bollywood Dance Hitlist']"));
            String name = playlistName.getText();
            System.out.println("Playlist Name: " + name);
            Thread.sleep(3000);

            WebElement trackCount = driver.findElement(By.xpath("(//*[@id='video-count-text'])[11]"));
            String count = trackCount.getText();
            int countInt = Integer.parseInt(count.replaceAll("[^0-9]", ""));
            System.out.println("Track Count: " + count);
            Thread.sleep(3000);

            sa.assertTrue(countInt <= 50, "Number of tracks is more than 50");
            sa.assertAll();
            System.out.println("End Test case: Music Tab Contents Printed Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

@Test(priority = 4, enabled = true)
public void testCase04() {
    try {
        System.out.println("Start Test case: Go to Youtube.com and print News tab contents");
        driver.get("https://www.youtube.com/");
        seleniumWrapper.sleep(4000);

        seleniumWrapper.clickElement(By.xpath("//a[@title='News']"));
        seleniumWrapper.sleep(6000);

        seleniumWrapper.scrollBy(0, 400);
        seleniumWrapper.sleep(6000);

        // Using WebDriverWait for the elements
        List<WebElement> title_Latest = seleniumWrapper.findElements(By.xpath("//*[@id=\"author-text\"]/span"));
        for (int i = 0; i < 3; i++) {
            WebElement title = title_Latest.get(i);
            String name = title.getText();
            System.out.println("Name of the " + (i + 1) + " News: " + name);
        }
        seleniumWrapper.sleep(3000);

        List<WebElement> body_Contents = seleniumWrapper.findElements(
                By.xpath("//div[@id='content']//ytd-post-renderer//following-sibling::div//*[@id='body']/div[1]"));
        for (int i = 0; i < 3; i++) {
            WebElement body = body_Contents.get(i);
            String content = body.getText();
            System.out.println("Content of the " + (i + 1) + " News: " + content);
        }
        seleniumWrapper.sleep(6000);

        int totalLikes = 0;
        List<WebElement> likes_Total = seleniumWrapper.findElements(By.xpath("//span[@id='vote-count-middle']"));
        for (int i = 0; i < Math.min(likes_Total.size(), 3); i++) {
            WebElement totalLikesElement = likes_Total.get(i);
            String likesText = totalLikesElement.getText();

            if (!likesText.equals("0")) {
                // Convert likesText to a numeric value
                int likesCount = 0;
                if (!likesText.isEmpty()) {
                    if (likesText.endsWith("K")) {
                        likesCount = (int) (Double.parseDouble(likesText.replace("K", "")) * 1000);
                    } else {
                        likesCount = Integer.parseInt(likesText);
                    }
                }
                totalLikes += likesCount;
                System.out.println("Likes of the " + (i + 1) + " News: " + likesCount);
            }
        }

        System.out.println("Total Likes on the first 3 News Posts: " + totalLikes);
        System.out.println("End Test case: News tab contents printed successfully");

    } catch (Exception e) {
        e.printStackTrace();
        Assert.fail("Test case 04 failed due to an exception: " + e.getMessage());
    }
}

@Test(priority = 5, enabled = true)
public void testCase05() {
    try {
        System.out.println("Start Test case: Search for Movies, Music, and Games");

        // Search for Movies
        driver.get("https://www.youtube.com/");
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='search']")));
        searchBox.sendKeys("Movies");
        searchBox.sendKeys(Keys.RETURN);

        // Wait for search results to load
        seleniumWrapper.sleep(4000);
        Assert.assertTrue(driver.getTitle().contains("Movies"), "Search for Movies failed");

        // Search for Music
        driver.get("https://www.youtube.com/");
        searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='search']")));
        searchBox.sendKeys("Music");
        searchBox.sendKeys(Keys.RETURN);

        // Wait for search results to load
        seleniumWrapper.sleep(4000);
        Assert.assertTrue(driver.getTitle().contains("Music"), "Search for Music failed");

        // Search for Games
        driver.get("https://www.youtube.com/");
        searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='search']")));
        searchBox.sendKeys("Games");
        searchBox.sendKeys(Keys.RETURN);

        // Wait for search results to load
        seleniumWrapper.sleep(4000);
        Assert.assertTrue(driver.getTitle().contains("Games"), "Search for Games failed");

        System.out.println("End Test case: Search test completed successfully");
    } catch (Exception e) {
        e.printStackTrace();
        Assert.fail("Test case 05 failed due to an exception: " + e.getMessage());
    }
}
    
    @AfterSuite(alwaysRun = true)
    public void endTest() {
        System.out.println("End Test: TestCases");
        if (driver != null) {
            driver.close();
            driver.quit();
        }
    }
}
