package ecommerce.Tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import ecommerce.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String productName = "ZARA COAT 3";
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));//Implementing implicitly wait
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(2));//Implementing explicit wait
		driver.get("https://rahulshettyacademy.com/client");
		
		LandingPage landingPage = new LandingPage(driver);
		driver.findElement(By.id("userEmail")).sendKeys("alan.a.glz@hotmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Gogu1606");
		driver.findElement(By.id("login")).click();
		
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".mb-3")));
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));//creating a list of webElements
		/*Iterating with each product using java streams, retrieving on "product" by applying findElement
		and filtering by equals to ZARA COAT 3, find the first and store in prod variable and if not returns null*/
		WebElement prod = products.stream().filter(product-> 
		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();//Finding element using prod
		
		//Using explicit wait until the element located is visible
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		//Using explicit wait until animation element locate is invisible
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();//Clicking on the cart button
		
		List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cart h3"));//Creating a list of webElements in the cart
		/*Comparing if there are any match in the cart vs productName and validating with an assertion*/
		Boolean match = cartProducts.stream().anyMatch(cartProduct-> cartProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(match);
		
		JavascriptExecutor js = (JavascriptExecutor)driver;//Creation of the js object to execute Javascript
		js.executeScript("window.scrollBy(0,500)");//Scrolling down with Javascript
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".totalRow button")));
		driver.findElement(By.cssSelector(".totalRow button")).click();//Clicking on the checkout button
		Actions a = new Actions(driver);//Sending keys using the Actions
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder*='Select Country']")), "United States").build().perform();
		
		js.executeScript("window.scrollBy(0,500)");//Scrolling down with Javascript
		//Explicit wait for the options displayed in the dropdown
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class*='ta-results']")));
		driver.findElement(By.cssSelector(".ta-item:nth-of-type(1)")).click();//Clicking on the first option
		driver.findElement(By.cssSelector(".action__submit")).click();//Clicking on the place order button
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();//Getting confirmation text
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));//Validating the confirmation message
		driver.close();
		
	}
	

}
