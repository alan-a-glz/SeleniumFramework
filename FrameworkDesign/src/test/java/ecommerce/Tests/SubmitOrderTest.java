package ecommerce.Tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ecommerce.TestComponents.Retry;

import ecommerce.TestComponents.BaseTest;
import ecommerce.pageobjects.CartPage;
import ecommerce.pageobjects.CheckOutPage;
import ecommerce.pageobjects.ConfirmationPage;
import ecommerce.pageobjects.LandingPage;
import ecommerce.pageobjects.OrderPage;
import ecommerce.pageobjects.ProductCatalogue;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SubmitOrderTest extends BaseTest{

	String productName = "ZARA COAT 3";
	
	@Test(dataProvider="getData",groups= {"Purchase"},retryAnalyzer=Retry.class)
	public void submitOrder(HashMap<String,String> input) throws IOException
	{

	
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		List <WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("product"));
		CartPage cartPage = productCatalogue.goToCartPage();
		
		Boolean match = cartPage.VerifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		CheckOutPage checkOutPage = cartPage.goToCheckOut();
		checkOutPage.selectCountry("United States");
		ConfirmationPage confirmationPage = checkOutPage.submitOrder();
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));//Validating the confirmation message
		
	}
	
	@Test(dependsOnMethods= {"submitOrder"})
	public void OrderHistoryTest()
	{
		ProductCatalogue productCatalogue = landingPage.loginApplication("alan.a.glz@hotmail.com", "Gogu1606");
		OrderPage orderPage = productCatalogue.goToOrdersPage();
		Assert.assertTrue(orderPage.VerifyOrderDisplay(productName));
	}
		
	@DataProvider
	public Object[][] getData() throws IOException
	{
				
		List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir")
				+"\\src\\test\\java\\ecommerce\\data\\PurchaseOrder.json");
		return new Object [][] { {data.get(0)},{data.get(1)},{data.get(2)} };
		
	}
	
	//Other way
	/*HashMap<String,String> map = new HashMap<String,String>();
	map.put("email", "alan.a.glz@hotmail.com");
	map.put("password", "Gogu1606");
	map.put("product", "ZARA COAT 3");
	
	HashMap<String,String> map1 = new HashMap<String,String>();
	map1.put("email", "alan.a.glz@hotmail.com");
	map1.put("password", "Gogu1606");
	map1.put("product", "ADIDAS ORIGINAL");
	
	HashMap<String,String> map2 = new HashMap<String,String>();
	map2.put("email", "alan.a.glz@hotmail.com");
	map2.put("password", "Gogu16");//Test to fail
	map2.put("product", "ZARA COAT 3");*/
	
	
	//Other way
	/*@DataProvider
	public Object [] [] getData()
	{
	return new Object [] [] {{"alan.a.glz@hotmail.com","Gogu1606","ZARA COAT 3"},{"alan.a.glz@hotmail.com"
	,"Gogu1606","ADIDAS ORIGINAL"},{"alan.a.glz@hotmail.com","Gogu16","ZARA COAT 3"}};
	}*/
	

}
