package Utils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.testng.Reporter.*;

public class ExtentReporterTNG implements IReporter {
    private ExtentReports extent;

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        extent = new ExtentReports(outputDirectory + File.separator + "ExtentReportTNG.html", true);

        for (ISuite suite : suites) {
            Map<String, ISuiteResult> result = suite.getResults();
            for (ISuiteResult r : result.values()) {
                ITestContext context = r.getTestContext();

                buildTestNodes(context.getPassedTests(), LogStatus.PASS);
                buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
                buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
            }
        }

        extent.flush();
        extent.close();
    }

    private void buildTestNodes(IResultMap tests, LogStatus status) {
        ExtentTest test;

        if (tests.size() > 0) {
            for (ITestResult result : tests.getAllResults()) {
                test = extent.startTest(result.getMethod().getMethodName());
                StringBuffer details = new StringBuffer();
                test.setStartedTime(getTime(result.getStartMillis()));
                test.setEndedTime(getTime(result.getEndMillis()));
                Object[] instance = result.getParameters();
                for (int i = 0; i < instance.length; i++) {
                    details.append(instance[i]);
                    if (!(i == instance.length - 1)) {
                        details.append(",");
                    }
                }

    //            if ((result.FAILURE > 0)) {
     //               test.addScreenCapture(Browser.screenshotPath + ".png");
       //         }

                if (result.getThrowable() != null) {
                    test.log(status, "Test Data - (" + details.toString() + ")");
                    test.log(status, result.getThrowable());
                } else {
                    test.log(status, "Test Data - (" + details.toString() + ")");
                    test.log(status, "Test " + status.toString().toLowerCase() + "ed");
                }

                extent.endTest(test);
            }
        }
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
}