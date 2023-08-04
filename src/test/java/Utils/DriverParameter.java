package Utils;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DriverParameter {
    public static WebDriver driver;
    public static WebDriverWait wait;

    @BeforeClass
    @Parameters("browser")
    public void beforeClass(String browser) {
        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.SEVERE);
        switch (browser.toLowerCase())
        {
            case "firefox" :
                System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
                driver = new FirefoxDriver();
                System.out.println("firefox started");
                break;
            case "safari":
                driver=new SafariDriver();
                break;
            case "edge":
                driver=new EdgeDriver();
                break;
            default:
                System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(options);
        }
        //driver.manage().window().maximize(); // Ekranı max yapıyor.
        Duration dr = Duration.ofSeconds(30);
        driver.manage().timeouts().pageLoadTimeout(dr);
        driver.manage().timeouts().implicitlyWait(dr);

        wait = new WebDriverWait(driver,
                Duration.ofSeconds(30));

        LoginTest();
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
    void LoginTest()
    {
        EventManagerApplicationsWebElements elements=new EventManagerApplicationsWebElements();
        elements.sendKeysFunction(elements.userName,"automationtest" );
        elements.sendKeysFunction(elements.password,"123456789");
        elements.clickFunction(elements.loginBtn);
        Assert.assertTrue(elements.wellComeMsg.getText().equals("Welcome Automation Test User"));
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
        Assert.assertTrue(elements.registrationMsg.getText().equals("No registered event has been found!!"));
    }
}
