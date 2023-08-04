package Utils;



import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SingleDriver {
    public static WebDriver driver;
    public static WebDriverWait wait;

    @BeforeClass
    public void beforeClass()
    {
        Logger logger= Logger.getLogger("");
        logger.setLevel(Level.SEVERE);
        System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        //driver.manage().window().maximize(); // Ekranı max yapıyor.
        Duration dr=Duration.ofSeconds(30);
        driver.manage().timeouts().pageLoadTimeout(dr);
        driver.manage().timeouts().implicitlyWait(dr);

        wait=new WebDriverWait(driver,
                Duration.ofSeconds(30));

        driver.get("https://e2e-assessment.piton.com.tr");

        LoginTest();

    }
    @AfterMethod
    public void printError(ITestResult result) {
        LocalDateTime date=LocalDateTime.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd.MM.yyyy");

        if (result.getStatus() == ITestResult.FAILURE) {
            String clsName = result.getTestClass().getName();
            clsName = clsName.substring(clsName.lastIndexOf(".") + 1);
            System.out.println("Test " + clsName + "." +
                    result.getName() + " FAILED");

            TakesScreenshot ts= (TakesScreenshot) driver;
            File screenShots=ts.getScreenshotAs(OutputType.FILE);
            try {
                FileHandler.copy(screenShots, new File("src/testResults/ScreenShots"+date.format(formatter)+".png"));

            } catch (IOException e) {
                e.printStackTrace();
            }
           }
      ExcelUtility.writeExcel("src/testResults/results.xlsx",result,"chrome",date.format(formatter));

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
