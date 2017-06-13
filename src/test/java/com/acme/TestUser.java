package com.acme;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestUser {
	WebDriver driver;
	
	@Before
	public void setUp() throws Exception {
		// Uncomment appropriate driver for your system
		//System.setProperty("webdriver.gecko.driver", "webdrivers/win64/geckodriver.exe"); // 64 Bit Windows
		System.setProperty("webdriver.gecko.driver", "webdrivers/linux64/geckodriver"); // 64 Bit Linux
		
		// Set up the driver global to this class
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		this.SignInValid();
	}
 
	// Attempt to sign in with valid info and succeed
	
	public void SignInValid() {
		driver.get("http://localhost:8080/");
		driver.findElement(By.id("login")).click();
		driver.findElement(By.id("username")).sendKeys("frank.paul@acme.com");
		driver.findElement(By.id("password")).sendKeys("starwars");
		driver.findElement(By.cssSelector(".btn")).click();
		assertTrue(driver.findElement(By.id("account-menu")).isDisplayed());
	}
	// AcmePass app should load list of saved passwords
	@Test
	public void VisitAcmePass() {
		driver.get("http://localhost:8080/#/acme-pass");
		String TestTitle = driver.getTitle();
		assertEquals("ACMEPasses", TestTitle);
		//junit.org
	}
	
	// Create a new AcmePass entry for a site and ensure worked
	@Test
	public void CreateNewPassValid() {
		this.VisitAcmePass();
		driver.get("http://localhost:8080/#/acme-pass/new");
		driver.findElement(By.id("field_site")).sendKeys("testSite.com");
		driver.findElement(By.id("field_login")).sendKeys("testlogin");
		driver.findElement(By.id("field_password")).sendKeys("testpass");
		driver.findElement(By.cssSelector("button[type^=submit]")).click(); //save
		String site = driver.findElement(By.xpath("//tbody/tr[1]/td[2]")).getText();
		assertEquals ("testSite.com", site);
		String login = driver.findElement(By.xpath("//tbody/tr[1]/td[2]")).getText();
		assertEquals ("testSite.com", site);
		String pass = driver.findElement(By.xpath("//tbody/tr[1]/td[2]")).getText();
		assertEquals ("testSite.com", site);
	}
	
	// Forget to enter a site and check for failure
	@Test
	public void CreateNewPassInvalid() {
		this.SignInValid();
		this.VisitAcmePass();
		driver.get("http://localhost:8080/#/acme-pass/new");
		driver.findElement(By.id("field_login")).sendKeys("testlogin");
		driver.findElement(By.id("field_password")).sendKeys("testpass");
		driver.findElement(By.cssSelector("button[disabled^=disabled]"));
		System.out.print("Site field missing, worked");
		
		driver.findElement(By.id("field_site")).sendKeys("something.com");
		driver.findElement(By.id("field_login")).clear();
		driver.findElement(By.id("field_password")).sendKeys("testpass");
		driver.findElement(By.cssSelector("button[disabled^=disabled]"));
		System.out.print("login missing, worked");
		
		driver.findElement(By.id("field_site")).sendKeys("something.com");
		driver.findElement(By.id("field_login")).sendKeys("testpass");
		driver.findElement(By.id("field_password")).clear();
		driver.findElement(By.cssSelector("button[disabled^=disabled]"));
		System.out.print("pass missing, worked");

	}
	
	// Look at a stored password, ensure its displayed
	@Test
	public void ViewSavedPassword() {
		this.VisitAcmePass();
		driver.findElement(By.xpath("//tbody/tr[1]"));
		
		
	}
	
	// Update a stored password or site name, ensure success
	@Test
	public void EditSavedPasswordValid() {
		this.SignInValid();
		this.VisitAcmePass();
		driver.get("http://localhost:8080/#/acme-pass/1/edit");
		driver.findElement(By.id("field_site")).clear();
		driver.findElement(By.id("field_site")).sendKeys("modify.com");
		driver.findElement(By.id("field_login")).clear();
		driver.findElement(By.id("field_login")).sendKeys("testedit");
		driver.findElement(By.id("field_password")).clear();
		driver.findElement(By.id("field_password")).sendKeys("editpass");
		driver.findElement(By.cssSelector("button[type^=submit]")).click();
		
	}
	
	// Enter invalid info while editing saved password, ensure fail
	@Test
	public void EditSavedPasswordInvalid() {
		
	}

	// Generate a random password and ensure output matches parameters given
	@Test
	public void UsePasswordGenerator() {
		
	}
	
	// Delete saved password and ensure it is removed
	@Test
	public void DeleteSavedPassword() {
		String array [] = new String [3];
		this.VisitAcmePass();
		driver.findElement(By.xpath("//button[2]")).click();
		driver.findElement(By.cssSelector("button.btn.btn-default")).click();
		String site = driver.findElement(By.xpath("//tbody/tr[1]/td[2]")).getText();
		assertEquals ("testsite.com", site);
		array[0] = site;
		String login = driver.findElement(By.xpath("//tbody/tr[1]/td[2]")).getText();
		assertEquals ("testsite.com", login);
		array[2] = login;
		String pass = driver.findElement(By.xpath("//tbody/tr[1]/td[2]")).getText();
		assertEquals ("testsite.com", pass);
		array[3] = pass;
		
		driver.findElement(By.xpath("//button[2]")).click();
		driver.findElement(By.cssSelector("button.btn.btn-danger[type^=submit]")).click();
		String site = driver.findElement(By.xpath("//tbody/tr[1]/td[2]")).getText();
		assertEquals ("testsite.com", site);
		array[0] = site;
		String login = driver.findElement(By.xpath("//tbody/tr[1]/td[2]")).getText();
		assertEquals ("testsite.com", login);
		array[2] = login;
		String pass = driver.findElement(By.xpath("//tbody/tr[1]/td[2]")).getText();
		assertEquals ("testsite.com", pass);
		array[3] = pass;
		
		
		
	}
	
	
	private void assertFalse(String string) {
		// TODO Auto-generated method stub
		
	}

	@After
	public void tearDown() throws Exception {
		// Once the tests are done, close down the webdriver
		driver.close();
	}

}