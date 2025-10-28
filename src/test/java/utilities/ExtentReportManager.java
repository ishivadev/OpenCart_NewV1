package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
//import java.net.URL;
import java.net.URL;

//Extent report 5.x...//version

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//import org.apache.commons.mail.DefaultAuthenticator;
//import org.apache.commons.mail.ImageHtmlEmail;
//import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;	// The full report
	public ExtentTest test; 		// One test case entry
	
	String repName;

	
	// onStart
	// ITestContext is an interface provided by TestNG (in package org.testng).
	// ITestContext gives information about the whole <test> block in your XML suite. Like - getName(),getStartDate()/getEndDate(),getSuite(), etc.
	public void onStart(ITestContext testContext) {
		
		/*
		Purpose					Pattern						Example
		Readable + short		dd_MMM_yyyy					14_Aug_2025
		Readable + time			dd_MMM_yyyy_HH-mm-ss		14_Aug_2025_23-22-15
		Sortable + safe			yyyyMMdd_HHmmss				20250814_232215
		With weekday			EEE_dd_MMM_yyyy				Thu_14_Aug_2025
		Full date time words	dd-MMM-yyyy_hh-mm-a			14-Aug-2025_11-22-PM 
		*/
		
		/*SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt=new Date();
		String currentdatetimestamp=df.format(dt);
		*/
		
		String timeStamp = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss").format(new Date());// Time stamp
		//System.out.println(timeStamp);
		repName = "Test-Report-" + timeStamp + ".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);// Specify location of the report

		sparkReporter.config().setDocumentTitle("Opencart Automation Report"); // Title of report
		sparkReporter.config().setReportName("Opencart Functional Testing"); // Name of the report
		sparkReporter.config().setTheme(Theme.DARK);
		
		// **** This is your main report file — like your entire notebook where all test results will be written. 
		// You usually initialize it at the start of your suite in onStart() ****
		extent = new ExtentReports();	
		
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "Opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environemnt", "QA");
		
		String os = testContext.getCurrentXmlTest().getParameter("os"); // Getting the 'OS' from the xml file using 'testContext'. 'testContext' could be any name, it could be result, sampleData etc.
		extent.setSystemInfo("Operating System", os);
		
		String browser = testContext.getCurrentXmlTest().getParameter("browser"); // Getting the 'Browser' from the xml file using 'testContext'.
		extent.setSystemInfo("Browser", browser);
		
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups(); // // Getting the 'Groups' from the xml file using 'testContext' if groups are available. Like Sanity, Regression, Master etc.
		if(!includedGroups.isEmpty()) {
		extent.setSystemInfo("Groups", includedGroups.toString());
		}
	}

	
	// onTestSuccess
	// ITestResult is an interface in TestNG (package org.testng), and it holds: getName(),getMethod(),getTestClass(),getStatus() etc.
	// result is a parameter of type ITestResult provided by TestNG.
	public void onTestSuccess(ITestResult result) {
	
		test = extent.createTest(result.getTestClass().getName()); 	// This represents one test case inside that report — like one page or section in the notebook. Every time a test runs (pass/fail/skip), you create a new test entry for it.
		test.assignCategory(result.getMethod().getGroups()); // To display 'Groups' in report
		test.log(Status.PASS,result.getName()+" got successfully executed...!!");
	}

	
	// onTestFailure
	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.FAIL,result.getName()+" got failed.");
		test.log(Status.INFO, result.getThrowable().getMessage());
		
		// Capturing the screenshot
		try {
			//String imgPath = new BaseClass().captureScreen(result.getName());   // We can use it like this to avoid object creation like below.
			
			BaseClass baseObj = new BaseClass();
			String imgPath = baseObj.captureScreen(result.getName()); // Calling the 'captureScreen' method from 'BaseClass and passes <test case name> as a parameter
			
			test.addScreenCaptureFromPath(imgPath); // 'test' is the report and here the <imgpath> is attaching with the report
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	
	// onTestSkipped
	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName()+" got skipped.");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}

	
	// onTestSkipped
	public void onFinish(ITestContext testContext) {
		
		extent.flush(); // Bind all the data in the report file.
		
		String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName; // Getting current path of the report.
		File extentReport = new File(pathOfExtentReport);	// capture the report file
		
		try {
			Desktop.getDesktop().browse(extentReport.toURI()); // Opening the report file automatically once execution finish. 
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		// Sending the report file URL to a user on Gmail
		/*  try {
			  
			  URL url = new  URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName); // Getting the report file path act change it as a URL.
		  
			  // Create the email message 
			  ImageHtmlEmail email = new ImageHtmlEmail();
			  email.setDataSourceResolver(new DataSourceUrlResolver(url));
			  email.setHostName("smtp.googlemail.com"); 
			  email.setSmtpPort(465);
			  email.setAuthenticator(new DefaultAuthenticator("shivadevm@gmail.com","password")); 
			  email.setSSLOnConnect(true);
			  email.setFrom("shivadevm@gmail.com"); //Sender
			  email.setSubject("Test Results");
			  email.setMsg("Please find Attached Report....");
			  email.addTo("shivadevm@gmail.com"); //Receiver 
			  email.attach(url, "extent report", "please check report..."); 
			  email.send(); // send the email 
		  }
		  catch(Exception e) 
		  { 
			  e.printStackTrace(); 
		  }
		 */ 
		 
	}

}
