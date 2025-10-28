package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage{

	
	public HomePage(WebDriver driver)
	{
		super(driver);
	}
	
	
	// Locators
	
	@FindBy( xpath = "//span[normalize-space()='My Account']")
	WebElement link_myaccount;
	
	@FindBy( xpath = "//a[normalize-space()='Register']")
	WebElement link_registration;
	
	@FindBy( xpath = "//ul[@class='dropdown-menu dropdown-menu-right']//a[normalize-space()='Login']")
	WebElement link_login;
	
	// Methods
	public void clickMyAccount()
	{
		link_myaccount.click();
	}
	
	public void clickRegistration()
	{
		link_registration.click();
	}
	
	public void clickLogin()
	{
		link_login.click();
	}
	
}
