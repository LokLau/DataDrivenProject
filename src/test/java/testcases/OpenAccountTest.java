package testcases;

import java.util.Hashtable;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import utilities.TestUtil;

public class OpenAccountTest extends TestBase {
	
	@Test (dataProviderClass = TestUtil.class, dataProvider = "DP")
	
	public void openAccountTest(Hashtable<String,String> data) {
		
		if(data.get("RunMode").equalsIgnoreCase("N")) {
			
			test.log(LogStatus.SKIP, " Skipped, Data RunMode is NO");
			throw new SkipException("Skipping the test as the Data RunMode is No" );
		}
		
		click("openAccountBtn_CSS");
		select("customerSelect_ID", data.get("CustomerName"));
		select("currencySelect_ID", data.get("Currency"));
		click("processBtn_CSS");
		
		 Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			
			
			Assert.assertTrue(alert.getText().contains("Account created successfully"));
			alert.accept();
			
	}

}
