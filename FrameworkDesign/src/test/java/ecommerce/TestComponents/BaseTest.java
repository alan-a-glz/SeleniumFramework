package ecommerce.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ecommerce.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	
	public WebDriver driver;
	public LandingPage landingPage;
	
	public WebDriver initializeDriver() throws IOException
	{
		//Properties class
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
				+"\\src\\main\\java\\Resources\\GlobalData.properties");
		prop.load(fis);
		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") : prop.getProperty("browser");
		//prop.getProperty("browser");
		
		if (browserName.contains("chrome")){
		ChromeOptions options = new ChromeOptions();
		WebDriverManager.chromedriver().setup();
			if(browserName.contains("headless")){//To run only if browserName contains the headless word
			options.addArguments("headless");//To run chrome in headless mode "only backend"
			}
		driver = new ChromeDriver(options);
		driver.manage().window().setSize(new Dimension(1440,900));//To run in full screen
		
		}else if (browserName.equalsIgnoreCase("firefox")){
			System.setProperty("webdriver.gecko.driver",
					"C:\\Users\\FAMILIAR\\Desktop\\Selenium with Java\\geckodriver-v0.31.0-win64\\geckodriver.exe");
			driver = new FirefoxDriver();
			
		}else if (browserName.equalsIgnoreCase("edge")){
			System.setProperty("webdriver.edge.driver", 
					"C:\\Users\\FAMILIAR\\Desktop\\Selenium with Java\\edgedriver_win64\\msedgedriver.exe");
			driver = new EdgeDriver();
		}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));//Implementing implicitly wait
		return driver;
	}
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException
	{
		//read json to string
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
		
		//String to HashMap with dependency Jackson DataBind
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String,String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>(){});	
		return data;	
	}
	
	public String getScreenShot(String testCaseName,WebDriver driver) throws IOException
	{
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir")+"\\reports\\"+ testCaseName +".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir")+"\\reports\\"+ testCaseName +".png";
	}
	
	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException
	{
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown()
	{
		driver.close();
	}

}
