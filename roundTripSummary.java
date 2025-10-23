package akasaAir;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class roundTripSummary {
	WebDriver driver;
	WebDriverWait wait;

	@BeforeClass
	public void launchBrowser() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		Reporter.log("Browser Launched", true);
	}

	@Test(priority = 1)
	public void navigateToApp() {
		driver.get("https://www.akasaair.com/");
		Reporter.log("Navigated to App", true);
	}

	@Test(priority = 2, dependsOnMethods = "navigateToApp")
	public void verifyTitle() {
		String expected = "Book Flights Online with Akasa Air at affordable fares.";
		String actual = driver.getTitle();
		Assert.assertEquals(actual, expected);
		driver.findElement(By.xpath("//button[@aria-label='Accept cookies']")).click();
		driver.switchTo().defaultContent();

		Reporter.log("Title Verified", true);
	}

	@Test(priority = 3, dependsOnMethods = "verifyTitle")
	public void mouseHoverBook() {
		WebElement element = driver.findElement(By.xpath("//a[@href='/flight-booking']"));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		Actions act = new Actions(driver);
		act.moveToElement(element).perform();
		Reporter.log("Hovered on Book", true);
	}

	@Test(priority = 4, dependsOnMethods = "mouseHoverBook")
	public void clickOnFlight() {
		driver.findElement(By.xpath("//span[@aria-label='Flight']")).click();
		Reporter.log("Clicked on Flight", true);
	}

	@Test(priority = 5, dependsOnMethods = "clickOnFlight")
	public void selectRoundTrip() {
		driver.findElement(By.id("roundTrip")).click();
		Reporter.log("Round Trip selected", true);
	}

	@Test(priority = 6, dependsOnMethods = "selectRoundTrip")
	public void fromAndTo() {
		driver.findElement(By.name("From")).sendKeys("PNQ");
		driver.findElement(By.xpath("//span[text()='PNQ']")).click();
		driver.findElement(By.id("To")).sendKeys("CCU");
		driver.findElement(By.xpath("//span[text()='CCU']")).click();
		Reporter.log("From and To selected", true);
	}

	@Test(priority = 7, dependsOnMethods = "fromAndTo")
	public void departureDate() {
		driver.findElement(By.name("DepartureDate")).click();
		WebElement element = driver.findElement(By.xpath("//button[@aria-label='Next Month']"));
		element.click();
		element.click();
		driver.findElement(By.xpath("//div[@aria-label='Choose Thursday, December 25th, 2025']")).click();
		Reporter.log("Departure Date selected", true);
	}

	@Test(priority = 8, dependsOnMethods = "departureDate")
	public void returnDate() {
		driver.findElement(By.name("returnDate")).click();
		driver.findElement(By.xpath("//button[@aria-label='Next Month']")).click();
		driver.findElement(By.xpath("//div[@aria-label='Choose Wednesday, February 25th, 2026']")).click();
		Reporter.log("Return Date selected", true);
	}

	@Test(priority = 9, dependsOnMethods = "returnDate")
	public void eachPasanger1() {
		driver.findElement(By.xpath("//div[@aria-label='Passengers']")).click();
		driver.findElement(By.xpath("//button[@aria-label='Children Plus']")).click();
		driver.findElement(By.xpath("//button[@aria-label='Senior Citizen Plus']")).click();
		driver.findElement(By.xpath("//button[@aria-label='Infant(s) Plus']")).click();
		driver.findElement(By.xpath("//button[@aria-label='Extra Seat(s) Plus']")).click();
		driver.findElement(By.xpath("//button[@aria-label='Done']")).click();
		Reporter.log("1 of each type of Passengers selected");
	}

	@Test(priority = 10, dependsOnMethods = "eachPasanger1")
	public void searchFlight() {
		driver.findElement(By.name("Search Flights")).click();
		Reporter.log("Search Flight button clicked", true);
	}

	@Test(priority = 11, dependsOnMethods = "searchFlight")
	public void selectAFlight() {
		WebElement toast = driver.findElement(By.xpath("//button[@aria-label='close']"));
		wait.until(ExpectedConditions.invisibilityOf(toast));
		driver.findElement(By.xpath("//span[text()='Lowest fare']")).click();
		driver.findElement(By.xpath("//span[text()='Flexi']")).click();
		driver.findElement(By.xpath("//div[text()='CCU - PNQ']")).click();
		driver.findElement(By.xpath("//span[text()='Lowest fare']")).click();
		driver.findElement(By.xpath("//span[text()='Flexi']")).click();
		Reporter.log("Selected a Flight", true);
	}

	@Test(priority = 12, dependsOnMethods = "selectAFlight")
	public void viewSummary() throws IOException {
		driver.findElement(By.xpath("//button[@aria-label='View summary']")).click();
		TakesScreenshot ts = (TakesScreenshot) driver;
		File temp = ts.getScreenshotAs(OutputType.FILE);
		File dest = new File("/home/rjshwr/eclipse-workspace/basicSelenium/target/screenshots/viewSummary.png");
		org.openqa.selenium.io.FileHandler.copy(temp, dest);
		driver.findElement(By.id("closePopUp")).click();
		Reporter.log("ScreenShot of Summary taken",true);
	}

	@Test(priority = 13, dependsOnMethods = "viewSummary")
	public void continueButton() throws IOException {
		driver.findElement(By.name("continue")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[@aria-label='Label-text']")));
		TakesScreenshot ts = (TakesScreenshot) driver;
		File temp = ts.getScreenshotAs(OutputType.FILE);
		File dest = new File("/home/rjshwr/eclipse-workspace/basicSelenium/target/screenshots/continue.png");
		org.openqa.selenium.io.FileHandler.copy(temp, dest);
		Reporter.log("Screenshot after clicking Continue button taken");
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
		Reporter.log("Browser Closed");
	}

}
