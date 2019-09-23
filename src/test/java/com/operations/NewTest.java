package com.operations;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class NewTest {
	@Test
	public void f() throws IOException, InterruptedException {
		/*WebDriver driver = null ;
		/* String SALTCHARS = "New Address";
     Random random = new Random();
     int low = 10;
     int high = 100;
     int result = random.nextInt(high-low) + low;
      System.out.println(SALTCHARS+result);
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") +"/Browser_files/chromedriver_win32/chromedriver.exe");
		//WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();


		driver.get("https://stg.prideprovider.com");
		driver.findElement(By.xpath("//*[@id='loginForm']/div[1]/input")).sendKeys("Shailesh.mane@logixal.com");
		driver.findElement(By.xpath("//*[@id='loginForm']/div[2]/input")).sendKeys("Admin123");
		driver.findElement(By.xpath("//*[@id='loginForm']/div[3]/button")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("/html/body/header/div/div[2]/div[3]/a/span[1]/i")).click();
		Thread.sleep(10000);
		List <WebElement> t1 =driver.findElements(By.xpath("//li[@data-bind='click:remove']"));
		System.out.println("Total cart items :" + t1.size());
		WebDriverWait myWaitVar = new WebDriverWait(driver,20);
		if(t1.size()>0) {

			for(int i =0;i<t1.size();i++)
			{
				myWaitVar.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@data-bind='click:remove']")));
				List <WebElement> t2 =null;
				Thread.sleep(10000);
				t2 =driver.findElements(By.xpath("//li[@data-bind='click:remove']"));
				System.out.println("Current Cart items" + t2.size());
				myWaitVar.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@data-bind='click:remove']")));
					Thread.sleep(10000);
					t2.get(0).click();
					t2.clear();
				}
				

					/*for(int i =0;i<tl.size();i++)
		{
			List <WebElement> t2 =driver.findElements(By.xpath("//li[@data-bind='click:remove']"));
			System.out.println(t2.size());
			t2.get(0).click();
			t2.remove(0);
			//break;


		}
					
			
			
		}*/
		Runtime rt = Runtime.getRuntime();
		rt.exec("taskkill /F /IM chromedriver.exe");
	}
	
}
	
