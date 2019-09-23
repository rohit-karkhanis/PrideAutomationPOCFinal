package com.testcases.driverscripts;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.operations.Common.Readconfig;
import com.operations.Common.Script_executor;
import com.operations.Common.Xls_writer;
import com.operations.Master_data;

import io.github.bonigarcia.wdm.WebDriverManager;

//import com.Utilities.SendEmail;
import com.Utilities.SendStatusReport;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Execute_MainScript {

	Platform macOS;

	WebDriver webdriver=null;

	public static Logger Applog;

	public static ExtentHtmlReporter htmlreporter;

	public static ExtentReports extent;

	public static ExtentTest test;

	Script_executor scre = new Script_executor();

	String browser_name;

	String Testcasenumber;

	String Sitename;
	String Channel;
	String Device;
	int DeviceScrHeight;
	int DeviceScrWidth;
	static Date Startdate;
	static Date Enddate;
	long startTime ;

	Xls_writer xls_writer=new Xls_writer();

	Readconfig rc =new Readconfig();
	SendStatusReport email =new SendStatusReport();
	

	public static SimpleDateFormat StartTime;
	public static SimpleDateFormat EndTime;

	Map<Integer, Object[]> Testcase_skipresults = new LinkedHashMap<Integer, Object[]>();

	Map<Integer, Object[]> Testscase_failresults = new LinkedHashMap<Integer, Object[]>();

	private SoftAssert softAssert = new SoftAssert();

	@BeforeSuite()

	public void Pre_requisite() throws IOException{
		
		rc.getObjectRepository();

		Applog=Logger.getLogger(rc.SiteName);


		PropertyConfigurator.configure("./resources/Log4j.properties");

		htmlreporter= new ExtentHtmlReporter(System.getProperty("user.dir") +"/test-output/STMExtentReport_"+rc.SiteName+".html");

		extent = new ExtentReports ();

		extent.attachReporter(htmlreporter);

		

		Startdate = new Date() ;
		StartTime = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss") ;
		//startTime = System.
		Applog.info("Execution started on " + StartTime.format(Startdate));
		System.out.println("Execution started on : " + StartTime.format(Startdate));
		//dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm") ;
		//Applog.info("Execution started on Firefox" + dateFormat.format(date));
	}


	@Parameters({"browser","Channel","Device","DeviceScrHeight","DeviceScrWidth"})
	@BeforeTest

	public void EnvSetup(String browser,String Channel,String Device,int DeviceScrHeight,int DeviceScrWidth) throws IOException
	{
		this.browser_name=browser;
		this.Channel=Channel;
		//ChromeOptions options = new ChromeOptions();
		//options.addArguments("start-maximized");
		//options.addArguments("--disable-notifications");

		if (Channel.equalsIgnoreCase("Desktop")) {


			if (browser.equalsIgnoreCase("firefox"))
			{

				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") +"/Browser_files/geckodriver-v0.23.0-win64/geckodriver.exe");
				webdriver = new FirefoxDriver();
				webdriver.manage().window().maximize();
				this.browser_name=browser;
				this.Channel=Channel;

				Applog.info("Execution started on Firefox" + StartTime.format(Startdate));


			} else if (browser.equalsIgnoreCase("chrome"))
			{
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") +"/Browser_files/chromedriver_win32/chromedriver.exe");
				//WebDriverManager.chromedriver().setup();
				webdriver = new ChromeDriver();
				//Dimension d = new Dimension(DeviceScrWidth, DeviceScrHeight);
				//webdriver.manage().window().setSize(d);
				webdriver.manage().window().maximize();
				
				this.browser_name=browser;
				this.Channel=Channel;
				//Applog.info(" Execution started on Chrome" + dateFormat.format(date));

			}

			else
			{
				throw new IllegalArgumentException("The Browser Type is Undefined");
			}
		}

		else if (Channel.equalsIgnoreCase("Mobile")) {

			this.Device=Device;
			this.DeviceScrHeight=DeviceScrHeight;
			this.DeviceScrWidth=DeviceScrWidth;

			if (browser.equalsIgnoreCase("firefox"))
			{

				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") +"/Browser_files/geckodriver-v0.23.0-win64/geckodriver.exe");
				webdriver = new FirefoxDriver();
				Dimension d = new Dimension(DeviceScrWidth,DeviceScrHeight);
				webdriver.manage().window().setSize(d);
				//Applog.info("Mobile Test execution started on Firefox" + dateFormat.format(date));


			} else if (browser.equalsIgnoreCase("chrome"))
			{
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") +"/Browser_files/chromedriver_win32/chromedriver.exe");
				//WebDriverManager.chromedriver().setup();
				webdriver = new ChromeDriver();
				Dimension d = new Dimension(DeviceScrWidth,DeviceScrHeight);
				webdriver.manage().window().setSize(d);
				Applog.info("Mobile Test execution started on Chrome" + StartTime.format(Startdate));

			}

			else
			{
				throw new IllegalArgumentException("The Browser Type is Undefined");
			}

		}
		else {
			throw new IllegalArgumentException("The Channel Type is Undefined");
		}
	}






	@Test(priority =1 ,dataProvider = "Fetch_Master_data",dataProviderClass=Master_data.class)
	public void ExecuteTest(String Section,String Functionality,String Testcasenumber, String Testcase_description , String Executionmode,String Severity) throws Throwable  
	{

		this.Testcasenumber=Testcasenumber;
		this.Sitename=rc.SiteName;

		if(Executionmode.equalsIgnoreCase("Yes")){
			try {
				if(Channel.equalsIgnoreCase("Desktop")){
					scre.Execute_script(Sitename,browser_name,"./Input_files/Actual_testcases/"+rc.SiteName+"/","./Output_files/"+StartTime.format(Startdate)+"/"+Sitename+"/"+browser_name+"/",
							"./Screenshots/"+StartTime.format(Startdate)+"/"+rc.SiteName+"/"+browser_name+"/", webdriver,Section,Functionality, Testcasenumber, Testcase_description, Executionmode, Severity,extent,Applog);

				}
				else if (Channel.equalsIgnoreCase("Mobile")) {
					scre.Execute_script(Sitename,browser_name,"./Input_files/Actual_testcases/"+rc.SiteName+"/","./Output_files/"+StartTime.format(Startdate)+"/"+Sitename+"/"+browser_name+"/"+Device+"/",
							"./Screenshots/"+StartTime.format(Startdate)+"/"+rc.SiteName+"/"+browser_name+"/"+Device+"/", webdriver,Section,Functionality, Testcasenumber, Testcase_description, Executionmode, Severity,extent,Applog);

				}

			} catch (Exception e) {
				StringWriter stack = new StringWriter();
				e.printStackTrace(new PrintWriter(stack));
				xls_writer.GenerateFailReport(Testscase_failresults, rc.SiteName, browser_name, Functionality, Testcasenumber, Severity,"./Failed_Reports/"+StartTime.format(Startdate)+"/"+rc.SiteName+"/"+browser_name+"/");
				Assert.fail(stack.toString());
				Applog.error(stack.toString());
				stack.flush();
				softAssert.assertAll();

			}


		}
		else{

			xls_writer.GenearateSkipFile(Testcase_skipresults,Functionality, Testcasenumber, Severity,"./Output_files/"+StartTime.format(Startdate)+"/"+rc.SiteName+"/"+browser_name+"/");
			Applog.info(Testcasenumber + " has been skipped for this execution...");
			throw new SkipException(Testcasenumber +" has been skipped..");
		}
	}

	@AfterMethod
	public void TestResults(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {

			if (Channel.equalsIgnoreCase("Mobile")) {
				test = extent.createTest(Sitename+"_"+browser_name+"_"+Device+"_"+Testcasenumber);
				test.fail(MarkupHelper.createLabel(Testcasenumber+" has been failed....", ExtentColor.RED));
			}
			else {
				test = extent.createTest(browser_name+"_"+Testcasenumber);
				test.fail(MarkupHelper.createLabel(Testcasenumber+" has been failed....", ExtentColor.RED));

			}

		}        
		else if (result.getStatus() == ITestResult.SKIP) {

			if (Channel.equalsIgnoreCase("Mobile")) {
				test = extent.createTest(Sitename+"_"+browser_name+"_"+Device+"_"+Testcasenumber);
				test.skip(MarkupHelper.createLabel(Testcasenumber+" has been skipped for this execution...", ExtentColor.YELLOW));
			}

			else {

				test = extent.createTest(browser_name+"_"+Testcasenumber);
				test.skip(MarkupHelper.createLabel(Testcasenumber+" has been skipped for this execution...", ExtentColor.YELLOW));
			}

		}
		else if (result.getStatus() == ITestResult.SUCCESS) {

			if (Channel.equalsIgnoreCase("Mobile")) {
				test = extent.createTest(Sitename+"_"+browser_name+"_"+Device+"_"+Testcasenumber);
				test.pass(MarkupHelper.createLabel(Testcasenumber + "has been passed", ExtentColor.GREEN));
			}
			else {
				test = extent.createTest(browser_name+"_"+Testcasenumber);
				test.pass(MarkupHelper.createLabel(Testcasenumber + "has been passed", ExtentColor.GREEN));

			}

		}
	}


	@AfterSuite

	public void close() {
		extent.flush();
		Enddate = new Date() ;
		EndTime = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss") ;
		Applog.info("Execution ended on : " + EndTime.format(Enddate));
		System.out.println("Execution ended on : " + EndTime.format(Enddate));
		long diff = Enddate.getTime() - Startdate.getTime();
		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		System.out.print("Total Time for Execution : ");
		System.out.print(diffHours + " hours, ");
		System.out.print(diffMinutes + " minutes, ");
		System.out.print(diffSeconds + " seconds.");
		email.performTask();
	}  


	@Parameters("browser")
	@AfterSuite

	public void closebrowser(String browser) throws IOException{
		if (browser.equalsIgnoreCase("firefox")){
			webdriver.close();	
			Runtime rt = Runtime.getRuntime();
			rt.exec("taskkill /F /IM geckodriver.exe");
		}
		else if(browser.equalsIgnoreCase("chrome")){
			webdriver.close();
			Runtime rt = Runtime.getRuntime();
			rt.exec("taskkill /F /IM chromedriver.exe");

		}
		else if(browser.equalsIgnoreCase("ie")){
			webdriver.close();
			Runtime rt = Runtime.getRuntime();
			rt.exec("taskkill /F /IM IEDriverServers.exe");
		}
		else if(browser.equalsIgnoreCase("safari")){
			webdriver.close();
		}

	}
	
	
}