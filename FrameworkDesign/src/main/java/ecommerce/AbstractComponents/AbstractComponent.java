package ecommerce.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ecommerce.pageobjects.CartPage;
import ecommerce.pageobjects.OrderPage;

public class AbstractComponent {
	
	WebDriver driver;

	public AbstractComponent(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = "[routerlink*='cart']")
	WebElement cartHeader;
	
	@FindBy(css = ".totalRow button")
	WebElement checkoutEle;
	
	@FindBy(css = "[routerlink*='myorders']")
	WebElement orderHeader;

	public void waitForElementToAppear(By findBy)
	{
	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(3));//Implementing explicit wait
	wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(findBy));
	}
	
	public void waitForWebElementToAppear(WebElement findBy)
	{
	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(3));//Implementing explicit wait
	wait.until(ExpectedConditions.visibilityOf(findBy));
	}
	
	public void waitForElementToBeClickeable(By findBy)
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;//Creation of the js object to execute Javascript
		js.executeScript("window.scrollBy(0,500)");//Scrolling down with Javascript
	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(3));//Implementing explicit wait
	wait.until(ExpectedConditions.elementToBeClickable(findBy));
	}
	
	public void waitForElementToDisappear(WebElement ele)
	{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(3));
		wait.until(ExpectedConditions.invisibilityOf(ele));
	}
	
	public CartPage goToCartPage()
	{
		cartHeader.click();
		CartPage cartPage = new CartPage(driver);
		return cartPage;
	}
	
	public OrderPage goToOrdersPage()
	{
		orderHeader.click();
		OrderPage orderPage = new OrderPage(driver);
		return orderPage;
	}

}
