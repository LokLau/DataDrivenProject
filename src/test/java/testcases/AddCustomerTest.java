package testcases;

import java.util.Hashtable;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.TestBase;
import utilities.TestUtil;

public class AddCustomerTest extends TestBase {

	@Test (dataProviderClass = TestUtil.class, dataProvider = "DP")
	public void addCustomerTest(Hashtable<String,String> data) {
		
	
	
		type("fNameInput_CSS", data.get("FirstName"));
		type("lNameInput_CSS", data.get("LastName"));
		type("pwdInput_CSS", data.get("Password"));
		click("addCustomerBtn_CSS");
		
		
	   Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		
		
		Assert.assertTrue(alert.getText().contains("Customer added successfully with customer"));
		alert.accept();
		
	
		
	}
	
	
}

