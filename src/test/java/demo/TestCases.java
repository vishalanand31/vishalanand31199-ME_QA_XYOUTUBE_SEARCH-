package demo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
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

            seleniumWrapper.scrollBy(0, 7000);

            seleniumWrapper.clickElement(By.xpath("//a[text()='About']"));

            WebElement about = seleniumWrapper.waitForVisibility(By.xpath("//main[@id='content']"));
            String text = about.getText();
            System.out.println(text);
            System.out.println("End Test case: About Section Contents Printed Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test case 01 failed due to an exception: " + e.getMessage());
        }
    }

    // Go to the Movies tab and in the “Top Selling” section, scroll to the extreme
    // right. Apply a Soft Assert on whether the movie is marked “A” for Mature or
    // not. Apply a Soft assert on whether the movie is either “Comedy” or
    // “Animation”.

    @Test(priority = 2, enabled = true)
    public void testCase02() {
        
        try {
            System.out.println("Start Test case: Go to Youtube.com and print Movies Tab Contents");
            driver.get("https://www.youtube.com/");
            seleniumWrapper.sleep(2000);
            seleniumWrapper.clickElement(By.xpath("//a[@title='Movies']"));
            seleniumWrapper.sleep(2000);

            WebElement topSellingSection = driver.findElement(By.xpath("//span[text()='Top selling']"));
            seleniumWrapper.moveToElement(topSellingSection);
            seleniumWrapper.sleep(3000);

            WebElement topSellingButton = driver.findElement(By.xpath(
                    "(//*[@id='right-arrow']/ytd-button-renderer/yt-button-shape/button/yt-touch-feedback-shape/div/div[2])[1]"));
            for (int i = 0; i < 3; i++) {
                topSellingButton.click();
                seleniumWrapper.sleep(3000);
            }

            WebElement lastMovie = seleniumWrapper
                    .waitForVisibility(By.xpath("//*[@id='items']/ytd-grid-movie-renderer[16]"));
            String movieRating = seleniumWrapper.getText(
                    By.xpath("//*[@id='items']/ytd-grid-movie-renderer[16]/ytd-badge-supported-renderer/div[2]"));
            System.out.println("Movie Rating:" + movieRating);

            sa.assertTrue(movieRating.contains("U"), "The movie is not marked 'U' for Mature.");

            String movieGenre = seleniumWrapper
                    .getText(By.xpath("//*[@id='items']/ytd-grid-movie-renderer[16]/a/span"));
            System.out.println("Movie Genre" + movieGenre);

            sa.assertTrue(movieGenre.contains("Comedy") || movieGenre.contains("Animation"),
                    "The movie is neither 'Comedy' nor 'Animation'.");
            sa.assertAll();
            System.out.println("End Test case: Movies Tab Contents Printed Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test case 02 failed due to an exception: " + e.getMessage());
        }
    }

    // Go to the “Music” tab and in the 1st section, scroll to the extreme right.
    // Print the name of the playlist. Soft Assert on whether the number of tracks
    // listed is less than or equal to 50.

    @Test(priority = 3, enabled = true)
    public void testCase03() {
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
                    .findElement(By.xpath("//*[@id='items']/ytd-compact-station-renderer[11]/div/a/h3"));
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

    // Go to “News” tab and print the title and body of the 1st 3 “Latest News
    // Posts” along with the sum of number of likes on all 3 of them. No likes given
    // means 0.

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

     
            List<WebElement> title_Latest = seleniumWrapper.findElements(By.xpath("//*[@id=\"author-text\"]/span"));
            for (int i = 0; i < 3; i++) {
                WebElement title = title_Latest.get(i);
                String name = title.getText();

                System.out.println("Name of the " + (i + 1) + " News: " + name);
            }
            Thread.sleep(3000);
           
            List<WebElement> body_Contents = seleniumWrapper.findElements(
                    By.xpath("//div[@id='content']//ytd-post-renderer//following-sibling::div//*[@id='body']/div[1]"));
            for (int i = 0; i < 3; i++) {
                WebElement body = body_Contents.get(i);
                String content = body.getText();
                System.out.println("Content of the " + (i + 1) + " News: " + content);
            }
            Thread.sleep(6000);

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
            Assert.fail("Test case failed due to exception: " + e.getMessage());
        }
    }

   
    @Test(priority = 5, enabled = true)
    public void testCase05() {
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
