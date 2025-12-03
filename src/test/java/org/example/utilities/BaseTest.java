package org.example.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import java.io.File;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.setExperimentalOption("useAutomationExtension", false);

        String headless = System.getProperty("headless", "false");
        if (Boolean.parseBoolean(headless)) {
            options.addArguments("--headless=new");
        }

        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingAnyFreePort()
                .withVerbose(true)
                .withLogFile(new File("chromedriver.log"))
                .build();

        driver = new ChromeDriver(service, options);
        driver.manage().window().maximize();
        driver.get("https://tutorialsninja.com/demo");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
