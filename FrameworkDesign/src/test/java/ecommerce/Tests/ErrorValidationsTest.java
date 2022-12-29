package ecommerce.Tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.Test;
import ecommerce.TestComponents.Retry;

import ecommerce.TestComponents.BaseTest;
import ecommerce.pageobjects.CartPage;
import ecommerce.pageobjects.CheckOutPage;
import ecommerce.pageobjects.ConfirmationPage;
import ecommerce.pageobjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest{

	String productName = "ZARA COAT 3";
	@Test(groups= {"ErrorHandling"},retryAnalyzer=Retry.class)
	public void LoginErrorValidation() throws IOException
	{

		landingPage.loginApplication("alan.a.glz@hotmail.com", "Go1606");//Incorrect password
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
	}
	
	@Test(retryAnalyzer=Retry.class)
	public void ProductErrorValidation() throws IOException
	{

		String productName = "ZARA COAT 3";
		ProductCatalogue productCatalogue = landingPage.loginApplication("alan.a.glz@hotmail.com", "Gogu1606");
		List <WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();
		
		Boolean match = cartPage.VerifyProductDisplay(productName);
		Assert.assertTrue(match);
		
	}

}
