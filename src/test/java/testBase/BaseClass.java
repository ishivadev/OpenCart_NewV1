package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.LogManager;  //Log4j package
import org.apache.logging.log4j.Logger;  //Log4j package

public class BaseClass {

	public WebDriver driver;
	public Logger logger;
	public Properties p;
	
	@BeforeClass
	@Parameters({"os","browser"})
	public void setupApp(String os, String br) throws IOException
	{
		FileReader file = new FileReader(".//src//test//resources//config.properties");
		p = new Properties();
		p.load(file);
		
		
		logger = LogManager.getLogger(this.getClass()); // Will get the class as per the call
		
		switch(br.toLowerCase())
		{
		case "chrome" : driver = new ChromeDriver(); break;
		case "edge" : driver = new EdgeDriver(); break;
		case "firefox" : driver = new FirefoxDriver(); break;
		default : System.out.println("Invalid browser name.."); return;
		
		}
		
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//driver.get("https://tutorialsninja.com/demo/");
		driver.get(p.getProperty("appURL"));
		driver.manage().window().maximize();
	}
	
	@AfterClass
	public void tearDown() throws InterruptedException
	{
		Thread.sleep(2000);
		driver.quit();
	}
	
	
	
	
	
	
	
	// Random methods
	
	@SuppressWarnings("deprecation")
	public String randomFirstName()
	{
		String generatedfirstname =  RandomStringUtils.randomAlphanumeric(5);
		return generatedfirstname;
	}
	
	
	@SuppressWarnings("deprecation")
	public String randomLastName()
	{
		String generatedlastname = RandomStringUtils.randomAlphanumeric(5);
		return generatedlastname;
	}
	
	@SuppressWarnings("deprecation")
	public String randomTelephone()
	{
		String generatedtelNumber = RandomStringUtils.randomNumeric(10);
		return generatedtelNumber;
	}
	
	@SuppressWarnings("deprecation")
	public String randomEmail()
	{
		String data1 = RandomStringUtils.randomAlphabetic(4);
		String data2 = RandomStringUtils.randomAlphabetic(4);
		
		String generatedEmail = data1 + data2;
		System.out.println("Random Email Id is : " + generatedEmail + "@gmail.com");
		return generatedEmail;
	}
	
	@SuppressWarnings("deprecation")
	public String randomPassword()
	{
		String strVal1 = RandomStringUtils.randomAlphabetic(4);
		String strVal2 = RandomStringUtils.randomNumeric(10);
		String generatedpassword = (strVal1+"@"+strVal2);
		System.out.println("Random Password is : " + generatedpassword);
		return generatedpassword;
	}
	
	
	
}
