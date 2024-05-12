package Test_Application_Scripts;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class UtilsTestNg {
	public WebDriver driver ;
	
	@BeforeTest
	public void intialiseBrowser() {
		this.driver = new ChromeDriver();
		driver.get("https://demo.automationtesting.in/Register.html");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		
	}
	
	@Test(priority = 1,invocationCount = 2,groups = "smoke")
	public void registerApp() {
		driver.findElement(By.xpath("//*[@placeholder='First Name']")).sendKeys("mkp");
		driver.findElement(By.xpath("//*[@ng-model='Adress']")).sendKeys("#mkp,Banglore-Karnataka-560001");
		driver.findElement(By.xpath("//*[@type='tel']")).sendKeys("+91 701933333333");
		driver.findElement(By.xpath("//*[@value='Male']")).click();
		driver.findElement(By.xpath("//*[@value='Cricket']")).click();
		
		((JavascriptExecutor)driver).executeScript("documents[0].scrollIntoView();",driver.findElement(By.xpath("//*[text()=' Submit ']")));
		driver.findElement(By.xpath("//*[text()=' Submit ']")).click();
	}
	
	@Test(priority = 2,invocationCount = 2,groups = "smoke")
	public void getScreenshotOfFooter() throws InterruptedException, IOException {
		WebElement footerSec = driver.findElement(By.id("footer"));
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", footerSec);
		Thread.sleep(3000);
		
		Date dateTime = new Date();
		
		
		File scr = footerSec.getScreenshotAs(OutputType.FILE);
		File dest = new File("./"+dateTime.toString().replace(":", "_").replace(" ", "_")+".png");
		FileHandler.copy(scr, dest);
		
		
	}
	
	@Test(priority = 3,invocationCount = 2,groups = "smoke")
	public void openALLinks() throws IOException, InterruptedException {
		List<WebElement> list = driver.findElements(By.xpath("//a"));
		
		for (WebElement webElementlinks : list) {
			String link = webElementlinks.getAttribute("href");
			
			URL url = new URL(link);
			HttpURLConnection urlCon =  (HttpURLConnection) url.openConnection();
			urlCon.connect();
			if (urlCon.getResponseCode()==200) {
				Thread.sleep(300);
				System.out.println("link is valid :"+link);
				
			}
			
			
		}
		
			
	}
	
	@Test(priority = 4,invocationCount = 1)
	public void uploadFile() throws InterruptedException {
		Thread.sleep(300);
		driver.findElement(By.id("imagesrc")).sendKeys("C:\\Users\\MookappaT\\Downloads\\sampleFile (8).jpeg");
	}
	
	@AfterTest
	public void tearDown() throws InterruptedException {
		Thread.sleep(300);
		driver.quit();
	}
	

}
