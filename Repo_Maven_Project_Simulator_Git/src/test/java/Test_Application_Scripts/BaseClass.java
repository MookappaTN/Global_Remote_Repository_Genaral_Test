package Test_Application_Scripts;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Driver;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BaseClass {
	
  public WebDriver driver = null;
  
  @Test(priority = 1)
  public void takeSectionScreenshot() throws IOException {
	  WebElement section = driver.findElement(By.xpath("//*[@class='header-navigation pull-right font-transform-inherit']"));
	  
	  File src = section.getScreenshotAs(OutputType.FILE);
	  Date currentDate = new Date();
	  File dest = new File("./"+currentDate.toString().replace(":", "_").replace(" ", "_")+".png");
	  FileHandler.copy(src, dest);	  
	  
  }
  
  @Test(priority = 2)
  public void getAllLinks() throws IOException, InterruptedException {
	  List<WebElement> links = driver.findElements(By.tagName("a"));
	  
	  for (WebElement webElement : links) {
		  
		  String allLinks = webElement.getAttribute("href");
		  String linkTextVal = webElement.getText();
		  URL url = new URL(allLinks);
		  HttpURLConnection httpReq = (HttpURLConnection) url.openConnection();
		  httpReq.connect();
		  
		  if(httpReq.getResponseCode()==200 && allLinks.length()>1) {
			  
			  System.out.println("The link is active : "+allLinks);
			  String tab = Keys.chord(Keys.CONTROL,Keys.RETURN);
			  Thread.sleep(3000);
			  driver.findElement(By.linkText(linkTextVal)).sendKeys(tab);
			  
		  }
		
	}
  }
  
  @Test(priority = 3)
  public void dropDown() {
	  WebElement dropdown = driver.findElement(By.xpath("//select[@country-data-region-id='gds-cr-one']"));
	  Select select = new Select(dropdown);
	  List<WebElement> dropdownValues = select.getOptions();
	 for (WebElement webElement : dropdownValues) {
		 System.out.println(webElement.getAttribute("value"));
		 select.selectByValue(webElement.getAttribute("value"));
		
	 }
	  
  }
  
  @Test(priority = 4)
  public void fileUpload() throws InterruptedException{
	  driver.get("https://demo.automationtesting.in/FileUpload.html");
	  Thread.sleep(3000);
	  
	  WebElement files = driver.findElement(By.xpath("//*[@id='input-4']"));
	  files.click();
	  Thread.sleep(3000);
	  files.sendKeys("C:\\Users\\MookappaT\\Documents\\New Folder\\temp.docx");
	  driver.findElement(By.xpath("//span[contains(.,'Upload')]")).submit();
	  
	  
	  
  }
  
  
  @BeforeTest
  public void beforeClass() {
	  
	  this.driver  = new ChromeDriver();
	  driver.get("https://www.geodatasource.com/software/country-region-dropdown-menu-demo");
	  
  }

  @AfterTest
  public void afterClass() {
	  driver.quit();
	  
	  
  }

}
