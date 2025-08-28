package testPackage;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

import static org.testng.Assert.assertTrue;

public class TestClass {

    WebDriver driver;


    @Parameters("browser")
    @BeforeMethod

    public void setUp(@Optional("chrome") String browser) {
        if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

    }


    @Test
    public void q1() throws InterruptedException {
        driver.navigate().to("https://duckduckgo.com");

        String title = driver.getTitle();
        assertTrue(title.contains("Google"), "Title does not contain Google but it contains " + title);
        driver.quit();

    }

    @Test
    public void q2() {
        driver.navigate().to("https://duckduckgo.com");

        By duckLogo = By.xpath("(//a[@href='/about'])[2]");
//        System.out.println(driver.findElement(duckLogo).isDisplayed());
        assertTrue(driver.findElement(duckLogo).isDisplayed(), "Logo is not displayed");
    }

    @Test
    public void q3() throws InterruptedException {
        driver.navigate().to("https://duckduckgo.com");

        driver.findElement(By.id("searchbox_input")).clear();
        driver.findElement(By.id("searchbox_input")).sendKeys("selenium webdriver");
        driver.findElement(By.xpath("//button[contains(@class,'searchbox_searchButton__LxebD')]")).click();
        String link = driver.findElement(By.cssSelector("#r1-0 h2 a")).getAttribute("href");

//        System.out.println(link);
        Assert.assertEquals(link, "https://www.selenium.dev/documentation/webdriver/", "Not expected Link : https://www.selenium.dev/documentation/webdriver/ Actual result: " + link);

    }

    @Parameters("firefox")
    @Test

    public void q4() {
        driver.navigate().to("https://duckduckgo.com");

        driver.findElement(By.id("searchbox_input")).clear();
        driver.findElement(By.id("searchbox_input")).sendKeys("TestNG");
        driver.findElement(By.xpath("//button[contains(@class,'searchbox_searchButton__LxebD')]")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#r1-2 h2 span")));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);

        String title = element.getText();
        Assert.assertEquals(title, "TestNG Tutorial", "Not expected Text");
    }

    @Test
    public void q5() {
        driver.navigate().to("https://duckduckgo.com");

        driver.findElement(By.id("searchbox_input")).clear();
        driver.findElement(By.id("searchbox_input")).sendKeys("Cucumber IO");
        driver.findElement(By.xpath("//button[contains(@class,'searchbox_searchButton__LxebD')]")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#r1-2 h2 a")));

        String link = element.getAttribute("href");
        Assert.assertEquals(link, "https://www.linkedin.com", "Not expected Link" + link);

    }

    @Test
    public void q6() {
        driver.navigate().to("https://the-internet.herokuapp.com/checkboxes");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//input[@type='checkbox'])[1]")));
        element.click();

        Assert.assertTrue(element.isSelected(), "Check Box 1 isn't Selected  ");

    }

    @AfterMethod
    public void afterTest() {
        driver.quit();


    }

}