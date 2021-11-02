package testclasses;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestClassUsingListeners {

	public WebDriver driver;

	@BeforeClass
	public void beforeClass() {

		System.setProperty("webdriver.chrome.driver",
				"F:\\Study\\Velocity Material\\Drivers\\Chrome\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.google.co.in");

	}

	@AfterClass
	public void afterClass() {

		driver.quit();

	}

	@Test
	public void testSuccessfull() {

		System.out.println("Executing Successfull Test Method");
	}

	@Test
	public void testFailed() {
		System.out.println("Executing Successfull Test Method");
		Assert.fail("Executing Failed Test Method");
	}

	@Test
	public void testSkipped() {
		System.out.println("Executing Successfull Test Method");
		throw new SkipException("Executing Skipped Test Case");
	}

}
