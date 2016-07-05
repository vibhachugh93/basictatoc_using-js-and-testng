package TestNG1;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import java.io.File;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.*;

public class NewTest1 
{
	
	File ffExecutable=new File("/home/vibhachugh/firefox/firefox");
	FirefoxBinary ffBinary=new FirefoxBinary(ffExecutable);
    FirefoxProfile ffProfile=new FirefoxProfile();
   
   
    public static String url = "http://10.0.1.86/";
	public static WebDriver driver;
	public static JavascriptExecutor js;
	
	@BeforeClass
	public void loadDrivers(){
		driver =new FirefoxDriver(ffBinary,ffProfile);
		driver.get(url);
		js = (JavascriptExecutor) driver;
	}

	
	// To test the title of the pageJain
	@Test(priority=0)
	public void testHomePageTitle()
	{
		String expectedTitle = "TAP Utility Server";
		String actualTitle = (String) js.executeScript("return document.title");
		Assert.assertEquals(expectedTitle , actualTitle);
		
	}
	
	//To navigate to the Basic/Advanced Course Page
	@Test(priority=1)
	public void click_Tatoc_Link()
	{
		js.executeScript("document.getElementsByTagName('a')[5].click()");
		String expectedTitle = "Welcome - T.A.T.O.C";
		String actualTitle = (String) js.executeScript("return document.title");
		Assert.assertEquals(expectedTitle , actualTitle);
		
	}
	
	//Navigate To Grid Gate
	@Test(dependsOnMethods = {"click_Tatoc_Link"})
	public void clickBasicCourse()
	{
		js.executeScript("document.getElementsByTagName('a')[0].click()");
		String expectedTitle = "Grid Gate - Basic Course - T.A.T.O.C";
		String actualTitle = (String) js.executeScript("return document.title");
		Assert.assertEquals(expectedTitle , actualTitle);
	}
	
	//Greenbox Click Event
	@Test(dependsOnMethods={"clickBasicCourse"})
	public void clickGreenbox()
	{
		js.executeScript("document.querySelector('.greenbox').click();");
		String expectedTitle = "Frame Dungeon - Basic Course - T.A.T.O.C";
		String actualTitle = (String) js.executeScript("return document.title");
		Assert.assertEquals(expectedTitle , actualTitle);
	}
	 
	// Box color change event
	
	@Test(dependsOnMethods={"clickGreenbox"})
	public void changeBoxcolor() throws InterruptedException
	{
		String color1 = (String) js.executeScript("return document.querySelector('#main').contentWindow.document.querySelector('#answer').className;");
		System.out.println(color1);
		String color2 = (String) js.executeScript("return document.querySelector('#main').contentWindow.document.querySelector('#child').contentWindow.document.getElementById('answer').getAttribute('class');");
		System.out.println(color2);
		while(!color1.equals(color2))
		{
			js.executeScript("document.getElementById('main').contentWindow.document.getElementsByTagName('a')[0].click();");
			Thread.sleep(3000);
			color2=(String)js.executeScript("return document.querySelector('#main').contentWindow.document.querySelector('#child').contentWindow.document.getElementById('answer').getAttribute('class');");
		}		
		js.executeScript("document.getElementById('main').contentWindow.document.getElementsByTagName('a')[1].click();");
		
		Thread.sleep(2000);
	}
	
	// drag and drop event
	
	@Test(dependsOnMethods={"changeBoxcolor"})
	public void dragandDrop()
	{
		js.executeScript("document.getElementById('dragbox').setAttribute('style','position: relative; left: 32px; top: -59px;')");
	    js.executeScript("document.querySelector('a[onclick^=gonext]').click();");
	}
	
	//pop up window
	@Test(dependsOnMethods={"dragandDrop"})
	public void launchPopupwindow() throws InterruptedException
	{
		//js.executeScript("document.querySelector('a[onclick^=launchwindow]').click();");
		/*String expectedTitle = "Popup - Basic Course - T.A.T.O.C";
		String actualTitle = (String) js.executeScript("return document.title");
		Assert.assertEquals(expectedTitle , actualTitle);*/
		String mainwindow=driver.getWindowHandle();
		js.executeScript("document.querySelector('a[onclick^=launchwindow]').click();");
		for(String winhandle:driver.getWindowHandles())
		{
			driver.switchTo().window(winhandle);
			
		}
		js.executeScript("document.getElementById('name').setAttribute('value','vibha')");
		Thread.sleep(2000);
		js.executeScript("document.getElementById('submit').click();");
	    driver.switchTo().window(mainwindow);
	    Thread.sleep(2000);
        js.executeScript("document.querySelector('a[onclick^=gonext]').click();");
	}
	 // cookie handling
	@Test(dependsOnMethods={"launchPopupwindow"})
	public void cookieGeneration()
	{
	  js.executeScript("document.querySelector('a[onclick^=generateToken]').click();");
	  String tok =(String) js.executeScript("return document.getElementById('token').textContent;");
	  System.out.println(tok);
	 //js.executeScript("document.cookie('Token=document.getElementById('token').textContent.split(':')[1].trim()'");
	 //js.executeScript("document.write('Token=+tok')");
	  int k=tok.length();
      String s3=tok.substring(7,k);
      s3="Token="+s3;
      Cookie c=new Cookie("mycookie",s3);
      driver.manage().addCookie(c);
      js.executeScript("document.querySelector('a[onclick^=gonext]').click();");
   }
}
	
	
	
	
	
	/*@AfterClass
	public void unloadDriver(){
		driver.quit();
	}*/
	  
	  



