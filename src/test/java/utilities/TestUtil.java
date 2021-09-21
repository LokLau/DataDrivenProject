package utilities;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import base.TestBase;

public class TestUtil extends TestBase {
	
	public static String screenPath;
	

	public static void takeScreenShot () throws IOException {
		
		String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\screenshots\\";
		
		Date date = new Date();
		String screenName = date.toString().replace(":", "_").replace(" ", "_") + ".jpg";
		
		screenPath = filePath+screenName;
		
		TakesScreenshot screen = ((TakesScreenshot)driver);
		
		File scrFile = screen.getScreenshotAs(OutputType.FILE);
		
		File desFile = new File(screenPath);
		
		FileUtils.copyFile(scrFile, desFile);
		
		
	}
	
	@DataProvider(name="DP")
	public Object[][] testData(Method m) {
		
		String sheetName = m.getName();
		
	//	System.out.println(sheetName);
		
		int rowCount = excel.getRowCount(sheetName);
		int colCount = excel.getColumnCount(sheetName);
		
		
		Object[][] data = new Object[rowCount-1][1];
		
		Hashtable<String, String> table = null;
		
		for (int r = 0; r <= rowCount-2; r++ ) {
			table = new Hashtable<String,String>();
			
			for (int c=0; c < colCount; c++ ){
				table.put(excel.getCellData(sheetName, c, 1), excel.getCellData(sheetName, c, r+2));
				data[r][0] =  table;
			}
		}
		
		return data;
	}
	
}
