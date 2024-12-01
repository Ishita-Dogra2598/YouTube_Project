package demo;

import org.openqa.selenium.By;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider{ // Lets us read the data
        ChromeDriver driver;
        Wrappers wrap=new Wrappers();
        ExcelDataProvider excel=new ExcelDataProvider();

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */

        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
         
         @Test(enabled = true)
      public void testCase01() throws InterruptedException
     {
      driver.get("https://www.youtube.com/");
        // 
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        Boolean status=wrap.CheckCurrentURl(driver);
       org.testng.Assert.assertTrue(status, "Oops!! The url is wrong .Please check the url");
       wrap.clickElementWrapper(driver,By.xpath("//a[contains(text(),'About')]"));
       wrap.messageOnScreen(driver,"h1","p");
    
       //p[contains(text(),'mission')]
     }
     @Test (enabled =true)
     public void testCase02() throws InterruptedException
     {
      wrap.getURLWrapper(driver,"https://www.youtube.com/");
      wrap.clickElementWrapper(driver,By.xpath("//a[@title='Movies']"));
      
      List <String> Categories=new ArrayList<>();
       Categories.add("Animation");
       Categories.add("Romance");
       Categories.add("Horror");
       Categories.add("Drama");
      wrap.checkMovieDetails(driver,By.xpath("//button[@aria-label='Next']"),"Kung Fu Panda 4","U",Categories);

     }
    
    @Test (enabled =true)
    public void testCase03() throws InterruptedException
    {
      wrap.getURLWrapper(driver,"https://www.youtube.com/");
      wrap.clickElementWrapper(driver,By.xpath("//a[@title='Music']"));

      wrap.checkMusicDetails(driver,"This year in music","Indian Indie Hits 2024");

      //ist section extreme right whether the last playlist is visible or not 
    }


    @Test (enabled=true)
    public void testCase04()
    {
      wrap.getURLWrapper(driver,"https://www.youtube.com/");
      wrap.clickElementWrapper(driver,By.xpath("//a[@title='News']"));
      wrap.ChecknewsDetails(driver, "Latest news posts");
      //Latest News Post >> Title and body of 1st 3 news and add the like count of 1st 3 news and print the sum 

     // "//span[text()='Latest news posts']/ancestor::div[@id='rich-shelf-header-container']/following-sibling::div//ytd-rich-item-renderer[@items-per-row='3' and @class='style-scope ytd-rich-shelf-renderer']"


    }

    @Test(enabled  =true,dataProvider = "excelData")
    public void testCase05(String SearchString) throws InterruptedException
    {
        driver.get("https://www.youtube.com/");
        Thread.sleep(3000);
        wrap.sendkeyselementwrapper(driver, By.xpath("//input[@id='search']"), SearchString);
//        int  i=1;
//        if(i==1)
//        {
//         System.out.println("Not taking the first column");
//        }
//        else{
Thread.sleep(8000);
       long viewcount= wrap.checkViewsCount(driver);
       System.out.println("The view count is : "+viewcount + " for the search keyword " + SearchString);
//        }
    }

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
        }

        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }
}