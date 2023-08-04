package EventManagerAplicationTestsParameter;

import Utils.DriverParameter;
import Utils.EventManagerApplicationsWebElements;
import Utils.SingleDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

public class _01_LoginModuleTestParameter extends DriverParameter {

    @Test
    void Test1_LoginNegativeTest()
    {
        driver.navigate().back();
        EventManagerApplicationsWebElements elements=new EventManagerApplicationsWebElements();
        elements.clickFunction(elements.loginBtn);
        wait.until(ExpectedConditions.visibilityOf(elements.passError));
        Assert.assertTrue(elements.userError.getText().equals("Username field is required"));
        Assert.assertTrue(elements.passError.getText().equals("Password field is required"));
        Assert.assertTrue(!driver.getCurrentUrl().contains("dashboard"));
    }
}
