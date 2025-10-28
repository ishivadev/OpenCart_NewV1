package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {

	@Test
	public void verifyLogin()
	{
		
		logger.info("***** Starting TC_002_LoginTest *****");
		
		// Home page
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		
		// Login page
		LoginPage lp = new LoginPage(driver);
		lp.setEmail(p.getProperty("email"));
		lp.setPassword(p.getProperty("password"));
		lp.clickLoginBtn();
		
		
		// MyAccount page
		MyAccountPage myacc = new MyAccountPage(driver);
		boolean exp_myacc_text = myacc.exp_MyAccount();
		
		if(exp_myacc_text)
		{
			Assert.assertEquals(exp_myacc_text, true);
		}
		else
		{
			Assert.assertTrue(false);
		}
		
		logger.info("***** Finished TC_002_LoginTest *****");
	}
	
}
