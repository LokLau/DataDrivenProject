package rough;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import utilities.ExcelReader;

public class TestProperties {
	
	public static ExcelReader excel = new ExcelReader("C:\\Users\\zilel\\OneDrive\\Desktop\\LOK\\Selenium_Project\\DataDrivenFrameWork\\src\\test\\resources\\excels\\testdata.xlsx");
	 
	public static void main(String[] args) throws IOException {
		
//		 Properties config = new Properties();
//		 FileInputStream fis = new FileInputStream("C:\\Users\\zilel\\OneDrive\\Desktop\\LOK\\Selenium Project\\DataDrivenFrameWork\\src\\test\\resources\\properties\\Config.properties");
//	
//		 
//		 config.load(fis);
//		 config.getProperty("browser");
		
		//int rows = excel.getRowCount("Login");
		
		System.out.println(System.getProperty("user.dir")+"\\test-output\\html\\extent.html" );
		 
		 
	}
}
