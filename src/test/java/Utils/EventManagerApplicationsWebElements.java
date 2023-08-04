package Utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class EventManagerApplicationsWebElements {
    public  WebDriver driver=SingleDriver.driver;
    public WebDriverWait wait=SingleDriver.wait;

   public EventManagerApplicationsWebElements() {

       if (driver==null){
           driver= DriverParameter.driver;
       }
        PageFactory.initElements(driver, this);
    }
    @FindBy(id = "username")
    public WebElement userName;
    @FindBy(id = "password")
    public WebElement password;
    @FindBy(css = "button.mat-focus-indicator.mat-flat-button.mat-button-base.mat-primary")
    public WebElement loginBtn;
    @FindBy(id = "welcomeUserMessage")
    public WebElement wellComeMsg;
   @FindBy(css = "mat-card-content.mat-card-content")
   public WebElement registrationMsg;
    @FindBy(xpath = "//mat-error[text()='Username field is required']")
    public WebElement userError;
    @FindBy(xpath = "//mat-error[text()='Password field is required']")
    public WebElement passError;
    @FindBy(css = "button.mat-focus-indicator.mat-flat-button.mat-button-base")
    public WebElement createEventButton;
    @FindBy(xpath = "//input[@data-placeholder='Enter event name']")
    public WebElement eventName;
    @FindBy(id = "description")
    public WebElement eventDescription;
    @FindBy(xpath = "//input[@formcontrolname='date']")
    public WebElement eventDate;
    @FindBy(xpath = "(//input[@data-placeholder='Enter first name'])[1]")
    public WebElement participantName;
    @FindBy(xpath = "(//input[@data-placeholder='Enter last name'])[1]")
    public WebElement participantLastName;
    @FindBy(xpath = "(//input[@data-placeholder='Enter email or phone'])[1]")
    public WebElement participantContact;
    @FindBy(xpath = "//mat-icon[text()='edit']")
    public WebElement editIcon;
    @FindBy(xpath = "//span[text()='Add Participant']")
    public WebElement addParticipant;
    @FindBy(xpath = "(//input[@data-placeholder='Enter first name'])[2]")
    public WebElement participantName2;
    @FindBy(xpath = "(//input[@data-placeholder='Enter last name'])[2]")
    public WebElement participantLastName2;
    @FindBy(xpath = "(//input[@data-placeholder='Enter email or phone'])[2]")
    public WebElement participantContact2;
    @FindBy(css = "button.mat-focus-indicator.mat-flat-button.mat-button-base.mat-primary")
    public WebElement createNewEventButton;
    @FindBy(css = "div.cdk-overlay-container")
    public WebElement successMsg;
    @FindBy(xpath = "//mat-error[text()='Event name field is required']")
    public WebElement eventNameError;
    @FindBy(xpath = "//mat-error[text()='Please choose a valid date']")
    public WebElement eventDateError;
    @FindBy(xpath = "//mat-error[text()='Participant name is required']")
    public WebElement participantNameError;
    @FindBy(xpath = "//mat-error[text()='Participant last name is required']")
    public WebElement participantLastNameError;
    @FindBy(xpath = "//mat-error[text()='Please enter email or phone number']")
    public WebElement contactError;
    @FindBy(css="table.mat-table.cdk-table.mat-elevation-z0.ng-star-inserted>thead>tr>th")
    public List<WebElement> headerItems;
    @FindBy(xpath="(//table//tbody//tr)[1]//td")
    public List<WebElement> eventsList1;
    @FindBy(css = "button[title='Participants']")
    public WebElement participantsButton;
    @FindBy(css = "button[title='Delete Event']")
    public WebElement deleteButton;
    @FindBy(css="table.participant-table>thead>tr>th")
    public List<WebElement> participantHeaderItems;
   @FindBy(xpath="(//table[@class='participant-table']//tbody//tr)[1]//td")
   public List<WebElement> participantsList1;
   @FindBy(xpath="(//table[@class='participant-table']//tbody//tr)[2]//td")
   public List<WebElement> participantsList2;

    public void clickFunction(WebElement element)
    {
        waitUntilClickable(element);
        scrollToElement(element);
        element.click();
    }

    public void sendKeysFunction(WebElement element, String value)
    {
        waitUntilVisible(element);
        scrollToElement(element);
        element.clear();
        element.sendKeys(value);
    }

    public void selectFunction(WebElement element, String value)
    {
        waitUntilVisible(element);
        scrollToElement(element);
        element.click();
         Select select=new Select(element);
         select.selectByVisibleText(value);
    }
    public void waitUntilClickable(WebElement element){
        if (wait==null){
            wait=DriverParameter.wait;
        }

        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitUntilVisible(WebElement element){
        if (wait==null){
            wait=DriverParameter.wait;
        }
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void scrollToElement(WebElement element){
        JavascriptExecutor js= (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
    }


}
