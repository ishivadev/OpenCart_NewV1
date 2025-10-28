package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginTestDDT extends BaseClass {

	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class)  // Getting the value from DataProviders class
	public void verifyLoginDDT(String email, String pwd, String expValue)   // Passing the data "email, pwd, expValue" for used in below method
	{
		
		logger.info("***** Starting TC_002_LoginTest *****");
		
		try
		{
			// Home page
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			
			
			// Login page
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(email);
			lp.setPassword(pwd);
			lp.clickLoginBtn();
			
			
			// MyAccount page
			MyAccountPage myacc = new MyAccountPage(driver);
			boolean exp_myacc_text = myacc.exp_MyAccount();
			
	
			
			// Data is valid - Login Success  - Test Passed - Logout
						  // - Login Failed   - Test Failed 
			
			if(expValue.equalsIgnoreCase("Valid"))
			{
				if(exp_myacc_text == true)
				{
					myacc.clickLogout();   //Logout user account
					Assert.assertTrue(true);
				}
				else 
				{
					Assert.assertTrue(false);
				}
			}		
			
			
			// Data is invalid - Login Success  - Test Failed - Logout
							// - Login Failed   - Test Passed 
			
			if(expValue.equalsIgnoreCase("Invalid"))
			{
				if(exp_myacc_text == true)
				{
					myacc.clickLogout();   //Logout user account
					Assert.assertTrue(false);
				}
				else 
				{
					Assert.assertTrue(true);
				}
			}
		}
		catch(Exception e)
		{
			Assert.fail(e.getMessage());
		}
				
		
		logger.info("***** Finished TC_002_LoginTest *****");
	}
	
}
