package ecommerce.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import ecommerce.AbstractComponents.AbstractComponent;

public class CheckOutPage extends AbstractComponent{

	WebDriver driver;
	public CheckOutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css = ".action__submit")
	WebElement submit;
	
	@FindBy(css = "[placeholder*='Select Country']")
	WebElement country;
	
	@FindBy(css = ".ta-item:nth-of-type(1)")
	WebElement selectCountry;
	
	By results = By.cssSelector(".ta-results");
	
	public void selectCountry(String countryName) 
	{
		Actions a = new Actions(driver);//Sending keys using the Actions
		a.sendKeys(country, countryName).build().perform();
		waitForElementToAppear(By.cssSelector(".ta-results"));
		selectCountry.click();
	}
	
	public ConfirmationPage submitOrder()
	{
		waitForElementToBeClickeable(By.cssSelector(".action__submit"));
		submit.click();
		return new ConfirmationPage(driver);
	}

}
