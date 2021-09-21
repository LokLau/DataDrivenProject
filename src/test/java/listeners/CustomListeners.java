package listeners;

import org.apache.poi.util.SystemOutLogger;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import utilities.TestUtil;

public class CustomListeners extends TestBase implements ITestListener {

	public void onTestFailure(ITestResult result) {

		
		System.setProperty("org.uncommons.reportng.escape-output","false");
		
		try {
			TestUtil.takeScreenShot();
		}catch(Throwable t) {
			
			Reporter.log(t.getMessage());
		}
		
		test.log(LogStatus.FAIL, result.getName().toUpperCase() + " FAIL with exception: " + result.getThrowable());
		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenPath));
	
		Reporter.log("ITestResult: " + result.getName());
		Reporter.log("<a target=\"_blank\" href=\""+ TestUtil.screenPath +"\"> Screenshot  </a>");
		Reporter.log("</br>");
		//Reporter.log("<a target=\"_blank\" href=\"C:\\Users\\zilel\\OneDrive\\Desktop\\LOK\\Selenium_Project\\DataDrivenFrameWork\\target\\surefire-reports\\html\\error1.jpg\"> Screenshot  </a>");
		
		report.endTest(test);
		report.flush();
	}

	public void onTestSuccess(ITestResult result) {
		
		test.log(LogStatus.PASS, result.getName().toUpperCase() + " PASS");
		report.endTest(test);
		report.flush();
		
	}

	public void onTestStart(ITestResult result) {
		test =  report.startTest(result.getName().toUpperCase());
		
		if (!isTestRunnable(result.getName(), excel)) {
			test.log(LogStatus.SKIP, result.getName().toUpperCase() +" RunMode is NO");
			throw new SkipException("Skipping the test as the RunMode is No: " + result.getName());
			
		}
		
	}

	public void onTestSkipped(ITestResult result) {
		/*
		 * test.log(LogStatus.SKIP, result.getName().toUpperCase()
		 * +" Skipped, RunMode is NO"); report.endTest(test); report.flush();
		 */
		
		report.endTest(test);
		report.flush();
	}
	
}
