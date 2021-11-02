package testclasses;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;

public class DemoTestClass {
	
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extentReport;
	public ExtentTest extentTest;
	public WebDriver driver;
	
	
	
	 @BeforeClass
	  public void beforeClass() {
		 
		 htmlReporter=new ExtentHtmlReporter(System.getProperty("user.dir")+"//reports//extent.html");
		 htmlReporter.config().setEncoding("utf-8");
		 htmlReporter.config().setDocumentTitle("Automation Reports");
		 htmlReporter.config().setReportName("Automation Test Results");
		 htmlReporter.config().setTheme(Theme.STANDARD);
		 
		 extentReport=new ExtentReports();
		 extentReport.setSystemInfo("Organization","DO Testing");
		 extentReport.setSystemInfo("Browser","Chrome");
		 extentReport.setSystemInfo("Tester Name","Dilip Raut");
		 extentReport.attachReporter(htmlReporter);
		 
		 
		 
		 System.setProperty("webdriver.chrome.driver","F:\\Study\\Velocity Material\\Drivers\\Chrome\\chromedriver_win32\\chromedriver.exe");
		 driver=new ChromeDriver();
		 driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		 driver.manage().window().maximize();
		 driver.get("https://www.google.co.in");
		 
	  }
	 
	 
	 @AfterClass
	  public void afterClass() {
		 
		 driver.quit();
		 extentReport.flush();
	  }
	
  @Test
  public void testSuccessfull() {
	  
	  
	  extentTest=extentReport.createTest("Successfull Test");
	  //extentTest.log(Status.PASS, "Test Method Successfull");
  }
  
  @Test
  public void testFailed() {
	  extentTest=extentReport.createTest("Failed Test");
	  //extentTest.log(Status.FAIL, "Test Method Successfull");
	  Assert.fail("Executing Fail Test Method");
  }
  
  @Test
  public void testSkipped() {
	  extentTest=extentReport.createTest("Skipped Test");
	  //extentTest.log(Status.SKIP, "Test Method Successfull");
	  throw new SkipException("Executing Skipped Test Case");
  }
  @AfterMethod
  public void afterMethod(ITestResult result) {
	  
	  String methodName=result.getMethod().getMethodName();
	  
	  if(result.getStatus()==ITestResult.FAILURE) {
		  String exceptionMessage=Arrays.toString(result.getThrowable().getStackTrace());
		  extentTest.fail("<details><Summary><b><font color=red>Exception Occured click to see details:"
				  +"</font></b></summary>"+exceptionMessage.replaceAll(",","<br>")+"</details> \n");
		  
		  String path=takesScreenshot(result.getMethod().getMethodName());
		  
		  try {
			  extentTest.fail("<b><font color=red>" + "Screenshot Of Failure" +"</font></b>",
		    MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		  }catch(IOException e) {
			  extentTest.fail("Test Failed ,Cannot attach screenshot");
		  }
		  
		  String logText= "<b> Test Method" +  methodName +"  is Failed</b>";
		  
		  Markup m=MarkupHelper.createLabel(logText, ExtentColor.RED);
		  extentTest.log(Status.FAIL, m);
	  }else if(result.getStatus()==ITestResult.SUCCESS) {
            String logText= "<b> Test Method" + methodName +" Successfull</b>";
		  
		  Markup m=MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		  extentTest.log(Status.PASS, m);

	  }else if(result.getStatus()==ITestResult.SKIP) {
          String logText= "<b> Test Method" + methodName +" Skipped</b>";
		  
		  Markup m=MarkupHelper.createLabel(logText, ExtentColor.ORANGE);
		  extentTest.log(Status.SKIP, m);

	  }
  }

 
 public String takesScreenshot(String methodName) {
	 String fileName=getScreenshotName(methodName);
	 String directory=System.getProperty("user.dir")+"/screenshots/";
	 new File (directory).mkdirs();
	 String path=directory+fileName;
	 
	 
	 try {
		File screenshot= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshot, new File(path));
		System.out.println("**********************");
		System.out.println("Screenshot Stored at  :  "+path);
		System.out.println("**********************");
		
	 }catch(Exception e) {
		 e.printStackTrace();
	 }
	 return  path;
 }
  
  
  
  public static String getScreenshotName(String methodName) {
	  Date d=new Date();
	  String fileName=methodName+"_"+d.toString().replace(":","_").replace(" ","_")+".png";
	  return fileName;
  }
  
  

}
