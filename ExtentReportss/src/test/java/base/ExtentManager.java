package base;

import java.io.File;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	private static ExtentReports extentReport;

	public static ExtentReports createInstance() {
		
		String fileName=getReportName();
		String directory=System.getProperty("user.dir")+"/reports/";
		new File(directory).mkdirs();
		String path=directory+fileName;
		
		ExtentHtmlReporter htmlReporter=new ExtentHtmlReporter(path);
		
		 htmlReporter.config().setEncoding("utf-8");
		 htmlReporter.config().setDocumentTitle("Automation Reports");
		 htmlReporter.config().setReportName("Automation Test Results");
		 htmlReporter.config().setTheme(Theme.STANDARD);
		 
		 extentReport=new ExtentReports();
		 extentReport.setSystemInfo("Organization","DO Testing");
		 extentReport.setSystemInfo("Browser","Chrome");
		 extentReport.setSystemInfo("Tester Name","Dilip Raut");
		 extentReport.attachReporter(htmlReporter);
		 
		 return extentReport;
		
	}

	public static String getReportName() {
		Date d = new Date();
		String fileName = "AutomationReport_" + "_" + d.toString().replace(":", "_").replace(" ", "_") + ".html";
		return fileName;
	}
}
