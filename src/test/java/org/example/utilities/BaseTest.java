
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriverService;
import java.io.File;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // setup chromedriver automatically
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // CI friendly flags
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.setExperimentalOption("useAutomationExtension", false);

        // Use headless only when system property headless=true is passed (workflow does -Dheadless=true)
        String headless = System.getProperty("headless", "false");
        if (Boolean.parseBoolean(headless)) {
            // try new headless, fallback if needed
            options.addArguments("--headless=new");
            // if runner fails with new, try: options.addArguments("--headless=chrome");
        }

        // create a service with verbose log to inspect on CI if needed
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingAnyFreePort()
                .withVerbose(true)
                .withLogFile(new File("chromedriver.log"))
                .build();

        driver = new ChromeDriver(service, options);
        // maximize or set implicit waits if you want
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

























// package org.example.utilities;

// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.chrome.ChromeDriver;
// import org.testng.annotations.AfterMethod;
// import org.testng.annotations.BeforeMethod;

// public class BaseTest {
//     protected WebDriver driver;

//     @BeforeMethod
//     public void setUp(){
//         driver =new ChromeDriver();
//         driver.manage().window().maximize();
//         driver.get("https://tutorialsninja.com/demo");

//     }
//     @AfterMethod
//     public void tearDown(){
//         if(driver!=null) driver.quit();
//     }
// }
