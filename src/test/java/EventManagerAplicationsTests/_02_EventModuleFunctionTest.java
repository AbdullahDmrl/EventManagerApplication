package EventManagerAplicationsTests;

import Utils.EventManagerApplicationsWebElements;
import Utils.SingleDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class _02_EventModuleFunctionTest extends SingleDriver {

    @Test
    void Test1_CreateNewEventNegativeTest()
    {

        EventManagerApplicationsWebElements elements=new EventManagerApplicationsWebElements();
        elements.clickFunction(elements.createEventButton);
        elements.sendKeysFunction(elements.eventDescription,"Java001" );
        elements.clickFunction(elements.createNewEventButton);
        Assert.assertTrue(elements.eventNameError.getText().equals("Event name field is required"));
        Assert.assertTrue(elements.eventDateError.getText().equals("Please choose a valid date"));
        Assert.assertTrue(elements.participantNameError.getText().equals("Participant name is required"));
        Assert.assertTrue(elements.participantLastNameError.getText().equals("Participant last name is required"));
        Assert.assertTrue(elements.contactError.getText().equals("Please enter email or phone number"));
    }

    @Test
    void Test2_CreateNewEvent()
    {
        driver.navigate().back();
        EventManagerApplicationsWebElements elements=new EventManagerApplicationsWebElements();
        elements.clickFunction(elements.createEventButton);
        elements.sendKeysFunction(elements.eventName,"Meeting" );
        elements.sendKeysFunction(elements.eventDescription,"Java001" );
        elements.sendKeysFunction(elements.eventDate, LocalDate.now().toString());
        elements.sendKeysFunction(elements.participantName, "Teacher");
        elements.sendKeysFunction(elements.participantLastName, "Herr Hoffmann");
        elements.sendKeysFunction(elements.participantContact, "123456789");
        elements.clickFunction(elements.createNewEventButton);
        wait.until(ExpectedConditions.visibilityOf(elements.successMsg));
        Assert.assertTrue(elements.successMsg.getText().contains("successfully"));
    }

    @Test
    void Test3_CheckEventsList()
    {
        EventManagerApplicationsWebElements elements=new EventManagerApplicationsWebElements();
        List<String> itemsList=new ArrayList<>();
        itemsList.add("1");
        itemsList.add("Meeting");
        itemsList.add("Java001");
        itemsList.add(convertToDate(LocalDate.now().toString()));
        List<WebElement> eventList=elements.eventsList1;
        List<List<WebElement>> resutsList=new ArrayList<>();
        resutsList.add(eventList);
        assertEventTableHeaders(elements.headerItems);
        assertEventList(resutsList,itemsList);
    }

    @Test
    void Test4_EditAndUpdateNewEvent()
    {
        EventManagerApplicationsWebElements elements=new EventManagerApplicationsWebElements();
        elements.clickFunction(elements.editIcon);
        elements.clickFunction(elements.addParticipant);
        elements.sendKeysFunction(elements.participantName2, "Student");
        elements.sendKeysFunction(elements.participantLastName2, "Ali");
        elements.sendKeysFunction(elements.participantContact2, "4567890");
        elements.clickFunction(elements.createNewEventButton);
        wait.until(ExpectedConditions.visibilityOf(elements.successMsg));
        Assert.assertTrue(elements.successMsg.getText().contains("successfully"));
    }

    @Test
    void Test5_CheckParticipantsList()
    {
        EventManagerApplicationsWebElements elements=new EventManagerApplicationsWebElements();
        elements.clickFunction(elements.participantsButton);
        assertParticipantsTableHeaders(elements.participantHeaderItems);
        List<String> paticipants=new ArrayList<>();
        paticipants.add("1");
        paticipants.add("Teacher");
        paticipants.add("Herr Hoffmann");
        paticipants.add("123456789");
        List<String> paticipants2=new ArrayList<>();
        paticipants2.add("2");
        paticipants2.add("Student");
        paticipants2.add("Ali");
        paticipants2.add("4567890");
        List<List<String>> participantsTotalList=new ArrayList<>();
        participantsTotalList.add(paticipants);
        participantsTotalList.add(paticipants2);
        List<WebElement> participantsList1=elements.participantsList1;
        List<WebElement> participantsList2=elements.participantsList2;
        List<List<WebElement>> resutsList=new ArrayList<>();
        resutsList.add(participantsList1);
        resutsList.add(participantsList2);

        assertParticipantsList(resutsList,participantsTotalList);
    }

    @Test
    void Test6_DeleteEvent()
    {
        EventManagerApplicationsWebElements elements=new EventManagerApplicationsWebElements();
        Actions actions = new Actions(driver);
        actions.moveToElement(elements.participantsButton).click().perform();
        elements.clickFunction(elements.deleteButton);
        Assert.assertTrue(elements.registrationMsg.getText().equals("No registered event has been found!!"));
    }

    public static String convertToDate(String inputDate) {
        LocalDate date = LocalDate.parse(inputDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
        return date.format(formatter);
    }

    public void assertEventTableHeaders(List<WebElement> headerItems) {
        List<String> itemsHeader=new ArrayList<>();
        itemsHeader.add("ID");
        itemsHeader.add("Name");
        itemsHeader.add("Description");
        itemsHeader.add("Date");
        for (int i = 0; i < headerItems.size()-1; i++)
        {
            Assert.assertEquals(headerItems.get(i).getText(),itemsHeader.get(i));
        }
    }
    public void assertParticipantsTableHeaders(List<WebElement> headerItems) {
        List<String> itemsHeader=new ArrayList<>();
        itemsHeader.add("#");
        itemsHeader.add("First Name");
        itemsHeader.add("Last Name");
        itemsHeader.add("Contact");
        for (int i = 1; i < headerItems.size(); i++)
        {
            Assert.assertEquals(headerItems.get(i).getText(),itemsHeader.get(i));
        }
    }
    public void assertEventList(List<List<WebElement>> resutsList, List<String> itemsList) {
        for (int i = 0; i <resutsList.size() ; i++)
        {
            for (int j = 0; j <resutsList.get(i).size()-1 ; j++)
            {
                Assert.assertEquals(resutsList.get(i).get(j).getText(),itemsList.get(j));
            }
        }
    }
    public void assertParticipantsList(List<List<WebElement>> elementsList, List<List<String>> actualList) {
        for (int i = 0; i <elementsList.size() ; i++)
        {
            for (int j = 0; j <elementsList.get(i).size() ; j++)
            {
                Assert.assertEquals(elementsList.get(i).get(j).getText(),actualList.get(i).get(j));
            }
        }
    }
}
