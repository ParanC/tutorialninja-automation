package org.example.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import java.io.File;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        // recommended flags for CI and headless stability
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-background-timer-throttling");
        options.addArguments("--disable-backgrounding-occluded-windows");
        options.addArguments("--disable-renderer-backgrounding");
        options.addArguments("--disable-software-rasterizer");
        // remote debugging port is optional; remove if causing trouble
        // options.addArguments("--remote-debugging-port=9222");

        // Headless only when -Dheadless=true
        String headless = System.getProperty("headless", "false");
        boolean isHeadless = Boolean.parseBoolean(headless);
        if (isHeadless) {
            options.addArguments("--headless=new"); // or "--headless=chrome"
            // ensure no-window actions are supported
        }

        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingAnyFreePort()
                .withVerbose(true)
                .withLogFile(new File("chromedriver.log"))
                .build();

        driver = new ChromeDriver(service, options);

        // implicit wait to reduce flakiness (you can replace with explicit waits later)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // only maximize in non-headless, in headless it won't help
        if (!isHeadless) {
            driver.manage().window().maximize();
        }

        // *** CRITICAL: navigate to application under test so page elements exist ***
        driver.get("https://tutorialsninja.com/demo");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception ignored) {}
        }
    }
}
