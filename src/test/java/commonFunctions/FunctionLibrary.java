package commonFunctions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class FunctionLibrary {
	public static WebDriver driver;
	public static Properties conpro;
	public static String Expected = "";
	public static String Actual = "";

	public static WebDriver startBrowser() throws Throwable
	{
		conpro = new Properties();
		conpro.load(new FileInputStream("./PropertyFile/Environment.properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();
		}
		else
		{
			System.out.println("Browser Value is Not Matching");
		}
		return driver;
	}

	public static void openUrl(WebDriver driver)
	{
		driver.get(conpro.getProperty("Url"));
	}

	public static void waitForElement(WebDriver driver, String LocaterType, String LocaterValue, String waitTime)
	{
		WebDriverWait Mywait = new WebDriverWait(driver, Integer.parseInt(waitTime));
		if(LocaterType.equalsIgnoreCase("id"))
		{
			Mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocaterValue)));
		}
		else if(LocaterType.equalsIgnoreCase("name"))
		{
			Mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocaterValue)));
		}
		else if(LocaterType.equalsIgnoreCase("xpath"))
		{
			Mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocaterValue)));
		}
	}

	public static void typeAction(WebDriver driver, String LocaterType, String LocaterValue, String testData )
	{
		if(LocaterType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocaterValue)).clear();
			driver.findElement(By.name(LocaterValue)).sendKeys(testData);
		}
		else if(LocaterType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocaterValue)).clear();
			driver.findElement(By.id(LocaterValue)).sendKeys(testData);
		}
		else if(LocaterType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocaterValue)).clear();
			driver.findElement(By.xpath(LocaterValue)).sendKeys(testData);
		}
	}

	public static void clickAction(WebDriver driver, String LocaterType, String LocaterValue)
	{
		if(LocaterType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocaterValue)).click();
		}
		else if(LocaterType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocaterValue)).click();
		}
		else if(LocaterType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocaterValue)).sendKeys(Keys.ENTER);
		}		
	}

	public static void validateTitle(WebDriver driver, String ExpectedTitle)
	{
		String ActualTitle = driver.getTitle();
		try {
			Assert.assertEquals(ActualTitle, ExpectedTitle,"Title Is Not Matching");
		}
		catch (Exception t)
		{
			System.out.println(t.getMessage());
		}	
	}

	public static void closeBrowser(WebDriver driver)
	{
		driver.quit();
	}
	
	public static void mouseClick(WebDriver driver) throws Throwable
	{
		Actions ac = new Actions(driver);
		ac.moveToElement(driver.findElement(By.xpath("//a[text()='Stock Items ']"))).perform();
		Thread.sleep(2000);
		ac.moveToElement(driver.findElement(By.xpath("(//a[contains(.,'Stock Categories')])[2]"))).click().perform();
	}
	
	public static void stockTable(WebDriver driver, String ExpectedData) throws Throwable
	{
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(ExpectedData);
		Thread.sleep(2000);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(2000);
		String Actualdata = driver.findElement(By.xpath("//table[@id='tbl_a_stock_categorieslist']/tbody/tr[1]/td[4]/div/span/span")).getText();
		System.out.println(ExpectedData+" "+Actualdata);
		Assert.assertEquals(Actualdata, ExpectedData,"Category Name Not Found In Table");
	}
	
	public static void captureData(WebDriver driver, String LocaterType, String LocaterValue)
	{
		Expected = driver.findElement(By.name(LocaterValue)).getAttribute("value");
	}
	
	public static void supplierTable(WebDriver driver) throws Throwable
	{
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Expected);
		Thread.sleep(2000);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(2000);
		Actual = driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr[1]/td[6]/div/span/span")).getText();
		System.out.println(Expected+" "+Actual);
		Assert.assertEquals(Actual, Expected,"Supplier Number not found in Table");
		
	}


}




















