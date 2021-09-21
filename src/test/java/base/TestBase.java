package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
//import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import utilities.ExcelReader;
import utilities.ExtentManager;
import utilities.TestUtil;

public class TestBase {

	/*	
	 * Initialize ...... 
	 * WebDrive
	 * Logs
	 * ExtentReport
	 * Excel
	 * DB
	 * Mail	
		*/
	
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties oR = new Properties();
	public static FileInputStream fIS;
    public static Logger log = Logger.getLogger(TestBase.class.getName());
 
	public static ExcelReader excel = new ExcelReader("C:\\Users\\zilel\\OneDrive\\Desktop\\LOK\\Selenium_Project\\DataDrivenFrameWork\\src\\test\\resources\\excels\\testdata.xlsx");
	public static WebDriverWait wait;
	
	public ExtentReports report =  ExtentManager.getInstance();
	public static ExtentTest test;
	
	@BeforeSuite
	public void setUp(){
		
	//	PropertyConfigurator.configure("C:\\Users\\zilel\\OneDrive\\Desktop\\LOK\\Selenium_Project\\DataDrivenFrameWork\\src\\test\\resources\\log4j.properties");
		
		if (driver == null) {
			
	  // load property to config from config.properties file
			try {
				
				fIS = new FileInputStream("C:\\Users\\zilel\\OneDrive\\Desktop\\LOK\\Selenium_Project\\DataDrivenFrameWork\\src\\test\\resources\\properties\\Config.properties");
			
			} catch(FileNotFoundException e){
				e.printStackTrace();
			} 
			
			
			try {
				config.load(fIS);
		//		log.info("configuration property loaded");
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//
			
		// load property to oR from OR.properties file
			
			try {
				
				fIS = new FileInputStream("C:\\Users\\zilel\\OneDrive\\Desktop\\LOK\\Selenium_Project\\DataDrivenFrameWork\\src\\test\\resources\\properties\\OR.properties");
				log.info("OR property loaded");
				
			} catch(FileNotFoundException e){
				e.printStackTrace();
			} 
			
			
			try {
				oR.load(fIS);
			} catch (IOException e) {
				e.printStackTrace();
			}
		// end of file loading
			
			
	//load webdriver
			if(config.getProperty("browser").equals("chrome")) {
				
				
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				
				
			}else if(config.getProperty("browser").equals("edge")) {
				
				WebDriverManager.edgedriver().setup();
				 driver = new EdgeDriver();
				
			}else if(config.getProperty("browser").equals("firefox")) {
				
				 WebDriverManager.firefoxdriver().setup();
				 driver = new FirefoxDriver();
				
			}
		//	
			
				
			driver.get(config.getProperty("testSiteUrl"));
            driver.manage().window().maximize();
            
         
            
            int waitTime = Integer.parseInt(config.getProperty("implicit.wait"));
            driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
            
            wait = new WebDriverWait(driver, 5);
            		
           
			
		}// end if IF(driver==null) statement 
		
		
		
	} // end of setUp method
	
	
	
	@AfterSuite
	public void tearDown() throws InterruptedException{
		
		
		if(driver != null) {
			Thread.sleep(2000);		
			driver.quit();
			
			try {
				fIS.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void click (String locator) {
		
		if(locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(oR.getProperty(locator))).click();
		}else if(locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(oR.getProperty(locator))).click();
		}else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(oR.getProperty(locator))).click();
		}
		
		test.log(LogStatus.INFO, "Clicking on: " + locator);
	}
	
	public void type (String locator, String value) {
		
		if(locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(oR.getProperty(locator))).sendKeys(value);
		}else if(locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(oR.getProperty(locator))).sendKeys(value);
		}else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(oR.getProperty(locator))).sendKeys(value);
		}
		
	
		test.log(LogStatus.INFO, "Typing in: " + locator +"- entered value: " + value);
	}
	
	static WebElement dropdown;
	public void select (String locator, String value) {
		
		
				if(locator.endsWith("_CSS")) {
					dropdown = driver.findElement(By.cssSelector(oR.getProperty(locator)));
				}else if(locator.endsWith("_XPATH")) {
					dropdown = driver.findElement(By.xpath(oR.getProperty(locator)));
				}else if (locator.endsWith("_ID")) {
					dropdown = driver.findElement(By.id(oR.getProperty(locator)));
				}
		
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
		
		test.log(LogStatus.INFO, "Selecting from dropdown: " + locator +"- selected value: " + value);
	}

	public boolean isElementPresent(By by) {
		
		try {
			driver.findElement(by);
			return true;
			
		}catch(NoSuchElementException e) {
			log.error("No Such Element");
			return false;
			
		}
	}
	
	
	
	public static void verifyEquals(String expected, String actual) throws IOException {
		
		
		try{
			
		    
			Assert.assertEquals(expected, actual);
			
		}catch(Throwable t) {
			
			
			TestUtil.takeScreenShot();
			
			test.log(LogStatus.FAIL, "VeryfyEquals FAIL with exception: " + t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenPath));
			
			/*
			 * Reporter.log("Verify Equals Failure: " + t.getMessage() + "</br>");
			 * Reporter.log("<a target=\"_blank\" href=\""+ TestUtil.screenPath
			 * +"\"> Screenshot  </a>"); Reporter.log("</br>");
			 */
			
			
		}
	}
	
	public static boolean isTestRunnable(String testName, ExcelReader excel) {
		
		String sheetName = "TestSuite";
		
		int rows = excel.getRowCount(sheetName);
		
		for (int rowNum=2; rowNum <= rows; rowNum++ ) {
			
			String testcase = excel.getCellData(sheetName, "TCID", rowNum);
			
			if( testcase.equalsIgnoreCase(testName)) {
				
				String runmode = excel.getCellData(sheetName, "RunMode", rowNum);
				
				if (runmode.equalsIgnoreCase("Y")) {
					return true;
				} else {
					return false;
				}
			}
					
		}
		return true;
		
		
	}
	
}
