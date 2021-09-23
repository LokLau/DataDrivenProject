package testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.TestBase;

public class LoginTest extends TestBase {
	
	@Test
	public void logInAsBankManager() throws IOException, InterruptedException {
		
		 
		//verifyEquals("123","abc");

		//Thread.sleep(3000);
		
		click("bmlBtn_CSS");
	
	    Assert.assertTrue(isElementPresent(By.cssSelector(oR.getProperty("addCusBtn_CSS"))), "Login not successful!");
		
	
		click("addCusBtn_CSS");
		
		//Assert.fail("log in unsuccessful!!!");
		
		
	}

	
	
}
